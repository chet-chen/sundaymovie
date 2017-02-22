package com.sunday.sundaymovie.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.RecyclerActorAdapter;
import com.sunday.sundaymovie.api.Api;
import com.sunday.sundaymovie.model.Movie;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.MovieCallBack;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private int mMovieId;
    private Movie mMovie;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ActionBar mActionBar;
    private RatingBar mRatingBar;
    private RecyclerView mRecyclerView;

    private int[] mImageViewMovieImgIds = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        init();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCollapsingToolbarLayout.setTitle("盗梦空间");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.activity_toolbar_menu);

        mRatingBar = (RatingBar) findViewById(R.id.rb_overall_rating);
        ((LayerDrawable) mRatingBar.getProgressDrawable()).getDrawable(2)
                .setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        mImageViewMovieImgIds = new int[]{R.id.iv_movie_img_1, R.id.iv_movie_img_2
                , R.id.iv_movie_img_3, R.id.iv_movie_img_4};

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_actor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        OkManager.getInstance().asyncGet(Api.getMovieUrl(99547), new MovieCallBack() {
            @Override
            public void onResponse(Movie response) {
                mMovie = response;
                if (mMovie == null) {
                    finish();
                }
                modelToView();
            }

            @Override
            public void onError(Exception e) {
                finish();
            }
        });
    }

    private void modelToView() {
        List<Movie.BasicBean.StageImgBean.ListBean> listBean = mMovie.getBasic().getStageImg().getList();
        for (int i = 0; i < listBean.size() || i < 4; i++) {
            Glide.with(this)
                    .load(listBean.get(i).getImgUrl())
                    .into((ImageView) findViewById(mImageViewMovieImgIds[i]));
        }
        mRecyclerView.setAdapter(new RecyclerActorAdapter(mMovie.getBasic().getActors(), this));
    }

}
