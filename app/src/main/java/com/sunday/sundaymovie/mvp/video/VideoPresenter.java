package com.sunday.sundaymovie.mvp.video;

import android.app.DownloadManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.view.SurfaceHolder;

import com.sunday.sundaymovie.util.StringFormatUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by agentchen on 2017/8/7.
 */

class VideoPresenter implements VideoContract.Presenter, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {
    private final VideoContract.View mView;
    private final String mUrl;
    private final String mTitle;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private TimerImmersionRunnable mImmersionRunnable = new TimerImmersionRunnable();
    private static Timer timer;
    private ProgressTimerTask mProgressTimerTask;

    private boolean mIsImmersion = true;
    private boolean mPrepared = false;
    private int mDuration;

    VideoPresenter(VideoContract.View view, String url, String title) {
        mView = view;
        mView.setPresenter(this);
        mUrl = url;
        mTitle = title;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(mView.getContext(), PowerManager.FULL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void start() {
        mView.showTitle(mTitle);
        try {
            mMediaPlayer.setDataSource(mView.getContext(), Uri.parse(mUrl));
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        cancelImmersionTimer();
        cancelProgressTimer();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onClickSurface() {
        if (mPrepared) {
            if (mIsImmersion) {
                mView.showMediaController();
                mIsImmersion = false;
                startImmersionTimer();
            } else {
                mView.hideMediaController();
                mIsImmersion = true;
                cancelImmersionTimer();
            }
        }
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(holder);
    }

    @Override
    public void onClickPlay() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mView.showPlayIcon();
            cancelImmersionTimer();
        } else {
            mMediaPlayer.start();
            mView.showPauseIcon();
            startImmersionTimer();
        }
    }

    @Override
    public void onClickDownload() {
        DownloadManager downloadManager = (DownloadManager) mView.getContext().getSystemService(DOWNLOAD_SERVICE);
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
        cancelImmersionTimer();
    }

    @Override
    public void onStopTrackingTouch(int progress) {
        mMediaPlayer.seekTo(progress * mDuration / 100);
        startImmersionTimer();
    }

    @Override
    public void onRestart() {
        onClickPlay();
    }

    @Override
    public void onPause() {
        if (mMediaPlayer.isPlaying()) {
            onClickPlay();
        }
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
    public void onPrepared(MediaPlayer mediaPlayer) {
        mPrepared = true;
        mDuration = mediaPlayer.getDuration();
        mView.showTotalTime(StringFormatUtil.getTimeString(mDuration));
        startProgressTimer();
        mediaPlayer.start();
        mView.hideProgressBar();
        mView.showPlay();
        mView.showBottomMediaController();
        mView.hideMediaController();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        mView.showSecondaryProgress(percent);
    }

    private void startImmersionTimer() {
        mHandler.postDelayed(mImmersionRunnable, 3000L);
    }

    private void cancelImmersionTimer() {
        mHandler.removeCallbacks(mImmersionRunnable);
    }

    private class TimerImmersionRunnable implements Runnable {
        @Override
        public void run() {
            if (!mIsImmersion) {
                mView.hideMediaController();
                mIsImmersion = true;
            }
        }
    }

    private void startProgressTimer() {
        timer = new Timer();
        mProgressTimerTask = new ProgressTimerTask();
        timer.schedule(mProgressTimerTask, 0L, 1000L);
    }

    private void cancelProgressTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mProgressTimerTask != null) {
            mProgressTimerTask.cancel();
            mProgressTimerTask = null;
        }
    }

    private class ProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    int current = mMediaPlayer.getCurrentPosition();
                    int percent = 100 * current / mDuration;
                    mView.showCurrentTime(percent, StringFormatUtil.getTimeString(current));
                }
            });
        }
    }
}
