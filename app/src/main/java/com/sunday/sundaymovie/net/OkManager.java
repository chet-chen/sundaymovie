package com.sunday.sundaymovie.net;

import com.sunday.sundaymovie.net.converter.Converter;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
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
    private static OkManager INSTANCE;
    private OkHttpClient client;

    private OkManager() {
        client = new OkHttpClient.Builder().build();
    }

    public static OkManager getInstance() {
        if (INSTANCE == null) {
            synchronized (OkManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkManager();
                }
            }
        }
        return INSTANCE;
    }

    public <T> Observable<T> asyncPost(final String url, final Map<String, String> params, final Converter<T> converter) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                FormBody.Builder formBuilder = new FormBody.Builder();
                if (params != null && !params.isEmpty()) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        formBuilder.add(entry.getKey(), entry.getValue());
                    }
                }
                RequestBody RequestBody = formBuilder.build();
                Request request = new Request.Builder().url(url).post(RequestBody).build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    T t = converter.parseResponse(response);
                    e.onNext(t);
                } else {
                    e.onError(new Exception("unsuccessful"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Observable<T> get(final String url, final Converter<T> converter) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    T t = converter.parseResponse(response);
                    e.onNext(t);
                } else {
                    e.onError(new Exception("unsuccessful"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
