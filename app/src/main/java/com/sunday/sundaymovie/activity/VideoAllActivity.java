package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.RecyclerVideosAdapter;
import com.sunday.sundaymovie.api.Api;
import com.sunday.sundaymovie.model.VideoAll;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.VideoAllCallBack;

public class VideoAllActivity extends BaseActivity {
    private int id;
    private String title;
    private VideoAll mVideoAll;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerVideosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle(title);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        OkManager.getInstance().asyncGet(Api.getVideoAllYrl(id), new VideoAllCallBack() {
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
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
            title = bundle.getString("title");
        }
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_video_all);
        mToolbar = (Toolbar) findViewById(R.id.video_all_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_videos);
    }

    private void modelToView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerVideosAdapter(mVideoAll.getList(), this));
    }

    public static void startMe(Context context, int id, String title) {
        Intent intent = new Intent(context, VideoAllActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
