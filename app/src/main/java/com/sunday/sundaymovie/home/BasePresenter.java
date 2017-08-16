package com.sunday.sundaymovie.home;

import android.content.Context;

import com.sunday.sundaymovie.model.StarModel;

/**
 * Created by agentchen on 2017/7/23.
 */

abstract class BasePresenter<T> implements HomeContract.Presenter {
    final HomeContract.View<T> mView;
    private final StarModel mStarModel;

    BasePresenter(HomeContract.View<T> view, Context context) {
        mView = view;
        mStarModel = new StarModel(context);
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

    @Override
    public void onDestroy() {
        mStarModel.close();
    }
}
