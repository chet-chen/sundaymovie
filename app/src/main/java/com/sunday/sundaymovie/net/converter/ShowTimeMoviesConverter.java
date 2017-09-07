package com.sunday.sundaymovie.net.converter;

import com.google.gson.Gson;
import com.sunday.sundaymovie.bean.ShowTimeMovies;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/7.
 */

public class ShowTimeMoviesConverter implements Converter<ShowTimeMovies> {
    @Override
    public ShowTimeMovies parseResponse(Response response) throws IOException {
        return new Gson().fromJson(response.body().string(), ShowTimeMovies.class);
    }
}
