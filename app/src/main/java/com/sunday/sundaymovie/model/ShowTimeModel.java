package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.bean.ShowTimeMovies;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.converter.GsonConverter;

import io.reactivex.Observable;

/**
 * Created by agentchen on 2017/7/23.
 */

public class ShowTimeModel {
    private OkManager mOkManager;

    public ShowTimeModel() {
        mOkManager = OkManager.getInstance();
    }

    public Observable<ShowTimeMovies> getShowTimeMovies() {
        return mOkManager.get(Api.SHOW_TIME_MOVIES, new GsonConverter<>(ShowTimeMovies.class));
    }
}
