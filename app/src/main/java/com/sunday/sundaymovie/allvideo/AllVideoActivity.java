package com.sunday.sundaymovie.allvideo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.bean.VideoAll;
import com.sunday.sundaymovie.video.VideoActivity;

import java.util.List;

/**
 * Created by agentchen on 2017/8/3.
 */

public class AllVideoActivity extends BaseActivity implements AllVideoContract.View, VideosAdapter.ItemListener {
    private AllVideoContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private VideosAdapter mAdapter;
    private ProgressBar mProgressBar;
    private OnScrollEndListener mScrollEndListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScrollEndListener = new OnScrollEndListener() {
            @Override
            public void onScrollEnd() {
                mPresenter.scrollEnd();
            }
        };
        mRecyclerView.setOnScrollListener(mScrollEndListener);
        mPresenter.start();
    }

    @Override
    public void setPresenter(AllVideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        int id = bundle.getInt("id");
        String title = bundle.getString("title");
        new AllVideoPresenter(this, id, title);
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_all_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.video_all_toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_videos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public static void startMe(Context context, int id, String title) {
        Intent intent = new Intent(context, AllVideoActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
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
    public void showAllVideo(List<VideoAll.Video> list) {
        if (mAdapter == null) {
            mAdapter = new VideosAdapter(list, this, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.replaceData(list);
        }
    }

    @Override
    public void showVideo(String url, String title) {
        VideoActivity.startMe(this, url, title);
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeScrollEndListener() {
        mRecyclerView.removeOnScrollListener(mScrollEndListener);
        mScrollEndListener = null;
    }

    @Override
    public void showTitle(String title) {
        setTitle(title);
    }

    @Override
    protected void onDestroy() {
        if (mScrollEndListener != null) {
            mRecyclerView.removeOnScrollListener(mScrollEndListener);
        }
        super.onDestroy();
    }

    @Override
    public void onClickVideo(int position) {
        mPresenter.openVideo(position);
    }

}
