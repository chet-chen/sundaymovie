package com.sunday.sundaymovie.mvp.moviedetail;

import android.content.Context;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;
import com.sunday.sundaymovie.bean.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/24.
 */

interface MovieDetailContract {
    interface View extends BaseView<Presenter> {

        Context getContext();

        void hideProgressBar();

        void showTopImage(String topImg);

        void showMainImage(String mainImg);

        void showBasicInfo(String movieName, String movieENName, boolean is3D, double overallRating
                , String movieDirectorName, String dateAndArea, String movieMins
                , String movieBoxOffice);

        void showType(String type);

        void hideType();

        void showMovieStory(String story);

        void setFollowed(boolean followed);

        void toast(String text);

        void showPhotos(ArrayList<String> urls);

        void showActor(List<Movie.BasicBean.ActorsBean> list);

        void showPhoto(ArrayList<String> urls, int position);

        void showAllPhoto(int id, String title);

        void showAllPhoto(ArrayList<String> urls, String title);

        void updatePhotos(ArrayList<String> urls);

        void showVideo(String url, String title);

        void showAllVideo(int id, String title);

        void finish();

        void hideVideoInfo();

        void showVideoInfo(String title, String imgUrl);
    }

    interface Presenter extends BasePresenter {

        void openPhoto(int position);

        void openAllPhoto();

        void openVideo();

        void openAllVideo();

        void openActor(Movie.BasicBean.ActorsBean actorsBean);

        void star();

        void unStar();

    }
}
