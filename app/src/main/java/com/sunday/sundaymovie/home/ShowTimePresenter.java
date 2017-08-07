package com.sunday.sundaymovie.home;

import com.sunday.sundaymovie.bean.ShowTimeMovies;
import com.sunday.sundaymovie.model.ShowTimeModel;
import com.sunday.sundaymovie.net.callback.ShowTimeCallBack;

/**
 * Created by agentchen on 2017/7/23.
 */

class ShowTimePresenter extends BasePresenter<ShowTimeMovies.MsBean> {
    private ShowTimeModel mShowTimeModel;

    ShowTimePresenter(HomeContract.View<ShowTimeMovies.MsBean> view) {
        super(view);
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
            public void onError() {
                mView.showNetError();
                mView.setRefreshing(false);
                mView.snackBar("网络异常,下拉重试");
            }
        });
    }
}
