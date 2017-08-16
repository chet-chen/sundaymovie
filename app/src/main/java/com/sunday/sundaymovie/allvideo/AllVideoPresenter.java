package com.sunday.sundaymovie.allvideo;

import com.sunday.sundaymovie.bean.VideoAll;
import com.sunday.sundaymovie.model.AllVideoModel;
import com.sunday.sundaymovie.net.callback.VideoAllCallBack;

import java.util.List;

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

    AllVideoPresenter(AllVideoContract.View view, int id, String title) {
        mView = view;
        mView.setPresenter(this);
        mId = id;
        mTitle = title;
        mAllVideoModel = new AllVideoModel();
    }

    @Override
    public void start() {
        loadAllVideo();
        mView.showTitle(mTitle);
    }

    @Override
    public void loadAllVideo() {
        mAllVideoModel.getAllVideo(mId, mPageCount, new VideoAllCallBack() {
            @Override
            public void onResponse(VideoAll response) {
                if (response != null) {
                    if (mFirstLoad) {
                        mView.removeProgressBar();
                        mList = response.getVideoList();
                        mTotalPageCount = response.getTotalPageCount();
                        mFirstLoad = false;
                    } else {
                        mList.addAll(response.getVideoList());
                    }
                    modelToView();
                    mLoading = false;
                } else {
                    onError();
                }
            }

            @Override
            public void onError() {
                mLoading = false;
                mPageCount--;
                mView.toast("有点问题");
            }
        });
    }

    private void modelToView() {
        mView.showAllVideo(mList);
    }

    @Override
    public void openVideo(int position) {
        VideoAll.Video video = mList.get(position);
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
