package com.sunday.sundaymovie.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by agentchen on 2017/9/3.
 */

public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getContext() {
        return baseApplication.getApplicationContext();
    }
}
