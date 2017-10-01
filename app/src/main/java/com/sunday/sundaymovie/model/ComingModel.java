package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.bean.ComingMovies;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.converter.GsonConverter;

import io.reactivex.Observable;

/**
 * Created by agentchen on 2017/7/23.
 */

public class ComingModel {
    private OkManager mOkManager;

    public ComingModel() {
        mOkManager = OkManager.getInstance();
    }

    public Observable<ComingMovies> getComingMovies() {
        return mOkManager.get(Api.COMING_MOVIES, new GsonConverter<>(ComingMovies.class));
    }
}
