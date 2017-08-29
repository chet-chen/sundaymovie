package com.sunday.sundaymovie.mvp.star;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.bean.StarMovie;
import com.sunday.sundaymovie.mvp.moviedetail.MovieDetailActivity;

import java.util.List;

/**
 * Created by agentchen on 2017/8/1.
 */

public class StarActivity extends BaseActivity implements StarContract.View, StarsMovieAdapter.ItemListener {
    private StarContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private StarsMovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void setPresenter(StarContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        new StarPresenter(this);
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_star);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public static void startMe(Context context) {
        Intent intent = new Intent(context, StarActivity.class);
        context.startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showStarMovie(List<StarMovie> list) {
        if (mAdapter == null) {
            mAdapter = new StarsMovieAdapter(this, list, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.replaceData(list);
        }
    }

    @Override
    public void onClick(int position) {
        mPresenter.openMovie(position);
    }

    @Override
    public void showMovie(int id) {
        MovieDetailActivity.startMe(this, id);
    }

}
