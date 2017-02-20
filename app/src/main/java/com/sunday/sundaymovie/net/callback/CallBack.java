package com.sunday.sundaymovie.net.callback;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/1/18.
 * Email agentchen97@gmail.com
 */

public interface CallBack<T> {
    T parseResponse(Response response);

    void onResponse(T response);

    void onError(Exception e);
}
