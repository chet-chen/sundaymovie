package com.sunday.sundaymovie.mvp.person;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.GridPhotosAdapter;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.bean.Person;
import com.sunday.sundaymovie.mvp.expriences.ExpriencesActivity;
import com.sunday.sundaymovie.mvp.moviedetail.MovieDetailActivity;
import com.sunday.sundaymovie.mvp.photo.PhotoActivity;
import com.sunday.sundaymovie.widget.MyScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/26.
 */

public class PersonActivity extends BaseActivity implements PersonContract.View, View.OnClickListener
        , GridPhotosAdapter.ItemListener {
    private PersonContract.Presenter mPresenter;
    private boolean isTitleHide = true;
    private Button mBtnShowMoreExpriences;
    private RecyclerView mRecyclerViewRelationPersons, mRecyclerViewImages;
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
        setContentView(R.layout.activity_actor);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mBtnShowMoreExpriences = (Button) findViewById(R.id.btn_show_more);
        mRecyclerViewRelationPersons = (RecyclerView) findViewById(R.id.rv_relation_persons);
        mRecyclerViewImages = (RecyclerView) findViewById(R.id.recycler_view_images);
        mRecyclerViewImages.setNestedScrollingEnabled(false);
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
    public void removeProgressBar() {
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
    public void removeContent() {
        ViewGroup parent = (ViewGroup) mExpandableTextView.getParent();
        parent.removeView(mExpandableTextView);
        parent.removeView(findViewById(R.id.tv_hint_content));
    }

    @Override
    public void showImages(List<String> urls) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewImages.setLayoutManager(gridLayoutManager);
        int space = getResources().getDimensionPixelSize(R.dimen.images_space);
        mRecyclerViewImages.addItemDecoration(new SpaceItemDecoration(space));
        GridPhotosAdapter adapter = new GridPhotosAdapter(urls, this, this);
        mRecyclerViewImages.setAdapter(adapter);
    }

    @Override
    public void removeImages() {
        ViewGroup parent = (ViewGroup) mRecyclerViewImages.getParent();
        parent.removeView(mRecyclerViewImages);
        parent.removeView(findViewById(R.id.tv_hint_img));
    }

    @Override
    public void showHotMovie(String imgUrl, String nameCn, String nameEn, String movieType) {
        try {
            Glide.with(this).load(imgUrl).placeholder(R.drawable.img_load).into(mIVHotMovieImg);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        mTVHotMovieNameCn.setText(nameCn);
        mTVHotMovieNameEn.setText(nameEn);
        mTVHotMovieType.setText(movieType);
        mHotMovieGroup.setOnClickListener(this);
    }

    @Override
    public void showHotMovieRating(double rating) {
        mTVHotMovieRating.setText(String.valueOf(rating));
        mRatingBar.setRating((float) rating / 2);
    }

    @Override
    public void hideHotMovieRating() {
        ((ViewGroup) mRatingBar.getParent()).removeView(mRatingBar);
        mTVHotMovieRating.setTextColor(getResources().getColor(R.color.colorTextBlack_3));
        mTVHotMovieRating.setText("暂无评分");
    }

    @Override
    public void removeHotMovie() {
        ViewGroup parent = (ViewGroup) mHotMovieGroup.getParent();
        parent.removeView(mHotMovieGroup);
        parent.removeView(findViewById(R.id.tv_hint_hot));
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
    public void removeExpriences() {
        View view = findViewById(R.id.exprience_group);
        ((ViewGroup) view.getParent()).removeView(view);
    }

    @Override
    public void showAllExpriences(ArrayList<Person.ExpriencesBean> list) {
        ExpriencesActivity.startMe(this, list);
    }

    @Override
    public void showRelationPersons(List<Person.RelationPersonsBean> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewRelationPersons.setLayoutManager(linearLayoutManager);
        mRecyclerViewRelationPersons.setAdapter(new PersonAdapter(list, this));
    }

    @Override
    public void removeRelationPersons() {
        ViewGroup parent = (ViewGroup) mRecyclerViewRelationPersons.getParent();
        parent.removeView(mRecyclerViewRelationPersons);
        parent.removeView(findViewById(R.id.tv_hint_relation));
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

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int mSpace;

        SpaceItemDecoration(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position > 1) {
                outRect.top = mSpace;
            }
            if (position % 2 == 0) {
                outRect.right = mSpace;
            }
        }
    }
}
