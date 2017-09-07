package com.sunday.sundaymovie.mvp.allphoto;

import com.sunday.sundaymovie.bean.AllPhoto;
import com.sunday.sundaymovie.model.AllPhotoModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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
    private Disposable mDisposable;

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

    @Override
    public void onViewDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    private void loadAllPhoto() {
        mAllPhotoModel.getAllPhoto(mMovieId).subscribe(new Observer<AllPhoto>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull AllPhoto allPhoto) {
                if (allPhoto != null) {
                    mAllPhoto = allPhoto;
                    mView.removeProgressBar();
                    modelToView();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.toast("有点问题");
            }

            @Override
            public void onComplete() {

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
