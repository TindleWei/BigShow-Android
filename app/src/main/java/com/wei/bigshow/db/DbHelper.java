package com.wei.bigshow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * describe
 * created by tindle
 * created time 16/8/5 下午4:19
 */
public class DbHelper extends SQLiteOpenHelper {

    public static String DB_LOCK = "db_story";

    public static String DB_NAME = "story.db";

    public static int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
