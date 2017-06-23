package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
import com.sunday.sundaymovie.adapter.ActorAdapter;
import com.sunday.sundaymovie.db.StarsTableHelper;
import com.sunday.sundaymovie.model.ImageAll;
import com.sunday.sundaymovie.model.Movie;
import com.sunday.sundaymovie.model.StarsMovie;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.ImageAllCallBack;
import com.sunday.sundaymovie.net.callback.MovieCallBack;
import com.sunday.sundaymovie.util.StringFormatUtil;
import com.sunday.sundaymovie.widget.FollowButton;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends BaseActivity implements View.OnClickListener {
    private int mMovieId = 99547;
    private OkManager mOkManager;
    private Movie mMovie;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RatingBar mRatingBar;
    private RecyclerView mRecyclerView;

    private ImageView mIVTopBgImg, mIVMainImg, mIVMovieVideoImg;
    private TextView mTVMovieName, mTVMovieENName, mTVIs3D, mTVOverallRating, mTVMovieType, mTVMovieDirectorName, mTVMovieDateAndArea, mTVMovieMins, mTVMovieBoxOffice, mTVMovieVideoTitle, mBtnAllImage, mBtnAllVideo;
    private ExpandableTextView mTVMovieStory;
    private ImageView[] mIVMovieImgs = new ImageView[4];
    private ArrayList<String> mImgsList;
    private ProgressBar mProgressBar;
    private FollowButton mFollowButton;
    private StarsTableHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkManager = OkManager.getInstance();
        helper = new StarsTableHelper(this);
        mBtnAllImage.setOnClickListener(this);
        mBtnAllVideo.setOnClickListener(this);
        mFollowButton.setOnClickListener(this);
        getData();
        mFollowButton.setFollowed(helper.queryIsExist(mMovieId), false);
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null) {
            mMovieId = bundle.getInt("movieId");
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
        mBtnAllImage = (TextView) findViewById(R.id.tv_btn_all_img);
        mBtnAllVideo = (TextView) findViewById(R.id.btn_all_video);
        mRatingBar = (RatingBar) findViewById(R.id.rb_overall_rating);
        mIVMovieImgs = new ImageView[]{(ImageView) findViewById(R.id.iv_movie_img_1)
                , (ImageView) findViewById(R.id.iv_movie_img_2)
                , (ImageView) findViewById(R.id.iv_movie_img_3)
                , (ImageView) findViewById(R.id.iv_movie_img_4)};
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_actor);
        mRecyclerView.setFocusable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
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

    private void modelToView() {
        List<Movie.BasicBean.StageImgBean.ListBean> imgs = mMovie.getBasic().getStageImg().getList();
        if (imgs.size() != 0) {
            /*Glide.with(Activity activity) 内部有这样的代码：
             * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())
             *   throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
             * 所以在with之前destroy了Activity就会抛异常,这里加上判断来解决这个问题
             */
            try {
                Glide.with(this)
                        .load(imgs.get(0).getImgUrl())
                        .into(mIVTopBgImg);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        try {
            Glide.with(this)
                    .load(mMovie.getBasic().getImg())
                    .placeholder(R.drawable.img_load)
                    .into(mIVMainImg);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        mCollapsingToolbarLayout.setTitle(mMovie.getBasic().getName());
        mTVMovieName.setText(mMovie.getBasic().getName());
        mTVMovieENName.setText(mMovie.getBasic().getNameEn());
        mRatingBar.setRating((float) (mMovie.getBasic().getOverallRating() / 2));
        if (mMovie.getBasic().getOverallRating() > 0) {
            mTVOverallRating.setText(String.valueOf(mMovie.getBasic().getOverallRating()));
        } else {
            mRatingBar.setVisibility(View.GONE);
            mTVOverallRating.setText("暂无评分");
            mTVOverallRating.setTextColor(getResources().getColor(R.color.colorTextBlack_4));
        }
        mTVMovieType.setText(StringFormatUtil.getMovieType(mMovie.getBasic().getType()));
        mTVMovieDirectorName.setText(String.format("导演: %s", mMovie.getBasic().getDirector().getName()));
        mTVMovieDateAndArea.setText(StringFormatUtil.getMovieReleaseText(
                mMovie.getBasic().getReleaseDate(), mMovie.getBasic().getReleaseArea()));
        String movieMin = mMovie.getBasic().getMins();
        if (movieMin.isEmpty()) {
            mTVMovieMins.setVisibility(View.GONE);
        } else {
            mTVMovieMins.setText(String.format("片长: %s", movieMin));
        }
        mTVMovieBoxOffice.setText(String.format("累计票房: %s", mMovie.getBoxOffice().getTotalBoxDes()));
        mTVMovieStory.setText(mMovie.getBasic().getStory());

        if (mMovie.getBasic().isIs3D()) {
            mTVIs3D.setBackground(getResources().getDrawable(R.drawable.text_bg_3d_true));
        }
//        如果此电影没有视频，则去除视频相关view
        if (mMovie.getBasic().getVideo().getCount() == 0) {
            findViewById(R.id.tv_hint_video).setVisibility(View.GONE);
            findViewById(R.id.layout_movie_video).setVisibility(View.GONE);
        } else {
            mTVMovieVideoTitle.setText(mMovie.getBasic().getVideo().getTitle());
            try {
                Glide.with(this)
                        .load(mMovie.getBasic().getVideo().getImg())
                        .placeholder(R.drawable.img_load)
                        .into(mIVMovieVideoImg);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            mIVMovieVideoImg.setOnClickListener(this);
        }

        List<Movie.BasicBean.StageImgBean.ListBean> listBean = mMovie.getBasic().getStageImg().getList();
        mImgsList = new ArrayList<>(4);
        for (int i = 0; i < listBean.size() && i < 4; i++) {
            try {
                Glide.with(this)
                        .load(listBean.get(i).getImgUrl())
                        .placeholder(R.drawable.img_load)
                        .into(mIVMovieImgs[i]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            mImgsList.add(listBean.get(i).getImgUrl());
        }
        if (listBean.size() < 4) {
            for (int i = 3; i >= listBean.size(); i--) {
                mIVMovieImgs[i].setVisibility(View.GONE);
            }
        }
        for (ImageView mIVMovieImg : mIVMovieImgs) {
            mIVMovieImg.setOnClickListener(this);
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
        mRecyclerView.setAdapter(new ActorAdapter(list, this));
    }

    public static void startMe(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("movieId", movieId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_movie_video_img:
                VideoActivity.startMe(MovieDetailActivity.this
                        , mMovie.getBasic().getVideo().getHightUrl()
                        , mMovie.getBasic().getVideo().getTitle());
                break;
            case R.id.tv_btn_all_img:
                PhotoAllActivity.startMe(MovieDetailActivity.this, mMovieId, mMovie.getBasic().getName());
                break;
            case R.id.btn_all_video:
                VideoAllActivity.startMe(MovieDetailActivity.this, mMovieId, mMovie.getBasic().getName());
                break;
            case R.id.iv_movie_img_1:
                imgClick(0);
                break;
            case R.id.iv_movie_img_2:
                imgClick(1);
                break;
            case R.id.iv_movie_img_3:
                imgClick(2);
                break;
            case R.id.iv_movie_img_4:
                imgClick(3);
                break;
            case R.id.btn_follow:
                if (mFollowButton.getFollowed()) {
                    helper.insert(new StarsMovie(mMovieId, mMovie.getBasic().getName(), mMovie.getBasic().getImg()));
                } else {
                    helper.delete(mMovieId);
                }
                break;
            default:
                break;
        }
    }

    private void imgClick(final int index) {
        PhotoActivity.startMe(this, mImgsList, index);
        if (mImgsList.size() <= 4) {
            mOkManager.asyncGetThread(Api.getImageAllUrl(mMovieId), new ImageAllCallBack() {
                @Override
                public void onResponse(ImageAll response) {
                    for (ImageAll.Image image : response.getImages()) {
                        mImgsList.add(image.getImage());
                    }
                    PhotoActivity.dataChange(MovieDetailActivity.this, mImgsList);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

    }

    private void getData() {
        mOkManager.asyncGet(Api.getMovieUrl(mMovieId), new MovieCallBack() {
            @Override
            public void onResponse(Movie response) {
                AlphaAnimation animation = new AlphaAnimation(1f, 0f);
                animation.setDuration(300L);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ((CoordinatorLayout) mProgressBar.getParent()).removeView(mProgressBar);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mProgressBar.startAnimation(animation);
                mMovie = response;
                if (mMovie == null) {
                    finish();
                }
                modelToView();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(MovieDetailActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
