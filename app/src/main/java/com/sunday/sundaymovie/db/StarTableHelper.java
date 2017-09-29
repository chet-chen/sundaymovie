package com.sunday.sundaymovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.sunday.sundaymovie.bean.StarMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

public class StarTableHelper {
    static final String TABLE_NAME = "star";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_IMG = "img";

    private DataBaseHelper mHelper;

    public StarTableHelper(@NonNull Context context) {
        mHelper = new DataBaseHelper(context);
    }

    public void insert(StarMovie starMovie) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, starMovie.getId());
        values.put(COLUMN_NAME, starMovie.getName());
        values.put(COLUMN_IMG, starMovie.getImg());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{"" + id});
        db.close();
    }

    public List<StarMovie> queryAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int count = cursor.getCount();
        List<StarMovie> list = new ArrayList<>(count);
        while (cursor.moveToNext()) {
            list.add(new StarMovie(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMG))));
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean queryIsExist(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        boolean b;
        Cursor cursor = db.query(TABLE_NAME, new String[]{"" + COLUMN_ID}, "id=?",
                new String[]{"" + id}, null, null, null);
        b = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return b;
    }
}
