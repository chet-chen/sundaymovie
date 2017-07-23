package com.sunday.sundaymovie.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.activity.MovieDetailActivity;

import static com.sunday.sundaymovie.R.id.refresh_layout;

/**
 * Created by agentchen on 2017/7/23.
 */

public abstract class BaseFragment<T> extends Fragment implements HomeContract.View<T>, SwipeRefreshLayout.OnRefreshListener, ItemClickListener {
    protected HomeContract.Presenter mPresenter;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    private boolean recyclerEmpty = true;
    protected View mNetErrorView;

    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_show_time);
        mRefreshLayout.setOnRefreshListener(this);
        mPresenter.start();
        return root;
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (mRefreshLayout.isRefreshing() != refreshing) {
            mRefreshLayout.setRefreshing(refreshing);
        }
    }

    @Override
    public void snackbar(String text) {
        Snackbar.make(mRefreshLayout, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNetError() {
        if (mNetErrorView == null && recyclerEmpty) {
            View root = getView();
            FrameLayout frameLayout = null;
            if (root != null) {
                frameLayout = (FrameLayout) root.findViewById(R.id.frame_layout);
                mNetErrorView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.net_error, frameLayout, false);
                frameLayout.addView(mNetErrorView);
            }
        }
    }

    @Override
    public void hideNetError() {
        if (mNetErrorView != null && recyclerEmpty) {
            View root = getView();
            if (root != null) {
                FrameLayout frameLayout = (FrameLayout) root.findViewById(R.id.frame_layout);
                frameLayout.removeView(mNetErrorView);
                recyclerEmpty = false;
                mNetErrorView = null;
            }
        }
    }

    @Override
    public void showMovieDetail(int id) {
        MovieDetailActivity.startMe(getActivity(), id);
    }


    @Override
    public void onClick(int id) {
        mPresenter.openMovieDetail(id);
    }

}
