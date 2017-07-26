package com.sunday.sundaymovie.moviedetail;

import android.content.Context;
import android.util.Log;

import com.sunday.sundaymovie.bean.AllPhoto;
import com.sunday.sundaymovie.bean.Movie;
import com.sunday.sundaymovie.model.AllPhotoModel;
import com.sunday.sundaymovie.model.MovieDetailModel;
import com.sunday.sundaymovie.model.StarsModel;
import com.sunday.sundaymovie.net.callback.ImageAllCallBack;
import com.sunday.sundaymovie.net.callback.MovieCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/24.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {
    private static final String TAG = "MyMovieDetailPresenter";
    private final int mMovieId;
    private final MovieDetailContract.View mView;
    private final MovieDetailModel mDetailModel;
    private final StarsModel mStarsModel;
    private Movie mMovie;
    private ArrayList<String> mImgsList;
    private boolean updatedImages = false;

    public MovieDetailPresenter(MovieDetailContract.View view, Context context, int movieId) {
        mView = view;
        mMovieId = movieId;
        mDetailModel = new MovieDetailModel();
        mStarsModel = new StarsModel(context);
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadMovieDetail();
    }

    @Override
    public void loadMovieDetail() {
        mDetailModel.getMovieDetail(mMovieId, new MovieCallBack() {
            @Override
            public void onResponse(Movie response) {
                mView.hideProgressBar();
                if (response == null) {
                    onError();
                } else {
                    mMovie = response;
                    modelToView();
                }
            }

            @Override
            public void onError() {
                mView.toast("有点问题");
                mView.finish();
            }
        });
    }

    @Override
    public void clickImage(ArrayList<String> urls, int position) {
        mView.showImage(urls, position);
        if (!updatedImages) {
            updatedImages = true;
            new AllPhotoModel().getAllPhoto(mMovieId, new ImageAllCallBack() {
                @Override
                public void onResponse(AllPhoto response) {
                    if (response != null) {
                        ArrayList<String> list = new ArrayList<>(mImgsList);
                        for (AllPhoto.Image image : response.getImages()) {
                            list.add(image.getImage());
                        }
                        mView.updateImages(list);
                    }
                }

                @Override
                public void onError() {
                }
            });
        }
    }

    @Override
    public void modelToView() {
        List<Movie.BasicBean.StageImgBean.ListBean> imgs = mMovie.getBasic().getStageImg().getList();
        if (imgs.size() != 0) {
            mView.showTopImage(imgs.get(0).getImgUrl());
        }
        mView.showMainImage(mMovie.getBasic().getImg());
        mView.showBasicInfo(mMovie.getBasic().getName(), mMovie.getBasic().getNameEn()
                , mMovie.getBasic().isIs3D(), mMovie.getBasic().getOverallRating()
                , mMovie.getBasic().getType(), mMovie.getBasic().getDirector().getName()
                , mMovie.getBasic().getReleaseDate(), mMovie.getBasic().getReleaseArea()
                , mMovie.getBasic().getMins(), mMovie.getBoxOffice().getTotalBoxDes());
        mView.showMovieStory(mMovie.getBasic().getStory());
//        如果此电影没有视频，则去除视频相关view
        if (mMovie.getBasic().getVideo().getCount() == 0) {
            mView.hideVideoInfo();
        } else {
            mView.showVideoInfo(mMovie.getBasic().getVideo().getTitle(), mMovie.getBasic().getVideo().getImg());
        }

        List<Movie.BasicBean.StageImgBean.ListBean> listBean = mMovie.getBasic().getStageImg().getList();
        mImgsList = new ArrayList<>(4);
        for (int i = 0; i < listBean.size() && i < 4; i++) {
            mImgsList.add(listBean.get(i).getImgUrl());
        }
        mView.showImages(mImgsList);

        List<Movie.BasicBean.ActorsBean> list = mMovie.getBasic().getActors();
//        把导演插入到演员list
        Movie.BasicBean.ActorsBean ab = new Movie.BasicBean.ActorsBean();
        Movie.BasicBean.DirectorBean directorBean = mMovie.getBasic().getDirector();
        ab.setActorId(directorBean.getDirectorId());
        ab.setImg(directorBean.getImg());
        ab.setName(directorBean.getName());
        ab.setRoleName("导演");
        list.add(0, ab);
        mView.showActor(list);
        mView.setFollowed(mStarsModel.isExist(mMovieId));
    }

    @Override
    public void openAllImages() {
        mView.showAllImages(mMovieId, mMovie.getBasic().getName());
    }

    @Override
    public void openVideo() {
        Movie.BasicBean.VideoBean bean = mMovie.getBasic().getVideo();
        mView.showVideo(bean.getHightUrl(), bean.getTitle());
    }

    @Override
    public void openAllVideo() {
        mView.showAllVideo(mMovieId, mMovie.getBasic().getName());
    }

    @Override
    public void star() {
        mStarsModel.insertMovie(mMovieId, mMovie.getBasic().getName(), mMovie.getBasic().getImg());
    }

    @Override
    public void unStar() {
        mStarsModel.deleteMovie(mMovieId);
    }

    @Override
    public void onDestroy() {
        mStarsModel.close();
    }
}
