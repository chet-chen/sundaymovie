package com.sunday.sundaymovie.net;

import android.os.Handler;
import android.os.Looper;

import com.sunday.sundaymovie.net.callback.CallBack;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by agentchen on 2017/2/15.
 * Email agentchen97@gmail.com
 */
public class OkManager {
    private static OkManager mOkManager;
    private OkHttpClient client;
    private Handler handler;

    private OkManager() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkManager getInstance() {
        if (mOkManager == null) {
            synchronized (OkManager.class) {
                if (mOkManager == null) {
                    mOkManager = new OkManager();
                }
            }
        }
        return mOkManager;
    }

    public <T> void asyncPost(String url, Map<String, String> params, final CallBack<T> callBack) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody RequestBody = formBuilder.build();
        Request request = new Request.Builder().url(url).post(RequestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final T t = callBack.parseResponse(response);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onResponse(t);
                    }
                });
            }
        });
    }

    public <T> void asyncGet(String url, final CallBack<T> callBack) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final T t = callBack.parseResponse(response);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onResponse(t);
                    }
                });
            }
        });
    }

    public <T> void asyncGetThread(String url, final CallBack<T> callBack) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(callBack.parseResponse(response));
            }
        });
    }
}
