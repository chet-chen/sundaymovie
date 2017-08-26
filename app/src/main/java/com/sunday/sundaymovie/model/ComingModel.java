package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.ComingCallBack;

/**
 * Created by agentchen on 2017/7/23.
 */

public class ComingModel {
    private OkManager mOkManager;

    public ComingModel() {
        mOkManager = OkManager.getInstance();
    }

    public void getComingMovies(ComingCallBack callBack) {
        mOkManager.asyncGet(Api.COMING_MOVIES, callBack);
    }
}
