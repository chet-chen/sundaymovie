package com.sunday.sundaymovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunday.sundaymovie.model.StarsMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

public class StarsTableHelper {
    private static final String TABLE_NAME = "stars";
    private DataBaseHelper mHelper;
    private SQLiteDatabase mDB;

    public StarsTableHelper(Context context) {
        mHelper = new DataBaseHelper(context, "sunday.db", null, 1);
        mDB = mHelper.getWritableDatabase();
    }

    public void insert(StarsMovie starsMovie) {
        ContentValues values = new ContentValues();
        values.put("id", starsMovie.getId());
        values.put("name", starsMovie.getName());
        values.put("img", starsMovie.getImg());
        mDB.insert(TABLE_NAME, null, values);
    }

    public void delete(int id) {
        mDB.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
    }

    public List<StarsMovie> queryAll() {
        List<StarsMovie> list = new ArrayList<>();
        Cursor cursor = mDB.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new StarsMovie(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("img")))
                );
            }
            cursor.close();
        }
        return list;
    }

    public boolean queryIsExist(int id) {
        boolean b;
        Cursor cursor = mDB.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        b = cursor.moveToFirst();
        cursor.close();
        return b;
    }

    public void close() {
        mDB.close();
        mHelper.close();
    }

}
