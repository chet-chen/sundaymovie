package com.sunday.sundaymovie.net.converter;

import com.google.gson.Gson;
import com.sunday.sundaymovie.bean.ComingMovies;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/7.
 */

public class ComingMoviesConverter implements Converter<ComingMovies> {
    @Override
    public ComingMovies parseResponse(Response response) throws IOException {
        String jsonStr = response.body().string();
        return new Gson().fromJson(jsonStr, ComingMovies.class);
    }
}
