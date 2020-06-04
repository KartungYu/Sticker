package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyDatabaseHelper";
    public static final String CREATE_IMAGE="create table Image(" +
            "id integer primary key autoincrement," +
            "photoPath text)";

    private Context mContext;

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_IMAGE);
                Toast.makeText(mContext, "create image success", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "create image success");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //改版本号时
        db.execSQL("drop table if exists Image");
        onCreate(db);
    }
}

