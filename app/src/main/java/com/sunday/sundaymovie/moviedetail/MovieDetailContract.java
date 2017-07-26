package com.sunday.sundaymovie.moviedetail;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;
import com.sunday.sundaymovie.bean.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/24.
 */

public interface MovieDetailContract {
    interface View extends BaseView<Presenter> {
        void hideProgressBar();

        void showTopImage(String topImg);

        void showMainImage(String mainImg);

        void showBasicInfo(String movieName, String movieENName, boolean is3D, double overallRating
                , List<String> movieType, String movieDirectorName, String releaseDate, String releaseArea, String movieMins
                , String movieBoxOffice);

        void showMovieStory(String story);

        void setFollowed(boolean followed);

        void toast(String text);

        void showImages(ArrayList<String> urls);

        void showActor(List<Movie.BasicBean.ActorsBean> list);

        void showImage(ArrayList<String> urls, int position);

        void showAllImages(int id, String title);

        void updateImages(ArrayList<String> urls);

        void showVideo(String url, String title);

        void showAllVideo(int id, String title);

        void finish();

        void hideVideoInfo();

        void showVideoInfo(String title, String imgUrl);
    }

    interface Presenter extends BasePresenter {
        void loadMovieDetail();

        void clickImage(ArrayList<String> urls, int position);

        void modelToView();

        void openAllImages();

        void openVideo();

        void openAllVideo();

        void star();

        void unStar();

        void onDestroy();
    }
}
