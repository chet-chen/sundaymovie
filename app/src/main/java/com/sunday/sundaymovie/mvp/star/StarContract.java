package com.sunday.sundaymovie.mvp.star;

import android.content.Context;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;
import com.sunday.sundaymovie.bean.StarMovie;

import java.util.List;

/**
 * Created by agentchen on 2017/8/1.
 */

interface StarContract {
    interface View extends BaseView<StarContract.Presenter> {
        Context getContext();

        void showStarMovie(List<StarMovie> list);

        void showMovie(int id);
    }

    interface Presenter extends BasePresenter {

        void loadStarMovie();

        void openMovie(int position);

        void onDestroy();
    }
}
