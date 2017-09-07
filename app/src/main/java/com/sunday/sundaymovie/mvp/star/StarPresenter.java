package com.sunday.sundaymovie.mvp.star;

import com.sunday.sundaymovie.bean.StarMovie;
import com.sunday.sundaymovie.model.StarModel;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by agentchen on 2017/8/1.
 */

class StarPresenter implements StarContract.Presenter {
    private final StarContract.View mView;
    private final StarModel mStarModel;
    private List<StarMovie> mList;

    StarPresenter(StarContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mStarModel = new StarModel();
    }

    @Override
    public void start() {
        loadStarMovie();
    }

    @Override
    public void onViewDestroy() {

    }

    private void loadStarMovie() {
        mStarModel.getAllStarMovie().subscribe(new Consumer<List<StarMovie>>() {
            @Override
            public void accept(List<StarMovie> starMovies) throws Exception {
                mList = starMovies;
                mView.showStarMovie(mList);
            }
        });
    }

    @Override
    public void openMovie(int position) {
        mView.showMovie(mList.get(position).getId());
    }
}
