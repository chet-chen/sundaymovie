package com.sunday.sundaymovie.mvp.allvideo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.bean.VideoAll;
import com.sunday.sundaymovie.mvp.video.VideoActivity;

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
        mScrollEndListener = new OnScrollEndListener();
        mRecyclerView.setOnScrollListener(mScrollEndListener);
        mPresenter.subscribe();
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
        mProgressBar.animate().alpha(0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                ((ViewGroup) mProgressBar.getParent()).removeView(mProgressBar);
                mProgressBar = null;
            }
        });
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
        super.onDestroy();
        if (mScrollEndListener != null) {
            mRecyclerView.removeOnScrollListener(mScrollEndListener);
        }
        mPresenter.unsubscribe();
    }

    @Override
    public void onClickVideo(VideoAll.Video video) {
        mPresenter.openVideo(video);
    }

    private class OnScrollEndListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (recyclerView == null) return;
            if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                    >= recyclerView.computeVerticalScrollRange() - 800) {
                onScrollEnd();
            }
        }

        void onScrollEnd() {
            mPresenter.scrollEnd();
        }
    }
}
