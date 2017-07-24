package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.MovieCallBack;

/**
 * Created by agentchen on 2017/7/24.
 */

public class MovieDetailModel {
    public void getMovieDetail(int movieId, MovieCallBack callBack) {
        OkManager.getInstance().asyncGet(Api.getMovieUrl(movieId), callBack);
    }
}
