package com.sunday.sundaymovie.mvp.home;

import com.sunday.sundaymovie.bean.ComingMovie;
import com.sunday.sundaymovie.bean.ComingMovies;
import com.sunday.sundaymovie.model.ComingModel;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by agentchen on 2017/7/23.
 */

class ComingPresenter extends BasePresenter<ComingMovie> {
    private ComingModel mComingModel;

    ComingPresenter(HomeContract.View<ComingMovie> view) {
        super(view);
        mComingModel = new ComingModel();
    }

    @Override
    protected void loadMovies() {
        mComingModel.getComingMovies().subscribe(new Observer<ComingMovies>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull ComingMovies comingMovies) {
                mView.removeNetError();
                mView.setRefreshing(false);
                mView.showMovies(comingMovies.getAttention());
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
