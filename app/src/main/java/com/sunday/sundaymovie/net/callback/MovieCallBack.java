package com.sunday.sundaymovie.net.callback;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunday.sundaymovie.model.Movie;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 */
public abstract class MovieCallBack implements CallBack<Movie> {
    @Override
    public Movie parseResponse(Response response) {
        Movie movie = null;
        if (response != null && response.isSuccessful()) {
            try {
                String jsonStr = response.body().string();
                JsonObject object = new JsonParser().parse(jsonStr).getAsJsonObject().getAsJsonObject("data");
                movie = new Gson().fromJson(object, Movie.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return movie;
    }
}
