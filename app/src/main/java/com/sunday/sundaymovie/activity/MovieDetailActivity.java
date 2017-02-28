package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.RecyclerActorAdapter;
import com.sunday.sundaymovie.api.Api;
import com.sunday.sundaymovie.model.Movie;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.MovieCallBack;
import com.sunday.sundaymovie.util.StringFormatUtil;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private int mMovieId = 220423;
    private Movie mMovie;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RatingBar mRatingBar;
    private RecyclerView mRecyclerView;

    private ImageView mIVTopBgImg;
    private ImageView mIVMainImg;
    private ImageView mIVMovieVideoImg;
    private TextView mTVMovieName;
    private TextView mTVMovieENName;
    private TextView mTVIs3D;
    private TextView mTVOverallRating;
    private TextView mTVMovieType;
    private TextView mTVMovieDirectorName;
    private TextView mTVMovieDateAndArea;
    private TextView mTVMovieMins;
    private TextView mTVMovieBoxOffice;
    private ExpandableTextView mTVMovieStory;
    private TextView mTVMovieVideoTitle;

    private ImageView[] mIVMovieImgs = new ImageView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("movieId")) {
            mMovieId = bundle.getInt("movieId");
        }

        init();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTop();
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

        mIVTopBgImg = (ImageView) findViewById(R.id.iv_top_bg_img);
        mIVMainImg = (ImageView) findViewById(R.id.iv_main_img);
        mIVMovieVideoImg = (ImageView) findViewById(R.id.iv_movie_video_img);

        mTVIs3D = (TextView) findViewById(R.id.tv_is_3D);
        mTVMovieName = (TextView) findViewById(R.id.tv_movie_name);

        mTVMovieENName = (TextView) findViewById(R.id.tv_movie_en_name);
        mTVMovieDirectorName = (TextView) findViewById(R.id.tv_movie_director_name);
        mTVMovieType = (TextView) findViewById(R.id.tv_movie_type);
        mTVMovieBoxOffice = (TextView) findViewById(R.id.tv_movie_box_office);
        mTVMovieMins = (TextView) findViewById(R.id.tv_movie_mins);
        mTVOverallRating = (TextView) findViewById(R.id.tv_overall_rating);
        mTVMovieStory = (ExpandableTextView) findViewById(R.id.tv_movie_story);
        mTVMovieDateAndArea = (TextView) findViewById(R.id.tv_movie_release_date_and_area);
        mTVMovieVideoTitle = (TextView) findViewById(R.id.tv_movie_video_title);

        mRatingBar = (RatingBar) findViewById(R.id.rb_overall_rating);
        ((LayerDrawable) mRatingBar.getProgressDrawable()).getDrawable(2)
                .setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        mIVMovieImgs = new ImageView[]{(ImageView) findViewById(R.id.iv_movie_img_1)
                , (ImageView) findViewById(R.id.iv_movie_img_2)
                , (ImageView) findViewById(R.id.iv_movie_img_3)
                , (ImageView) findViewById(R.id.iv_movie_img_4)};

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_actor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        OkManager.getInstance().asyncGet(Api.getMovieUrl(mMovieId), new MovieCallBack() {
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
                e.printStackTrace();
                finish();
            }
        });
    }

    private void modelToView() {
        Glide.with(this)
                .load(mMovie.getBasic().getStageImg().getList().get(0).getImgUrl())
                .into(mIVTopBgImg);
        Glide.with(this)
                .load(mMovie.getBasic().getImg())
                .into(mIVMainImg);
        mCollapsingToolbarLayout.setTitle(mMovie.getBasic().getName());
        mTVMovieName.setText(mMovie.getBasic().getName());
        mTVMovieENName.setText(mMovie.getBasic().getNameEn());
        mRatingBar.setRating((float) (mMovie.getBasic().getOverallRating() / 2));
        mTVOverallRating.setText(String.format("%s 分", String.valueOf(mMovie.getBasic().getOverallRating())));
        mTVMovieType.setText(StringFormatUtil.getMovieType(mMovie.getBasic().getType()));
        mTVMovieDirectorName.setText(String.format("导演: %s", mMovie.getBasic().getDirector().getName()));
        mTVMovieDateAndArea.setText(StringFormatUtil.getMovieReleaseText(
                mMovie.getBasic().getReleaseDate(), mMovie.getBasic().getReleaseArea()));
        String movieMin;
        movieMin = mMovie.getBasic().getMins();
        if (movieMin.isEmpty()) {
            movieMin = "--";
        }
        mTVMovieMins.setText(String.format("片长: %s", movieMin));
        mTVMovieBoxOffice.setText(String.format("累计票房: %s", mMovie.getBoxOffice().getTotalBoxDes()));
        mTVMovieStory.setText(mMovie.getBasic().getStory());

        if (mMovie.getBasic().isIs3D()) {
            mTVIs3D.setBackground(getResources().getDrawable(R.drawable.text_bg_3d_true));
        } else {
            mTVIs3D.setBackground(getResources().getDrawable(R.drawable.text_bg_3d_false));
        }
//        如果此电影没有视频，则隐藏视频相关view
        if (mMovie.getBasic().getVideo().getCount() == 0) {
            findViewById(R.id.final_video_str).setVisibility(View.GONE);
            findViewById(R.id.layout_movie_video).setVisibility(View.GONE);
        } else {
            mTVMovieVideoTitle.setText(mMovie.getBasic().getVideo().getTitle());
            Glide.with(this)
                    .load(mMovie.getBasic().getVideo().getImg())
                    .into(mIVMovieVideoImg);
        }

        List<Movie.BasicBean.StageImgBean.ListBean> listBean = mMovie.getBasic().getStageImg().getList();
        final ArrayList<String> imgsList = new ArrayList<>(4);
        for (int i = 0; i < listBean.size() || i < 4; i++) {
            Glide.with(this)
                    .load(listBean.get(i).getImgUrl())
                    .into(mIVMovieImgs[i]);
            imgsList.add(listBean.get(i).getImgUrl());
        }
        for (int i = 0; i < mIVMovieImgs.length; i++) {
            final int finalI = i;
            mIVMovieImgs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoActivity.startMe(MovieDetailActivity.this, imgsList, finalI);
                }
            });
        }

        List<Movie.BasicBean.ActorsBean> list = mMovie.getBasic().getActors();
//        把导演插入到演员list
        Movie.BasicBean.ActorsBean ab = new Movie.BasicBean.ActorsBean();
        Movie.BasicBean.DirectorBean directorBean = mMovie.getBasic().getDirector();
        ab.setActorId(directorBean.getDirectorId());
        ab.setImg(directorBean.getImg());
        ab.setName(directorBean.getName());
        ab.setRoleName("导演");
        list.add(0, ab);
        mRecyclerView.setAdapter(new RecyclerActorAdapter(list, this));
    }

    public static void startMe(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("movieId", movieId);
        context.startActivity(intent);
    }

    private void setTop() {
        /*
        * 问题 activity启动，不处在最顶部
        * 解决 获取最顶部view焦点
        */
        mTVMovieName.setFocusable(true);
        mTVMovieName.setFocusableInTouchMode(true);
        mTVMovieName.requestFocus();
    }

}
