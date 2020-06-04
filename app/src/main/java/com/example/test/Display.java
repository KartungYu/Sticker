package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Display extends AppCompatActivity {

    private GridView gridView;
    private MyAdapter adapter;
    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        gridView = (GridView) findViewById(R.id.gridview);


        dataList =  getIntent().getStringArrayListExtra("ARRAYLIST");
//        dataList = new ArrayList<Map<String, Object>>();
//        System.out.println(paths.size());
//        ;
//        for (int i = 0; i < paths.size(); i++) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            System.out.println(paths.get(i));
//            String p = paths.get(i);
//            Uri uri = Uri.fromFile(new File(p));
//            map.put("img", uri);
//            dataList.add(map);
//        }
//        {
//            Map<String, Object> map=new HashMap<String, Object>();
//            String p=(String)paths.get(i);
//            System.out.println("yyyy");
//            System.out.println(p);
//            Uri uri=Uri.fromFile(new File(p));
////            map.put("id",i);
//           map.put("img", uri);
//           dataList.add(map);
//        }


        adapter = new MyAdapter(dataList,this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Display.this);
                builder.setTitle("提示").setMessage("第"+arg2+"被點擊了").create().show();
            }
        });
    }

}


