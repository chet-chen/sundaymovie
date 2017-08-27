package com.sunday.sundaymovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunday.sundaymovie.bean.StarMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

public class StarTableHelper {
    private static final String TABLE_NAME = "star";
    private DataBaseHelper mHelper;

    public StarTableHelper(Context context) {
        mHelper = new DataBaseHelper(context);
    }

    public void insert(StarMovie starMovie) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", starMovie.getId());
        values.put("name", starMovie.getName());
        values.put("img", starMovie.getImg());
        db.insert(TABLE_NAME, null, values);
    }

    public void delete(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
    }

    public List<StarMovie> queryAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        List<StarMovie> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new StarMovie(
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
        SQLiteDatabase db = mHelper.getWritableDatabase();
        boolean b;
        Cursor cursor = db.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        b = cursor.moveToFirst();
        cursor.close();
        return b;
    }

    public void close() {
        mHelper.close();
    }

}