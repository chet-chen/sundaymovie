package com.sunday.sundaymovie.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.activity.BaseActivity;
import com.sunday.sundaymovie.adapter.ImgGridViewAdapter;
import com.sunday.sundaymovie.bean.Person;
import com.sunday.sundaymovie.expriences.ExpriencesActivity;
import com.sunday.sundaymovie.moviedetail.MovieDetailActivity;
import com.sunday.sundaymovie.photo.PhotoActivity;
import com.sunday.sundaymovie.widget.MyGridView;
import com.sunday.sundaymovie.widget.MyScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/26.
 */

public class PersonActivity extends BaseActivity implements PersonContract.View, View.OnClickListener
        , ImgGridViewAdapter.ItemListener {
    private PersonContract.Presenter mPresenter;
    private boolean isTitleHide = true;
    private GridView mGridView;
    private Button mBtnShowMoreExpriences;
    private RecyclerView mRecyclerView;
    private ImageView mIVMainImg, mIVHotMovieImg, mIVExpriences;
    private ExpandableTextView mExpandableTextView;
    private View mHotMovieGroup;
    private RatingBar mRatingBar;
    private TextView mTVNameCn, mTVNameEn, mTVAddress, mTVBirth, mTVProfession, mTVHotMovieNameCn, mTVHotMovieNameEn, mTVHotMovieType, mTVHotMovieRating, mTVExpriencesTitle, mTVExpriencesContent;
    private Toolbar mToolbar;
    private View mTitleView;
    private MyScrollView mScrollView;
    private ProgressBar mProgressBar;
    private AlphaAnimation mAnimationShow;
    private AlphaAnimation mAnimationHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScrollView.setOnScrollChangedListener(new MyScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (t - oldt > 0) {
                    if (t > 100 && isTitleHide) {
                        showTitle();
                    }
                } else {
                    if (t < 100 && mTitleView.getVisibility() == View.VISIBLE) {
                        hideTitle();
                    }
                }
            }
        });
        mPresenter.start();
    }

    public static void startMe(Context context, int id) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void initParams(Bundle bundle) {
        int id = bundle.getInt("id");
        new PersonPresenter(this, id);
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_person);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mGridView = (MyGridView) findViewById(R.id.grid_view_img);
        /*
        *问题 activity启动，不处在最顶部
        *原因 ScrollView 中嵌套其他可滑动View,如 GridView、RecyclerView 等
        *解决 设置子View setFocusable(false)
        */
        mGridView.setFocusable(false);
        mBtnShowMoreExpriences = (Button) findViewById(R.id.btn_show_more);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_relation_persons);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mIVMainImg = (ImageView) findViewById(R.id.iv_main_img);
        mIVHotMovieImg = (ImageView) findViewById(R.id.iv_hot_movie_img);
        mIVExpriences = (ImageView) findViewById(R.id.iv_expriences_img);
        mTVNameCn = (TextView) findViewById(R.id.tv_name_cn);
        mTVNameEn = (TextView) findViewById(R.id.tv_name_en);
        mTVAddress = (TextView) findViewById(R.id.tv_address_s);
        mTVBirth = (TextView) findViewById(R.id.tv_birth_s);
        mTVProfession = (TextView) findViewById(R.id.tv_profession_s);
        mExpandableTextView = (ExpandableTextView) findViewById(R.id.expandable);
        mTVHotMovieNameCn = (TextView) findViewById(R.id.tv_movie_t);
        mTVHotMovieNameEn = (TextView) findViewById(R.id.tv_movie_t_en);
        mTVHotMovieType = (TextView) findViewById(R.id.tv_movie_type);
        mTVHotMovieRating = (TextView) findViewById(R.id.tv_rating);
        mTVExpriencesTitle = (TextView) findViewById(R.id.tv_expriences_title);
        mTVExpriencesContent = (TextView) findViewById(R.id.tv_expriences_content);
        mRatingBar = (RatingBar) findViewById(R.id.rb_rating);
        mHotMovieGroup = findViewById(R.id.hot_movie_group);
        mScrollView = (MyScrollView) findViewById(R.id.scroll_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTitleView = getToolbarTitle();
        mTitleView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(PersonContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private View getToolbarTitle() {
        int count = mToolbar.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mToolbar.getChildAt(i);
            if (child instanceof TextView) {
                return child;
            }
        }
        return new View(this);
    }

    private void showTitle() {
        isTitleHide = false;
        if (mAnimationShow == null) {
            mAnimationShow = new AlphaAnimation(0f, 1f);
            mAnimationShow.setDuration(500L);
            mAnimationShow.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mTitleView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        mTitleView.startAnimation(mAnimationShow);
    }

    private void hideTitle() {
        isTitleHide = true;
        if (mAnimationHide == null) {
            mAnimationHide = new AlphaAnimation(1f, 0f);
            mAnimationHide.setDuration(500L);
            mAnimationHide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mTitleView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        mTitleView.startAnimation(mAnimationHide);
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
                ((LinearLayout) mProgressBar.getParent()).removeView(mProgressBar);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mProgressBar.startAnimation(animation);
    }

    @Override
    public void showName(String name) {
        setTitle(name);
        mTVNameCn.setText(name);
    }

    @Override
    public void showMainImage(String url) {
        try {
            Glide.with(this).load(url).placeholder(R.drawable.img_load).into(mIVMainImg);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showBasicInfo(String enName, String address, int birthYear, int birthMonth, int birthDay, String profession) {
        mTVNameEn.setText(enName);
        mTVAddress.setText(address);
        mTVBirth.setText(String.format("生日: %d-%d-%d", birthYear, birthMonth, birthDay));
        mTVProfession.setText(profession);
    }

    @Override
    public void showContent(String content) {
        mExpandableTextView.setText(content);
    }

    @Override
    public void hideContent() {
        mExpandableTextView.setVisibility(View.GONE);
        findViewById(R.id.tv_hint_content).setVisibility(View.GONE);
    }

    @Override
    public void showImages(List<String> urls) {
        ImgGridViewAdapter gridViewAdapter = new ImgGridViewAdapter(urls, this, 2);
        gridViewAdapter.setItemListener(this);
        mGridView.setAdapter(gridViewAdapter);
    }

    @Override
    public void hideImages() {
        mGridView.setVisibility(View.GONE);
        findViewById(R.id.tv_hint_img).setVisibility(View.GONE);
    }

    /**
     * @param rating 若小于等于0,则没有评分
     */
    @Override
    public void showHotMovie(String imgUrl, String nameCn, String nameEn, String movieType, double rating) {
        try {
            Glide.with(this).load(imgUrl).placeholder(R.drawable.img_load).into(mIVHotMovieImg);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        mTVHotMovieNameCn.setText(nameCn);
        mTVHotMovieNameEn.setText(nameEn);
        mTVHotMovieType.setText(movieType);
        if (rating > 0) {
            mTVHotMovieRating.setText(String.valueOf(rating));
            mRatingBar.setRating((float) rating / 2);
        } else {
            mRatingBar.setVisibility(View.GONE);
            mTVHotMovieRating.setTextColor(getResources().getColor(R.color.colorTextBlack_3));
            mTVHotMovieRating.setText("暂无评分");
        }
        mHotMovieGroup.setOnClickListener(this);
    }

    @Override
    public void hideHotMovie() {
        mHotMovieGroup.setVisibility(View.GONE);
        findViewById(R.id.tv_hint_hot).setVisibility(View.GONE);
    }

    @Override
    public void showHotMovie(int movieId) {
        MovieDetailActivity.startMe(this, movieId);
    }

    @Override
    public void showExpriences(String imgUrl, int year, String title, String content) {
        try {
            Glide.with(this).load(imgUrl).placeholder(R.drawable.img_load).into(mIVExpriences);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        mTVExpriencesTitle.setText(String.format("%d年 %s", year, title));
        mTVExpriencesContent.setText(content);
        mBtnShowMoreExpriences.setOnClickListener(this);
    }

    @Override
    public void hideExpriences() {
        findViewById(R.id.exprience_group).setVisibility(View.GONE);
    }

    @Override
    public void showAllExpriences(ArrayList<Person.ExpriencesBean> list) {
        ExpriencesActivity.startMe(this, list);
    }

    @Override
    public void showRelationPersons(List<Person.RelationPersonsBean> list) {
        mRecyclerView.setAdapter(new PersonAdapter(list, this));
    }

    @Override
    public void hideRelationPersons() {
        LinearLayout parent = (LinearLayout) mRecyclerView.getParent();
        parent.removeView(findViewById(R.id.tv_hint_relation));
        parent.removeView(mRecyclerView);
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_more:
                mPresenter.openAllExpriences();
                break;
            case R.id.hot_movie_group:
                mPresenter.openHotMovie();
                break;
        }
    }

    @Override
    public void onClickPhoto(int position) {
        mPresenter.openPhoto(position);
    }

    @Override
    public void showPhoto(ArrayList<String> urls, int position) {
        PhotoActivity.startMe(this, urls, position);
    }
}
