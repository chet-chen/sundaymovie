package com.sunday.sundaymovie.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.activity.BaseActivity;
import com.sunday.sundaymovie.activity.VideoActivity;
import com.sunday.sundaymovie.allphoto.AllPhotoActivity;
import com.sunday.sundaymovie.allvideo.AllVideoActivity;
import com.sunday.sundaymovie.bean.Movie;
import com.sunday.sundaymovie.photo.PhotoActivity;
import com.sunday.sundaymovie.widget.FollowButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/24.
 */

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View, PhotoAdapter.ItemListener, View.OnClickListener {
    private static final String TAG = "MyMovieDetailActivity";
    private MovieDetailContract.Presenter mPresenter;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RatingBar mRatingBar;
    private RecyclerView mRecyclerActor, mRecyclerPhoto;

    private ImageView mIVTopBgImg, mIVMainImg, mIVMovieVideoImg;
    private TextView mTVMovieName, mTVMovieENName, mTVIs3D, mTVOverallRating, mTVMovieType, mTVMovieDirectorName, mTVMovieDateAndArea, mTVMovieMins, mTVMovieBoxOffice, mTVMovieVideoTitle, mBtnAllVideo;
    private ExpandableTextView mTVMovieStory;
    private ProgressBar mProgressBar;
    private FollowButton mFollowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();
        mBtnAllVideo.setOnClickListener(this);
        mFollowButton.setOnClickListener(this);
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null) {
            new MovieDetailPresenter(this, this, bundle.getInt("movieId"));
        }
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_movie_detail);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));

        Toolbar toolbar = (Toolbar) findViewById(R.id.movie_detail_toolbar);
        setSupportActionBar(toolbar);

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
        mFollowButton = (FollowButton) findViewById(R.id.btn_follow);
        mTVMovieStory = (ExpandableTextView) findViewById(R.id.tv_movie_story);
        mTVMovieDateAndArea = (TextView) findViewById(R.id.tv_movie_release_date_and_area);
        mTVMovieVideoTitle = (TextView) findViewById(R.id.tv_movie_video_title);
        mBtnAllVideo = (TextView) findViewById(R.id.btn_all_video);
        mRatingBar = (RatingBar) findViewById(R.id.rb_overall_rating);
        mRecyclerActor = (RecyclerView) findViewById(R.id.recycler_actor);
        mRecyclerActor.setFocusable(false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerActor.setLayoutManager(linearLayoutManager1);
        mRecyclerActor.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerPhoto = (RecyclerView) findViewById(R.id.recycler_images);
        mRecyclerPhoto.setFocusable(false);
        mRecyclerPhoto.setLayoutManager(linearLayoutManager2);
        mRecyclerPhoto.setNestedScrollingEnabled(false);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static void startMe(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("movieId", movieId);
        context.startActivity(intent);
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
    public void showTopImage(String topImg) {
        Glide.with(this).load(topImg).into(mIVTopBgImg);
    }

    @Override
    public void showMainImage(String mainImg) {
        Glide.with(this).load(mainImg).placeholder(R.drawable.img_load).into(mIVMainImg);
    }

    @Override
    public void showBasicInfo(String movieName, String movieENName, boolean is3D
            , double overallRating, String movieDirectorName
            , String dateAndArea, String movieMins, String movieBoxOffice) {
        mCollapsingToolbarLayout.setTitle(movieName);
        mTVMovieName.setText(movieName);
        mTVMovieENName.setText(movieENName);
        if (overallRating > 0) {
            mRatingBar.setRating((float) (overallRating / 2));
            mTVOverallRating.setText(String.valueOf(overallRating));
        } else {
            mRatingBar.setVisibility(View.GONE);
            mTVOverallRating.setText("暂无评分");
            mTVOverallRating.setTextColor(getResources().getColor(R.color.colorTextBlack_4));
        }
        mTVMovieDirectorName.setText(String.format("导演: %s", movieDirectorName));
        mTVMovieDateAndArea.setText(dateAndArea);
        if (movieMins.isEmpty()) {
            mTVMovieMins.setVisibility(View.GONE);
        } else {
            mTVMovieMins.setText(String.format("片长: %s", movieMins));
        }
        mTVMovieBoxOffice.setText(String.format("累计票房: %s", movieBoxOffice));
        if (is3D) {
            mTVIs3D.setBackground(getResources().getDrawable(R.drawable.text_bg_3d_true));
        }
    }

    @Override
    public void showType(String type) {
        mTVMovieType.setText(type);
    }

    @Override
    public void hideType() {
        mTVMovieType.setVisibility(View.GONE);
    }

    @Override
    public void showMovieStory(String story) {
        mTVMovieStory.setText(story);
    }

    @Override
    public void setFollowed(boolean followed) {
        mFollowButton.setFollowed(followed, false);
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotos(ArrayList<String> urls) {
        mRecyclerPhoto.setAdapter(new PhotoAdapter(urls, this, this));
    }

    @Override
    public void showActor(List<Movie.BasicBean.ActorsBean> list) {
        mRecyclerActor.setAdapter(new ActorAdapter(list, this));
    }

    @Override
    public void showPhoto(ArrayList<String> urls, int position) {
        PhotoActivity.startMe(this, urls, position);
    }

    @Override
    public void showAllPhoto(int id, String title) {
        AllPhotoActivity.startMe(this, id, title);
    }

    @Override
    public void updatePhotos(ArrayList<String> urls) {
        PhotoActivity.dataChange(this, urls);
    }

    @Override
    public void onImageClick(int position) {
        mPresenter.openPhoto(position);
    }

    @Override
    public void onMoreImageClick() {
        mPresenter.openAllPhoto();
    }

    @Override
    public void showVideo(String url, String title) {
        Log.d(TAG, "showVideo: " + url + title);
        VideoActivity.startMe(this, url, title);
    }

    @Override
    public void showAllVideo(int id, String title) {
        AllVideoActivity.startMe(this, id, title);
    }

    @Override
    public void hideVideoInfo() {
        findViewById(R.id.tv_hint_video).setVisibility(View.GONE);
        findViewById(R.id.layout_movie_video).setVisibility(View.GONE);
    }

    @Override
    public void showVideoInfo(String title, String imgUrl) {
        mTVMovieVideoTitle.setText(title);
        try {
            Glide.with(this)
                    .load(imgUrl)
                    .placeholder(R.drawable.img_load)
                    .into(mIVMovieVideoImg);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        mIVMovieVideoImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_follow:
                if (mFollowButton.getFollowed()) {
                    mPresenter.star();
                } else {
                    mPresenter.unStar();
                }
                break;
            case R.id.btn_all_video:
                mPresenter.openAllVideo();
                break;
            case R.id.iv_movie_video_img:
                mPresenter.openVideo();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
