package com.sunday.sundaymovie.net.converter;

import com.google.gson.Gson;
import com.sunday.sundaymovie.bean.VideoAll;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/7.
 */

public class VideoAllConverter implements Converter<VideoAll> {
    @Override
    public VideoAll parseResponse(Response response) throws IOException {
        String jsonStr = response.body().string();
        return new Gson().fromJson(jsonStr, VideoAll.class);
    }
}
