package com.sunday.sundaymovie.mvp.allvideo;

import com.sunday.sundaymovie.bean.VideoAll;
import com.sunday.sundaymovie.model.AllVideoModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by agentchen on 2017/8/3.
 */

class AllVideoPresenter implements AllVideoContract.Presenter {
    private final AllVideoContract.View mView;
    private final int mId;
    private final String mTitle;
    private final AllVideoModel mAllVideoModel;
    private List<VideoAll.Video> mList;
    private int mPageCount = 1;
    private int mTotalPageCount = 1;
    private boolean mLoading = false;
    private boolean mFirstLoad = true;
    private Observer<VideoAll> mObserver;
    private CompositeDisposable mDisposable;

    AllVideoPresenter(AllVideoContract.View view, int id, String title) {
        mView = view;
        mId = id;
        mTitle = title;
        mAllVideoModel = new AllVideoModel();
        mDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadAllVideo();
        mView.showTitle(mTitle);
    }

    @Override
    public void unsubscribe() {
        mDisposable.clear();
    }

    private void loadAllVideo() {
        if (mObserver == null) {
            mObserver = new Observer<VideoAll>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    mDisposable.add(d);
                }

                @Override
                public void onNext(@NonNull VideoAll videoAll) {
                    if (videoAll != null) {
                        if (mFirstLoad) {
                            mView.removeProgressBar();
                            mList = videoAll.getVideoList();
                            mTotalPageCount = videoAll.getTotalPageCount();
                            mFirstLoad = false;
                        } else {
                            mList.addAll(videoAll.getVideoList());
                        }
                        modelToView();
                        mLoading = false;
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    mLoading = false;
                    mPageCount--;
                    mView.toast("有点问题");
                }

                @Override
                public void onComplete() {

                }
            };
        }
        mAllVideoModel.getAllVideo(mId, mPageCount).subscribe(mObserver);
    }

    private void modelToView() {
        mView.showAllVideo(mList);
    }

    @Override
    public void openVideo(VideoAll.Video video) {
        mView.showVideo(video.getHightUrl(), video.getTitle());
    }

    @Override
    public void scrollEnd() {
        if (mPageCount < mTotalPageCount) {
            if (!mLoading) {
                mLoading = true;
                mPageCount++;
                loadAllVideo();
            }
        } else {
            mView.removeScrollEndListener();
        }
    }
}
