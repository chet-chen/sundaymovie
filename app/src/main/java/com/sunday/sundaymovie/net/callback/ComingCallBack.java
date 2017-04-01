package com.sunday.sundaymovie.net.callback;

import com.google.gson.Gson;
import com.sunday.sundaymovie.model.ComingMovies;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 */
public abstract class ComingCallBack implements CallBack<ComingMovies> {
    @Override
    public ComingMovies parseResponse(Response response) {
        ComingMovies comingMovies = null;
        if (response != null && response.isSuccessful()) {
            try {
                String jsonStr = response.body().string();
                comingMovies = new Gson().fromJson(jsonStr, ComingMovies.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return comingMovies;
    }
}
