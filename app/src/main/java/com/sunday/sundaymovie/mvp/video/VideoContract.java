package com.sunday.sundaymovie.mvp.video;

import android.support.annotation.NonNull;
import android.view.SurfaceHolder;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;

/**
 * Created by agentchen on 2017/8/7.
 */

interface VideoContract {
    interface View extends BaseView<VideoContract.Presenter> {
        void showTitle(String title);

        void showMediaController();

        void hideMediaController();

        void hideProgressBar();

        void showProgressBar();

        void enabledPlayButton();

        void enabledSeekBar();

        void showPlayIcon();

        void showPauseIcon();

        void showTotalTime(String totalTime);

        void showCurrentTime(int percent, String currentTime);

        void showSecondaryProgress(int percent);

        void toast(String text);

        void finish();
    }

    interface Presenter extends BasePresenter {
        void onClickContentView();

        void onSurfaceCreated(@NonNull SurfaceHolder holder);

        void onClickPlay();

        void onClickDownload();

        void onStartTrackingTouch();

        void onStopTrackingTouch(int progress);

        void onRestart();

        void onPause();
    }
}
