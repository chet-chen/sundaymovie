package com.sunday.sundaymovie.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.util.GlideCacheUtil;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private GlideCacheUtil mGlideCacheUtil;
    private RelativeLayout mRelativeLayout;
    private CheckBox mCheckBox;
    private Button mBtnCleanCache;
    private TextView mTVImgCacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlideCacheUtil = GlideCacheUtil.getInstance();
        mRelativeLayout.setOnClickListener(this);
        mBtnCleanCache.setOnClickListener(this);
        mTVImgCacheSize.setText(mGlideCacheUtil.getChcheSize(this));
    }


    @Override
    protected void initParams(Bundle bundle) {
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_settings);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.settings_item_img_cache);
        mCheckBox = (CheckBox) findViewById(R.id.check_box_img_cache);
        mBtnCleanCache = (Button) findViewById(R.id.btn_clean_cache);
        mTVImgCacheSize = (TextView) findViewById(R.id.tv_img_cache_size);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    public static void startMe(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_item_img_cache:
                mCheckBox.setChecked(!mCheckBox.isChecked());
                break;
            case R.id.btn_clean_cache:
                if (mCheckBox.isChecked()) {
                    mGlideCacheUtil.cleanDiskCache(this);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTVImgCacheSize.setText(mGlideCacheUtil.getChcheSize(getApplicationContext()));
                        }
                    }, 200);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
}
