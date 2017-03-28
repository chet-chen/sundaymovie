package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.RecyclerVideosAdapter;
import com.sunday.sundaymovie.api.Api;
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
    private RecyclerVideosAdapter mAdapter;
    private OnScrollEndListener mOnScrollEndListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTitle);
        mOkManager = OkManager.getInstance();
        mOkManager.asyncGet(Api.getVideoAllUrl(id, pageCount), new VideoAllCallBack() {
            @Override
            public void onResponse(VideoAll response) {
                mVideoAll = response;
                modelToView();
            }

            @Override
            public void onError(Exception e) {
                finish();
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
        mAdapter = new RecyclerVideosAdapter(mVideos, this);
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
