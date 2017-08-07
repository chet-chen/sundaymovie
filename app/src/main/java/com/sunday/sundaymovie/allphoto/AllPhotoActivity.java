package com.sunday.sundaymovie.allphoto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.adapter.ImgGridViewAdapter;
import com.sunday.sundaymovie.photo.PhotoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/31.
 */

public class AllPhotoActivity extends BaseActivity implements AllPhotoContract.View, ImgGridViewAdapter.ItemListener {
    private AllPhotoContract.Presenter mPresenter;
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private ImgGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void setPresenter(AllPhotoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        new AllPhotoPresenter(this, bundle.getInt("movieId"), bundle.getString("title"));
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_photo_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.image_all_toolbar);
        setSupportActionBar(toolbar);
        mGridView = (GridView) findViewById(R.id.image_all_grid_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public static void startMe(Context context, int movieId, String title) {
        Intent intent = new Intent(context, AllPhotoActivity.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public void showTitle(String title) {
        setTitle(title);
    }

    @Override
    public void hideProgressBar() {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(300L);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ViewGroup) mProgressBar.getParent()).removeView(mProgressBar);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mProgressBar.startAnimation(animation);
    }

    @Override
    public void showAllImage(List<String> urls) {
        if (mAdapter == null) {
            mAdapter = new ImgGridViewAdapter(urls, this, 3);
            mAdapter.setItemListener(this);
        }
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhoto(ArrayList<String> urls, int position) {
        PhotoActivity.startMe(this, urls, position);
    }

    @Override
    public void onClickPhoto(int position) {
        mPresenter.openPhoto(position);
    }
}
