package com.sunday.sundaymovie.mvp.photo;

import com.sunday.sundaymovie.base.BaseApplication;
import com.sunday.sundaymovie.model.PhotoModel;

import java.io.File;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by agentchen on 2017/7/31.
 */

class PhotoPresenter implements PhotoContract.Presenter {
    private final PhotoContract.View mView;
    private final PhotoModel mPhotoModel;
    private List<String> mImgURLs;
    private int mPosition;
    private File mDownloadImage;

    PhotoPresenter(PhotoContract.View view, List<String> imgURLs, int position) {
        mView = view;
        mView.setPresenter(this);
        mImgURLs = imgURLs;
        mPosition = position;
        mPhotoModel = new PhotoModel(BaseApplication.getContext());
    }

    @Override
    public void subscribe() {
        mView.showPhotos(mImgURLs, mPosition);
        mView.showPosition(mPosition, mImgURLs.size());
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void openDownloadImage() {
        if (mDownloadImage != null) {
            mView.showDownloadImage(mDownloadImage);
        }
    }

    @Override
    public void downloadImage(int position) {
        mPhotoModel.downloadImage(mImgURLs.get(position)).subscribe(new Observer<File>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull File file) {
                mDownloadImage = file;
                mView.showSnackBar("图片已保存", "打开");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.toast("下载失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onPageSelected(int position) {
        mView.showPosition(position, mImgURLs.size());
    }

    @Override
    public void dataChange(List<String> urls) {
        mImgURLs = urls;
        mView.showPhotos(mImgURLs, -1);
        mView.showPosition(mView.getCurrentItem(), mImgURLs.size());
    }
}
