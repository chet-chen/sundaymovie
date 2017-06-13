package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.VideosAdapter;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.listener.OnScrollEndListener;
import com.sunday.sundaymovie.model.VideoAll;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.VideoAllCallBack;

import java.util.List;

public class VideoAllActivity extends BaseActivity {
    private int id;
    private String mTitle;
    private int pageCount = 1;
    private boolean loading = false;

    private OkManager mOkManager;
    private VideoAll mVideoAll;
    private List<VideoAll.Video> mVideos;

    private RecyclerView mRecyclerView;
    private VideosAdapter mAdapter;
    private OnScrollEndListener mOnScrollEndListener;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTitle);
        mOkManager = OkManager.getInstance();
        mOkManager.asyncGet(Api.getVideoAllUrl(id, pageCount), new VideoAllCallBack() {
            @Override
            public void onResponse(VideoAll response) {
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
                mVideoAll = response;
                modelToView();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(VideoAllActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        mOnScrollEndListener = new OnScrollEndListener() {
            @Override
            public void onScrollEnd() {
                if (pageCount < mVideoAll.getTotalPageCount() && !loading) {
                    loading = true;
                    mOkManager.asyncGet(Api.getVideoAllUrl(id, ++pageCount), new VideoAllCallBack() {
                        @Override
                        public void onResponse(VideoAll response) {
                            mVideos.addAll(response.getVideoList());
                            mAdapter.notifyDataSetChanged();
                            loading = false;
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                            pageCount--;
                            loading = false;
                        }
                    });
                } else if (pageCount == mVideoAll.getTotalPageCount()) {
                    mRecyclerView.removeOnScrollListener(mOnScrollEndListener);
                }
            }
        };
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
            mTitle = bundle.getString("title");
        }
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_video_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.video_all_toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_videos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        mVideos = mVideoAll.getVideoList();
        mAdapter = new VideosAdapter(mVideos, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollEndListener);
    }

    public static void startMe(Context context, int id, String title) {
        Intent intent = new Intent(context, VideoAllActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecyclerView.removeOnScrollListener(mOnScrollEndListener);
    }
}
