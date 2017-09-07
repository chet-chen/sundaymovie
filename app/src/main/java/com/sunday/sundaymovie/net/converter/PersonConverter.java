package com.sunday.sundaymovie.net.converter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunday.sundaymovie.bean.Person;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/7.
 */

public class PersonConverter implements Converter<Person> {
    @Override
    public Person parseResponse(Response response) throws IOException {
        String jsonStr = response.body().string();
        JsonObject object = new JsonParser().parse(jsonStr)
                .getAsJsonObject().getAsJsonObject("data").getAsJsonObject("background");
        return new Gson().fromJson(object, Person.class);
    }
}
