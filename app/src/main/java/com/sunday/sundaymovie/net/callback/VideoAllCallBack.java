package com.sunday.sundaymovie.net.callback;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sunday.sundaymovie.model.VideoAll;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 */
public abstract class VideoAllCallBack implements CallBack<VideoAll> {
    @Override
    public VideoAll parseResponse(Response response) {
        VideoAll videoAll = null;
        if (response != null && response.isSuccessful()) {
            try {
                String jsonStr = response.body().string();
                JsonArray array = new JsonParser().parse(jsonStr).getAsJsonObject().getAsJsonArray("videoList");
                Gson gson = new Gson();
                videoAll = new VideoAll();
                for (JsonElement element : array) {
                    videoAll.addVideo(
                            gson.fromJson(element.getAsJsonObject(), VideoAll.Video.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return videoAll;
    }
}
