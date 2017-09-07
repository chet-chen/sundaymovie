package com.sunday.sundaymovie.mvp.home;

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
import com.sunday.sundaymovie.mvp.moviedetail.MovieDetailActivity;

import static com.sunday.sundaymovie.R.id.refresh_layout;

/**
 * Created by agentchen on 2017/7/23.
 */

public abstract class BaseFragment<T> extends Fragment implements HomeContract.View<T>, SwipeRefreshLayout.OnRefreshListener, ItemListener {
    protected HomeContract.Presenter mPresenter;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    private boolean mRecyclerEmpty = true, mIsReady = false;
    protected View mNetErrorView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mRefreshLayout = root.findViewById(refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRecyclerView = root.findViewById(R.id.recycler_view_show_time);
        mRefreshLayout.setOnRefreshListener(this);
        if (mPresenter == null) {
            recreatePresenter();
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mIsReady = true;
        if (getUserVisibleHint()) {
            mPresenter.start();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mIsReady && isVisibleToUser) {
            mPresenter.start();
        }
    }

    protected abstract void recreatePresenter();

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
    public void snackBar(String text) {
        Snackbar.make(mRefreshLayout, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNetError() {
        if (mNetErrorView == null && mRecyclerEmpty) {
            View root = getView();
            if (root != null) {
                FrameLayout frameLayout = root.findViewById(R.id.frame_layout);
                mNetErrorView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.net_error, frameLayout, false);
                frameLayout.addView(mNetErrorView);
            }
        }
    }

    @Override
    public void removeNetError() {
        if (mNetErrorView != null && mRecyclerEmpty) {
            View root = getView();
            if (root != null) {
                FrameLayout frameLayout = root.findViewById(R.id.frame_layout);
                frameLayout.removeView(mNetErrorView);
                mRecyclerEmpty = false;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onViewDestroy();
    }
}
