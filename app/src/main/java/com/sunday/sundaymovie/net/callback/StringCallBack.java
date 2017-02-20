package com.sunday.sundaymovie.net.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/1/18.
 * Email agentchen97@gmail.com
 */

public abstract class StringCallBack implements CallBack<String> {
    @Override
    public String parseResponse(Response response) {
        if (response != null && response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
