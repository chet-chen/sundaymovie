package com.sunday.sundaymovie.mvp.photo;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.widget.HackyViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/31.
 */

public class PhotoActivity extends BaseActivity implements PhotoContract.View, View.OnClickListener {
    public static final String ACTION_DATA_CHANGE = "com.sunday.sundaymovie.ACTION_DATA_CHANGE";
    private PhotoContract.Presenter mPresenter;
    private ViewPager mViewPager;
    private TextView mTextView;
    private ImageButton mButtonDownloadImg;
    private CoordinatorLayout mCoordinatorLayout;
    private PhotoViewPagerAdapter mPagerAdapter;
    private DataChangeReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_DATA_CHANGE);
        mReceiver = new DataChangeReceiver();
        registerReceiver(mReceiver, intentFilter);
        mButtonDownloadImg.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mPresenter.start();
    }

    @Override
    public void setPresenter(PhotoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        isFullScreen = true;
        new PhotoPresenter(this, bundle.getStringArrayList("imgURLs"), bundle.getInt("position"));
    }

    public static void startMe(@NonNull Context context, ArrayList<String> imgURLs, int position) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putStringArrayListExtra("imgURLs", imgURLs);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    public static void dataChange(@NonNull Context context, ArrayList<String> list) {
        Intent intent = new Intent(ACTION_DATA_CHANGE);
        intent.putStringArrayListExtra("imgURLs", list);
        context.sendBroadcast(intent);
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_photo);
        mViewPager = (HackyViewPager) findViewById(R.id.photo_hacky_view_pager);
        mViewPager.setPageTransformer(true, new IntervalTransformer());
        mTextView = (TextView) findViewById(R.id.tv_photo_position_hint);
        mButtonDownloadImg = (ImageButton) findViewById(R.id.btn_download_img);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
    }

    @Override
    public void showPosition(int position, int count) {
        mTextView.setText(position + 1 + " / " + count);
    }

    @Override
    public void showSnackBar(@NonNull String text, String actionText) {
        Snackbar.make(mCoordinatorLayout, text, Snackbar.LENGTH_LONG)
                .setAction(actionText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.openDownloadImage();
                    }
                }).show();
    }

    @Override
    public void showDownloadImage(@NonNull File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            uri = getApplicationContext().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager manager = getPackageManager();
        if (manager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            startActivity(intent);
        } else {
            toast("没有可用的应用");
        }
    }

    @Override
    public void showPhotos(List<String> urls, int startPosition) {
        if (mPagerAdapter == null) {
            mPagerAdapter = new PhotoViewPagerAdapter(this, urls);
            mViewPager.setAdapter(mPagerAdapter);
        } else {
            mPagerAdapter.replaceData(urls);
        }
        if (startPosition >= 0) {
            mViewPager.setCurrentItem(startPosition);
        }
    }

    @Override
    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download_img:
                mPresenter.downloadImage(mViewPager.getCurrentItem());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.clearOnPageChangeListeners();
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private class DataChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_DATA_CHANGE.equals(action)) {
                ArrayList<String> urls = intent.getExtras().getStringArrayList("imgURLs");
                mPresenter.dataChange(urls);
            }
        }
    }

    private class IntervalTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (-1 <= position && position <= 1)
                page.setTranslationX(position * 20);
        }
    }
}
