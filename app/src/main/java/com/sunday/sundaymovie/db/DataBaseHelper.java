package com.sunday.sundaymovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sunday.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_STAR_TABLE =
            "CREATE TABLE " + StarTableHelper.TABLE_NAME + " (" +
                    StarTableHelper.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    StarTableHelper.COLUMN_NAME + " TEXT," +
                    StarTableHelper.COLUMN_IMG + " TEXT )";

    DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
