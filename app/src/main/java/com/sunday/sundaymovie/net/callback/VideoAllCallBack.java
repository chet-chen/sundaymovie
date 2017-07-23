package com.sunday.sundaymovie.net.callback;

import com.google.gson.Gson;
import com.sunday.sundaymovie.bean.VideoAll;

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
                videoAll = new Gson().fromJson(jsonStr, VideoAll.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return videoAll;
    }
}
