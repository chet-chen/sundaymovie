package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.ImgGridViewAdapter;
import com.sunday.sundaymovie.model.ImageAll;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.ImageAllCallBack;

import java.util.ArrayList;
import java.util.List;

public class PhotoAllActivity extends BaseActivity {
    private GridView mGridView;
    private int mMovieId;
    private String mTitle;
    private ImageAll mImageAll;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTitle);
        OkManager.getInstance().asyncGet(Api.getImageAllUrl(mMovieId), new ImageAllCallBack() {
            @Override
            public void onResponse(ImageAll response) {
                AlphaAnimation animation = new AlphaAnimation(1f, 0f);
                animation.setDuration(300L);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ((LinearLayout) mProgressBar.getParent()).removeView(mProgressBar);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mProgressBar.startAnimation(animation);
                mImageAll = response;
                modelToView();
            }

            @Override
            public void onError(Exception e) {
                finish();
            }
        });
    }

    @Override
    protected void initParams(Bundle bundle) {
        mMovieId = bundle.getInt("movieId");
        mTitle = bundle.getString("title");
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_photo_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.image_all_toolbar);
        setSupportActionBar(toolbar);
        mGridView = (GridView) findViewById(R.id.image_all_grid_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void modelToView() {
        ImgGridViewAdapter adapter = new ImgGridViewAdapter(getImgUrls(), this, 3);
        mGridView.setAdapter(adapter);
        setTitle(mTitle + "  " + adapter.getCount() + "张");
    }

    private List<String> getImgUrls() {
        List<String> list = new ArrayList<>(mImageAll.getImages().size());
        for (ImageAll.Image image : mImageAll.getImages()) {
            list.add(image.getImage());
        }
        return list;
    }

    public static void startMe(Context context, int movieId, String movieName) {
        Intent intent = new Intent(context, PhotoAllActivity.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("title", movieName);
        context.startActivity(intent);
    }
}
