package com.sunday.sundaymovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_STARS_TABLE = "create table stars (" +
            "id integer primary key," +
            "name text," +
            "img text)";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
