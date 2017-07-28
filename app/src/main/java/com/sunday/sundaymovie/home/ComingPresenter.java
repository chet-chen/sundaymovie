package com.sunday.sundaymovie.home;

import android.content.Context;

import com.sunday.sundaymovie.bean.ComingMovie;
import com.sunday.sundaymovie.bean.ComingMovies;
import com.sunday.sundaymovie.model.ComingModel;
import com.sunday.sundaymovie.net.callback.ComingCallBack;

/**
 * Created by agentchen on 2017/7/23.
 */

class ComingPresenter extends BasePresenter<ComingMovie> {
    private ComingModel mComingModel;

    ComingPresenter(HomeContract.View<ComingMovie> view, Context context) {
        super(view, context);
        mComingModel = new ComingModel();
    }

    @Override
    public void loadMovies() {
        mComingModel.getComingMovies(new ComingCallBack() {
            @Override
            public void onResponse(ComingMovies response) {
                mView.hideNetError();
                mView.setRefreshing(false);
                mView.showMovies(response.getAttention());
            }

            @Override
            public void onError() {
                mView.showNetError();
                mView.setRefreshing(false);
                mView.snackBar("网络异常,下拉重试");
            }
        });
    }
}
