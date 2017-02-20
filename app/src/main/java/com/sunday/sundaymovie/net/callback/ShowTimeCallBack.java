package com.sunday.sundaymovie.net.callback;

import com.google.gson.Gson;
import com.sunday.sundaymovie.model.ShowTimeMovies;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 */
public abstract class ShowTimeCallBack implements CallBack<ShowTimeMovies> {
    @Override
    public ShowTimeMovies parseResponse(Response response) {
        ShowTimeMovies showTimeMovies = null;
        if (response != null && response.isSuccessful()) {
            try {
                showTimeMovies = new Gson().fromJson(response.body().string(), ShowTimeMovies.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return showTimeMovies;
    }
}
