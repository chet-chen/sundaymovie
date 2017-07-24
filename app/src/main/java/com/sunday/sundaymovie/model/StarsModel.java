package com.sunday.sundaymovie.model;

import android.content.Context;

import com.sunday.sundaymovie.bean.StarsMovie;
import com.sunday.sundaymovie.db.StarsTableHelper;

/**
 * Created by agentchen on 2017/7/23.
 */

public class StarsModel {
    private StarsTableHelper mHelper;

    public StarsModel(Context context) {
        mHelper = new StarsTableHelper(context);
    }

    public void insertMovie(int id, String name, String imageURL) {
        StarsMovie starsMovie = new StarsMovie(id, name, imageURL);
        mHelper.insert(starsMovie);
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
