package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.base.BaseApplication;
import com.sunday.sundaymovie.bean.StarMovie;
import com.sunday.sundaymovie.db.StarTableHelper;

import java.util.List;

/**
 * Created by agentchen on 2017/7/23.
 */

public class StarModel {
    private static StarTableHelper mHelper;

    public StarModel() {
        mHelper = new StarTableHelper(BaseApplication.getContext());
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
}
