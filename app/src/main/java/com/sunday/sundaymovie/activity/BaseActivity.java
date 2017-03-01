package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by agentchen on 2017/3/1.
 * Email agentchen97@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected boolean isAllowScreenRotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        initParams(bundle);
        initView(this);
        if (!isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    protected abstract void initParams(Bundle bundle);

    protected abstract void initView(Context context);
}
