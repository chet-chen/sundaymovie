package com.sunday.sundaymovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunday.sundaymovie.bean.StarsMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

public class StarsTableHelper {
    private static final String TABLE_NAME = "stars";
    private DataBaseHelper mHelper;

    public StarsTableHelper(Context context) {
        mHelper = new DataBaseHelper(context);
    }

    public void insert(StarsMovie starsMovie) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", starsMovie.getId());
        values.put("name", starsMovie.getName());
        values.put("img", starsMovie.getImg());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
    }

    public List<StarsMovie> queryAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        List<StarsMovie> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
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
