package com.sunday.sundaymovie.net.converter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunday.sundaymovie.bean.Movie;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/6.
 */

public class MovieConverter implements Converter<Movie> {
    @Override
    public Movie parseResponse(Response response) throws IOException {
        String jsonStr = response.body().string();
        JsonObject object = new JsonParser().parse(jsonStr).getAsJsonObject().getAsJsonObject("data");
        return new Gson().fromJson(object, Movie.class);
    }
}
