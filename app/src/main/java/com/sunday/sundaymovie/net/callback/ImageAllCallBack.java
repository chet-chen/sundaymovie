package com.sunday.sundaymovie.net.callback;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sunday.sundaymovie.model.ImageAll;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 */
public abstract class ImageAllCallBack implements CallBack<ImageAll> {
    @Override
    public ImageAll parseResponse(Response response) {
        ImageAll imageAll = null;
        if (response != null && response.isSuccessful()) {
            try {
                String jsonStr = response.body().string();
                JsonArray array = new JsonParser().parse(jsonStr).getAsJsonObject().getAsJsonArray("images");
                Gson gson = new Gson();
                imageAll = new ImageAll();
                for (JsonElement element : array) {
                    imageAll.addImage(
                            gson.fromJson(element.getAsJsonObject(), ImageAll.Image.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageAll;
    }
}
