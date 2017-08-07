package com.sunday.sundaymovie.star;

import com.sunday.sundaymovie.bean.StarMovie;
import com.sunday.sundaymovie.model.StarModel;

import java.util.List;

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
        mStarModel = new StarModel(view.getContext());
    }

    @Override
    public void start() {
        loadStarMovie();
    }

    @Override
    public void loadStarMovie() {
        mList = mStarModel.getAll();
        mView.showStarMovie(mList);
    }

    @Override
    public void openMovie(int position) {
        mView.showMovie(mList.get(position).getId());
    }

    @Override
    public void onDestroy() {
        mStarModel.close();
    }
}
