package com.sunday.sundaymovie.net.converter;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/6.
 */

public interface Converter<T> {
    T parseResponse(Response response) throws Exception;
}
