package com.sunday.sundaymovie.home;

import android.content.Context;

import com.sunday.sundaymovie.model.StarsModel;

/**
 * Created by agentchen on 2017/7/23.
 */

abstract class BasePresenter<T> implements HomeContract.Presenter {
    final HomeContract.View<T> mView;
    private StarsModel mStarsModel;

    BasePresenter(HomeContract.View<T> view, Context context) {
        mView = view;
        mStarsModel = new StarsModel(context);
        view.setPresenter(this);
    }

    @Override
    public void start() {
        mView.setRefreshing(true);
        loadMovies();
    }

    public abstract void loadMovies();

    @Override
    public void refresh() {
        loadMovies();
    }

    @Override
    public void star(int id, String name, String imageURL) {
        mStarsModel.insertMovie(id, name, imageURL);
        mView.snackbar("已收藏");
    }

    @Override
    public void unStar(int id) {
        mStarsModel.deleteMovie(id);
        mView.snackbar("已取消收藏");
    }

    @Override
    public void openMovieDetail(int id) {
        mView.showMovieDetail(id);
    }
}
