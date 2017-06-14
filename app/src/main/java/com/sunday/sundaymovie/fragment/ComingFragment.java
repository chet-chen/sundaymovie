package com.sunday.sundaymovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.ComingAdapter;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.model.ComingMovies;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.ComingCallBack;

import static com.sunday.sundaymovie.R.id.refresh_layout;

/**
 * Created by agentchen on 2017/3/28.
 * Email agentchen97@gmail.com
 */

public class ComingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ComingMovies mComingMovies;
    private OkManager mOkManager;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ComingAdapter mAdapter;
    private boolean recyclerEmpty = true;
    private FrameLayout mFrameLayout;
    private View mNetErrorView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkManager = OkManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_show_time);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setRefreshing(true);
        onRefresh();
        return view;
    }

    @Override
    public void onRefresh() {
        getDataFromInternet();
    }

    private void getDataFromInternet() {
        mOkManager.asyncGet(Api.COMING_MOVIE, new ComingCallBack() {
            @Override
            public void onResponse(ComingMovies response) {
                recyclerEmpty = false;
                if (mNetErrorView != null) {
                    mFrameLayout.removeView(mNetErrorView);
                    mNetErrorView = null;
                }
                mComingMovies = response;
                modelToView();
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Exception e) {
                if (mNetErrorView == null && recyclerEmpty) {
                    mNetErrorView = LayoutInflater.from(getActivity())
                            .inflate(R.layout.net_error, mFrameLayout, false);
                    mFrameLayout.addView(mNetErrorView);
                }
                Snackbar.make(mRefreshLayout.getRootView(), "网络异常,下拉重试", Snackbar.LENGTH_SHORT).show();
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public void smoothScrollToTop() {
        if (mAdapter != null && mAdapter.getItemCount() > 0) {
            if (((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() > 5) {
                mRecyclerView.scrollToPosition(5);
            }
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void modelToView() {
        if (mAdapter == null) {
            //在这里setLayoutManager为了解决没网状态下启动Fragment，将不能下拉刷新，原因未知
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new ComingAdapter(getActivity(), mComingMovies.getMoviecomings());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged(mComingMovies.getMoviecomings());
        }
    }
}
