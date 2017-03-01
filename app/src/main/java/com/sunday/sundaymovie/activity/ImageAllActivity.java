package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.ImgAllGridViewAdapter;
import com.sunday.sundaymovie.api.Api;
import com.sunday.sundaymovie.model.ImageAll;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.ImageAllCallBack;

import java.util.ArrayList;
import java.util.List;

public class ImageAllActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private GridView mGridView;
    private int mMovieId;
    private String mTitle;
    private ImageAll mImageAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_all);
        init();
        Bundle bundle = getIntent().getExtras();
        mMovieId = bundle.getInt("movieId");
        mTitle = bundle.getString("title");
        OkManager.getInstance().asyncGet(Api.getImageAllUrl(mMovieId), new ImageAllCallBack() {
            @Override
            public void onResponse(ImageAll response) {
                mImageAll = response;
                modelToView();
            }

            @Override
            public void onError(Exception e) {
                finish();
            }
        });
        mToolbar.setTitle(mTitle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void modelToView() {
        mGridView.setAdapter(new ImgAllGridViewAdapter(getImgUrls(), this));
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.image_all_toolbar);
        mGridView = (GridView) findViewById(R.id.image_all_grid_view);
    }

    private List<String> getImgUrls() {
        List<String> list = new ArrayList<>(mImageAll.getImages().size());
        for (ImageAll.Image image : mImageAll.getImages()) {
            list.add(image.getImage());
        }
        return list;
    }

    public static void startMe(Context context, int movieId, String movieName) {
        Intent intent = new Intent(context, ImageAllActivity.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("title", movieName);
        context.startActivity(intent);
    }
}
