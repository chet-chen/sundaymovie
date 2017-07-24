package com.sunday.sundaymovie.net.callback;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sunday.sundaymovie.bean.AllPhoto;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 */
public abstract class ImageAllCallBack implements CallBack<AllPhoto> {
    @Override
    public AllPhoto parseResponse(Response response) {
        AllPhoto allPhoto = null;
        if (response != null && response.isSuccessful()) {
            try {
                String jsonStr = response.body().string();
                JsonArray array = new JsonParser().parse(jsonStr).getAsJsonObject().getAsJsonArray("images");
                Gson gson = new Gson();
                allPhoto = new AllPhoto();
                for (JsonElement element : array) {
                    allPhoto.addImage(
                            gson.fromJson(element.getAsJsonObject(), AllPhoto.Image.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allPhoto;
    }
}
