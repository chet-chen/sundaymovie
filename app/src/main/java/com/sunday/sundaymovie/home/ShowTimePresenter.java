package com.sunday.sundaymovie.home;

import android.content.Context;

import com.sunday.sundaymovie.bean.ShowTimeMovies;
import com.sunday.sundaymovie.model.ShowTimeModel;
import com.sunday.sundaymovie.net.callback.ShowTimeCallBack;

/**
 * Created by agentchen on 2017/7/23.
 */

public class ShowTimePresenter extends BasePresenter<ShowTimeMovies.MsBean> {
    private ShowTimeModel mShowTimeModel;

    public ShowTimePresenter(HomeContract.View<ShowTimeMovies.MsBean> view, Context context) {
        super(view, context);
        mShowTimeModel = new ShowTimeModel();
    }

    @Override
    public void loadMovies() {
        mShowTimeModel.getShowTimeMovies(new ShowTimeCallBack() {
            @Override
            public void onResponse(ShowTimeMovies response) {
                mView.hideNetError();
                mView.setRefreshing(false);
                mView.showMovies(response.getMs());
            }

            @Override
            public void onError(Exception e) {
                mView.showNetError();
                mView.setRefreshing(false);
                mView.snackbar("网络异常,下拉重试");
            }
        });
    }
}
