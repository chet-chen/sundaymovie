package com.sunday.sundaymovie.model;

import android.content.Context;

import com.sunday.sundaymovie.bean.StarMovie;
import com.sunday.sundaymovie.db.StarsTableHelper;

import java.util.List;

/**
 * Created by agentchen on 2017/7/23.
 */

public class StarModel {
    private StarsTableHelper mHelper;

    public StarModel(Context context) {
        mHelper = new StarsTableHelper(context);
    }

    public List<StarMovie> getAll() {
        return mHelper.queryAll();
    }

    public void insertMovie(int id, String name, String imageURL) {
        StarMovie starMovie = new StarMovie(id, name, imageURL);
        mHelper.insert(starMovie);
    }

    public void deleteMovie(int id) {
        mHelper.delete(id);
    }

    public boolean isExist(int id) {
        return mHelper.queryIsExist(id);
    }

    public void close() {
        mHelper.close();
    }
}
