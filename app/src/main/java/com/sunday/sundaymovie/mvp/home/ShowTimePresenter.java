package com.sunday.sundaymovie.mvp.home;

import com.sunday.sundaymovie.bean.ShowTimeMovies;
import com.sunday.sundaymovie.model.ShowTimeModel;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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
    protected void loadMovies() {
        mShowTimeModel.getShowTimeMovies().subscribe(new Observer<ShowTimeMovies>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull ShowTimeMovies showTimeMovies) {
                mView.removeNetError();
                mView.setRefreshing(false);
                mView.showMovies(showTimeMovies.getMs());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.showNetworkError();
                mView.setRefreshing(false);
                mView.snackBar("网络异常,下拉重试");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
