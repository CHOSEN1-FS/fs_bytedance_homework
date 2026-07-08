package com.example.day2.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper
{
    // 修改版本号从 1 改为 2
    public static final int DATABASE_VERSION = 2;  // 改为 2
    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "info";

    // 创建表的SQL语句 - 确保有email列
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email CHAR(20), " +
                    "password CHAR(20)" +
                    ")";

    public MyHelper(Context context)
    {
        // 使用新的版本号
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // 删除旧表，创建新表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
