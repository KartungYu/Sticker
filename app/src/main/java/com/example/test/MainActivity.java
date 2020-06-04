package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private  Button display;
//    private ImageView imageView;
    private Context context;
    private MyDatabaseHelper helper=new MyDatabaseHelper(MainActivity.this,"img.db",null,2);
    private ArrayList<String>paths=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (Button) findViewById(R.id.display);
        button = (Button) findViewById(R.id.choose);
       // imageView = (ImageView) findViewById(R.id.imageview);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Display.class);
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor=db.query("Image",null,null,null,null,null,null);
                paths.clear();
                if (cursor.moveToFirst())
                {
           do{

                String path=cursor.getString(cursor.getColumnIndex("photoPath"));
                if(path==null)continue;
                paths.add(path);
                System.out.println(path);

            }while (cursor.moveToNext());
        }
           cursor.close();
                intent1.putStringArrayListExtra("ARRAYLIST",paths);
                startActivity(intent1);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                try{
                    Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                     System.out.println(bitmap);
                     System.out.println("yes");
                   String path=saveImageToGallery(bitmap);
                    System.out.println(path);
//                    helper=new MyDatabaseHelper(MainActivity.this,"img.db",null,2);
                    SQLiteDatabase db =helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("photoPath",path);
                    long res = db.insert("Image",null, values);
                    if(res != -1) {
                        Toast.makeText(MainActivity.this,"insert Successful",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,"insert failed",Toast.LENGTH_SHORT).show();
                    }
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
                //imageView.setImageURI(uri);
            }
        }
    }

    public String saveImageToGallery(Bitmap bmp) {
        //生成路径
       // String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File externalFilesDir = getExternalFilesDir("Caches");
        System.out.println(externalFilesDir);
        //文件名为时间
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(timeStamp));
        String fileName = sd + ".jpg";
        //获取文件
        File file = new File(externalFilesDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            //通知系统相册刷新
            MainActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
            fileName=file.toString();
           // System.out.println(fileName);
            return fileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

