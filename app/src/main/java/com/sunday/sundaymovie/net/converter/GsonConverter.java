package com.sunday.sundaymovie.net.converter;

import com.google.gson.Gson;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/30.
 */

public class GsonConverter<T> implements Converter<T> {

    private Class<T> mClass;

    public GsonConverter(Class<T> tClass) {
        mClass = tClass;
    }

    @Override
    public T parseResponse(Response response) throws Exception {
        return new Gson().fromJson(response.body().string(), mClass);
    }

}
