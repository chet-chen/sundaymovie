package com.sunday.sundaymovie.mvp.home;

import com.sunday.sundaymovie.model.StarModel;

/**
 * Created by agentchen on 2017/7/23.
 */

abstract class BasePresenter<T> implements HomeContract.Presenter {
    protected final HomeContract.View<T> mView;
    private StarModel mStarModel;
    private boolean mIsStarted = false;

    BasePresenter(HomeContract.View<T> view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mStarModel == null) {
            mStarModel = new StarModel();
        }
        if (!mIsStarted) {
            mIsStarted = true;
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
        mStarModel.insertMovie(id, name, imageURL);
        mView.snackBar("已收藏");
    }

    @Override
    public void unStar(int id) {
        mStarModel.deleteMovie(id);
        mView.snackBar("已取消收藏");
    }

    @Override
    public void openMovieDetail(int id) {
        mView.showMovieDetail(id);
    }

}
