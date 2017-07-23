package com.sunday.sundaymovie.net.callback;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunday.sundaymovie.bean.Person;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 */
public abstract class PersonCallBack implements CallBack<Person> {
    @Override
    public Person parseResponse(Response response) {
        Person person = null;
        if (response != null && response.isSuccessful()) {
            try {
                String jsonStr = response.body().string();
                JsonObject object = new JsonParser().parse(jsonStr)
                        .getAsJsonObject().getAsJsonObject("data").getAsJsonObject("background");
                person = new Gson().fromJson(object, Person.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return person;
    }
}
