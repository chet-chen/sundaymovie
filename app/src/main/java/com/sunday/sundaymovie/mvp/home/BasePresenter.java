package com.sunday.sundaymovie.mvp.home;

import com.sunday.sundaymovie.model.StarModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by agentchen on 2017/7/23.
 */

abstract class BasePresenter<T> implements HomeContract.Presenter {
    protected final HomeContract.View<T> mView;
    CompositeDisposable mDisposable;
    private StarModel mStarModel;
    private boolean mIsSubscribed = false;

    BasePresenter(HomeContract.View<T> view) {
        mView = view;
        mStarModel = new StarModel();
        mDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (!mIsSubscribed) {
            mIsSubscribed = true;
            mView.setRefreshing(true);
            loadMovies();
        }
    }

    protected abstract void loadMovies();

    @Override
    public void refresh() {
        loadMovies();
    }

    @Override
    public void star(int id, String name, String imageURL) {
        mStarModel.starMovie(id, name, imageURL);
        mView.snackBar("已收藏");
    }

    @Override
    public void unStar(int id) {
        mStarModel.unstarMovie(id);
        mView.snackBar("已取消收藏");
    }

    @Override
    public void openMovieDetail(int id) {
        mView.showMovieDetail(id);
    }

    @Override
    public void unsubscribe() {
        mDisposable.clear();
    }
}
