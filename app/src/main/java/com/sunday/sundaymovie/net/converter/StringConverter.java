package com.sunday.sundaymovie.net.converter;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/7.
 */

public class StringConverter implements Converter<String> {
    @Override
    public String parseResponse(Response response) throws Exception {
        return response.body().string();
    }
}
