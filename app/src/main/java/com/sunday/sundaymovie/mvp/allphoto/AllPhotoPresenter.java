package com.sunday.sundaymovie.mvp.allphoto;

import com.sunday.sundaymovie.bean.AllPhoto;
import com.sunday.sundaymovie.model.AllPhotoModel;
import com.sunday.sundaymovie.net.callback.ImageAllCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/31.
 */

class AllPhotoPresenter implements AllPhotoContract.Presenter {
    private final AllPhotoContract.View mView;
    private final int mMovieId;
    private final String mTitle;
    private final AllPhotoModel mAllPhotoModel;
    private AllPhoto mAllPhoto;
    private ArrayList<String> mUrls;

    AllPhotoPresenter(AllPhotoContract.View view, int movieId, String title) {
        mView = view;
        mView.setPresenter(this);
        mMovieId = movieId;
        mTitle = title;
        mAllPhotoModel = new AllPhotoModel();
    }

    @Override
    public void start() {
        mView.showTitle(mTitle);
        loadAllPhoto();
    }

    private void loadAllPhoto() {
        mAllPhotoModel.getAllPhoto(mMovieId, new ImageAllCallBack() {
            @Override
            public void onResponse(AllPhoto response) {
                if (response != null) {
                    mAllPhoto = response;
                    mView.removeProgressBar();
                    modelToView();
                } else {
                    onError();
                }
            }

            @Override
            public void onError() {
                mView.toast("有点问题");
            }
        });
    }

    private void modelToView() {
        List<AllPhoto.Image> images = mAllPhoto.getImages();
        mView.showTitle(mTitle + " " + images.size() + "张");
        mUrls = new ArrayList<>(images.size());
        for (AllPhoto.Image image : images) {
            mUrls.add(image.getImage());
        }
        mView.showAllImage(mUrls);
    }

    @Override
    public void openPhoto(int position) {
        mView.showPhoto(mUrls, position);
    }
}
