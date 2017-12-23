package com.sunday.sundaymovie.mvp.video;

import android.app.DownloadManager;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;

import com.sunday.sundaymovie.base.BaseApplication;
import com.sunday.sundaymovie.util.StringFormatUtil;

import java.io.IOException;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by agentchen on 2017/8/7.
 */

class VideoPresenter implements VideoContract.Presenter, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener {
    private final VideoContract.View mView;
    private final String mUrl;
    private final String mTitle;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private volatile boolean mIsHiddenMediaController = false;
    private int mDuration;
    private int mPositionRecord = 0;
    private Rect mSurfaceFrame;

    VideoPresenter(VideoContract.View view, String url, String title) {
        mView = view;
        mUrl = url;
        mTitle = title;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mView.enabledMediaController(false);
        mView.showPauseIcon();
        mView.showProgressBar();
        mView.showTitle(mTitle);
        postHideMediaController();
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnInfoListener(this);
        }
        try {
            mMediaPlayer.setDataSource(mUrl);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void unsubscribe() {
        removeShowProgress();
        removeHideMediaController();
        if (mMediaPlayer != null) {
            mPositionRecord = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mDuration = mMediaPlayer.getDuration();
        mView.showTotalTime(StringFormatUtil.getTimeString(mDuration));
        if (mPositionRecord > 0) {
            mMediaPlayer.seekTo(mPositionRecord);
        }
        mMediaPlayer.start();
        postShowProgress();
        mView.enabledMediaController(true);
        mView.hideProgressBar();
    }

    @Override
    public void onClickContentView() {
        if (mIsHiddenMediaController) {
            postShowProgress();
            mView.showMediaController();
            mIsHiddenMediaController = false;
            if (mMediaPlayer.isPlaying()) {
                postHideMediaController();
            }
        } else {
            removeShowProgress();
            mView.hideMediaController();
            mIsHiddenMediaController = true;
            removeHideMediaController();
        }
    }

    @Override
    public void onSurfaceCreated(@NonNull SurfaceHolder holder) {
        mMediaPlayer.setDisplay(holder);
        mSurfaceFrame = holder.getSurfaceFrame();
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(holder);
        mSurfaceFrame = holder.getSurfaceFrame();
    }

    @Override
    public void onSurfaceDestroyed() {
        mMediaPlayer.setOnCompletionListener(null);
        mSurfaceFrame = null;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        if (width > 0 && height > 0 && mSurfaceFrame != null) {
            int rawWidth = mSurfaceFrame.width();
            int rawHeight = mSurfaceFrame.height();
            if ((float) width / height > (float) rawHeight / rawWidth) {
                height = (int) ((float) rawWidth / width * height);
                width = rawWidth;
            } else {
                width = (int) ((float) rawHeight / height * width);
                height = rawHeight;
            }
            mView.setSurfaceSize(width, height);
        }
    }

    @Override
    public void onClickPlay() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            removeShowProgress();
            mView.showPlayIcon();
            removeHideMediaController();
        } else {
            mMediaPlayer.start();
            postShowProgress();
            mView.showPauseIcon();
            postHideMediaController();
        }
    }

    @Override
    public void onClickDownload() {
        DownloadManager downloadManager = (DownloadManager) BaseApplication.getContext()
                .getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(mUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setTitle(mTitle)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, mTitle + ".mp4");
        downloadManager.enqueue(request);
        mView.toast("开始下载");
    }

    @Override
    public void onStartTrackingTouch() {
        removeShowProgress();
        removeHideMediaController();
    }

    @Override
    public void onStopTrackingTouch(int progress) {
        mMediaPlayer.seekTo(progress * mDuration / 1000);
        postShowProgress();
        postHideMediaController();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.finish();
            }
        }, 800L);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        mView.showSecondaryProgress(percent * 10);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                mView.showProgressBar();
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mView.hideProgressBar();
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                mView.hideProgressBar();
                break;
        }
        return true;
    }

    private void postHideMediaController() {
        mHandler.postDelayed(mHideMediaController, 3000L);
    }

    private void removeHideMediaController() {
        mHandler.removeCallbacks(mHideMediaController);
    }

    private final Runnable mHideMediaController = new Runnable() {
        @Override
        public void run() {
            if (!mIsHiddenMediaController) {
                mView.hideMediaController();
                mIsHiddenMediaController = true;
                removeShowProgress();
            }
        }
    };

    private void postShowProgress() {
        mHandler.post(mShowProgress);
    }

    private void removeShowProgress() {
        mHandler.removeCallbacks(mShowProgress);
    }

    private final Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer == null || !mMediaPlayer.isPlaying() || mIsHiddenMediaController) {
                return;
            }
            int current = mMediaPlayer.getCurrentPosition();
            int progress = 1000 * current / mDuration;
            mView.showCurrentTime(progress, StringFormatUtil.getTimeString(current));
            mHandler.postDelayed(this, 800);
        }
    };
}
