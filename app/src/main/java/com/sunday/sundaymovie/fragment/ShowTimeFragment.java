package com.sunday.sundaymovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.ShowTimeAdapter;
import com.sunday.sundaymovie.api.Api;
import com.sunday.sundaymovie.model.ShowTimeMovies;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.ShowTimeCallBack;

import static com.sunday.sundaymovie.R.id.refresh_layout;

/**
 * Created by agentchen on 2017/3/28.
 * Email agentchen97@gmail.com
 */

public class ShowTimeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ShowTimeMovies mShowTimeMovies;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private OkManager mOkManager;
    private ShowTimeAdapter mAdapter;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setRefreshing(true);
        onRefresh();
        return view;
    }

    public void getDataFromInternet() {
        mOkManager.asyncGet(Api.SHOW_TIME, new ShowTimeCallBack() {
            @Override
            public void onResponse(ShowTimeMovies response) {
                mShowTimeMovies = response;
                modelToView();
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void modelToView() {
        if (mAdapter == null) {
            mAdapter = new ShowTimeAdapter(getActivity(), mShowTimeMovies.getMs());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.refresh(mShowTimeMovies.getMs());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        getDataFromInternet();
    }
}
