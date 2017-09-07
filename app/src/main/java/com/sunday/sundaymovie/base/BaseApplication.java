package com.sunday.sundaymovie.base;

import android.app.Application;
import android.content.Context;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by agentchen on 2017/9/3.
 */

public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable != null) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    public static Context getContext() {
        return baseApplication.getApplicationContext();
    }
}
