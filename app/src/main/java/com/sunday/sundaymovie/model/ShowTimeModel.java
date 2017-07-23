package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.ShowTimeCallBack;

/**
 * Created by agentchen on 2017/7/23.
 */

public class ShowTimeModel {
    private OkManager mOkManager;

    public ShowTimeModel() {
        mOkManager = OkManager.getInstance();
    }

    public void getShowTimeMovies(ShowTimeCallBack callBack) {
        mOkManager.asyncGet(Api.SHOW_TIME_MOVIES, callBack);
    }
}
