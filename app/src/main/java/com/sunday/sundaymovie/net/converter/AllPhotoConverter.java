package com.sunday.sundaymovie.net.converter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sunday.sundaymovie.bean.AllPhoto;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/7.
 */

public class AllPhotoConverter implements Converter<AllPhoto> {
    @Override
    public AllPhoto parseResponse(Response response) throws IOException {
        String jsonStr = response.body().string();
        JsonArray array = new JsonParser().parse(jsonStr).getAsJsonObject().getAsJsonArray("images");
        Gson gson = new Gson();
        AllPhoto allPhoto = new AllPhoto();
        for (JsonElement element : array) {
            allPhoto.addImage(
                    gson.fromJson(element.getAsJsonObject(), AllPhoto.Image.class));
        }
        return allPhoto;
    }
}
