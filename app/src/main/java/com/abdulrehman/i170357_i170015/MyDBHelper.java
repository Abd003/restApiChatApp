package com.abdulrehman.i170357_i170015;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    String CREATE_USER_TABLE="CREATE TABLE "+
            MyUserContract.Users.TABLENAME+ "("+
            MyUserContract.Users._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MyUserContract.Users._EMAIL+ " TEXT, "+
            MyUserContract.Users._PASSWORD+ " TEXT );";
    String DELETE_USER_TABLE="DROP TABLE IF EXISTS "+MyUserContract.Users.TABLENAME;
    public MyDBHelper(@Nullable Context context) {
        super(context, MyUserContract.DB_NAME, null, MyUserContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_USER_TABLE);
        onCreate(db);
    }
}
