package com.sunday.sundaymovie.net.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/1/18.
 * Email agentchen97@gmail.com
 */

public abstract class BytesCallBack implements CallBack<byte[]> {
    @Override
    public byte[] parseResponse(Response response) {
        if (response != null && response.isSuccessful()) {
            try {
                return response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
