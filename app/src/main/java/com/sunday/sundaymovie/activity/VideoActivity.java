package com.sunday.sundaymovie.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.util.StringFormatUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends BaseActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {
    private String mUrl;
    private String mTitle;

    private int duration;
    private boolean isImmersion = true, first = true;

    private Handler mHandler = new Handler();
    private TimerImmerRunnable mRunnable = new TimerImmerRunnable();
    private static Timer timer;
    private ProgressTimerTask mProgressTimerTask;
    private MediaPlayer mMediaPlayer;

    private SurfaceView mSurfaceView;
    private TextView mCurrentTimeTextView, mTotalTimeTextView, mTVTitle;
    private ProgressBar mProgressBar;
    private SeekBar mSeekBar;
    private ImageButton mButtonPlay, mButtonDownload, mButtonBack;
    private View mMediaControllerBottom;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTVTitle.setText(mTitle);
        mMediaPlayer = new MediaPlayer();
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        try {
            mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.FULL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(mUrl));
            mMediaPlayer.setOnCompletionListener(VideoActivity.this);
            mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    mSeekBar.setSecondaryProgress(percent);
                }
            });

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    duration = mp.getDuration();
                    mTotalTimeTextView.setText(StringFormatUtil.getTimeString(duration));
                    startProgressTimer();
                    mp.start();
                    timerImmersion();
                    mProgressBar.setVisibility(View.INVISIBLE);
                    alphaUnShowAnim(mRelativeLayout);
                    mSurfaceView.setOnClickListener(VideoActivity.this);
                    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            mHandler.removeCallbacks(mRunnable);
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            mMediaPlayer.seekTo(seekBar.getProgress() * duration / 100);
                            mHandler.postDelayed(mRunnable, 2500L);
                        }
                    });
                }
            });
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mButtonPlay.setOnClickListener(this);
        mButtonDownload.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null) {
            mUrl = bundle.getString("mUrl");
            mTitle = bundle.getString("mTitle");
        }
        isFullScreen = true;
        isAllowScreenRotate = true;
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_video);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view_video);
        mSeekBar = (SeekBar) findViewById(R.id.media_controller_seek_bar);
        mButtonPlay = (ImageButton) findViewById(R.id.btn_play_video);
        mButtonDownload = (ImageButton) findViewById(R.id.btn_download_video);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_video_load);
        mCurrentTimeTextView = (TextView) findViewById(R.id.current);
        mTotalTimeTextView = (TextView) findViewById(R.id.total);
        mMediaControllerBottom = findViewById(R.id.media_controller_bottom);
        mTVTitle = (TextView) findViewById(R.id.tv_movie_video_title);
        mButtonBack = (ImageButton) findViewById(R.id.btn_back);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.media_controller_all);

        mButtonPlay.setVisibility(View.INVISIBLE);
        mMediaControllerBottom.setVisibility(View.INVISIBLE);
    }

    public static void startMe(Context context, String url, String title) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("mUrl", url);
        intent.putExtra("mTitle", title);
        context.startActivity(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mButtonPlay.callOnClick();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer.isPlaying()) {
            mButtonPlay.callOnClick();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelProgressTimer();
        mHandler.removeCallbacks(mRunnable);
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.surface_view_video:
                immersionSwitch();
                break;
            case R.id.btn_play_video:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_white_56dp));
                } else {
                    mMediaPlayer.start();
                    mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_white_56dp));
                    timerImmersion();
                }
                break;
            case R.id.btn_download_video:
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(mUrl);
                DownloadManager.Request request = new DownloadManager.Request(uri)
                        .setTitle(mTitle)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, mTitle + ".mp4");
                downloadManager.enqueue(request);
                Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private class ProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    setProgressAndText();
                }
            });
        }
    }

    public void startProgressTimer() {
        cancelProgressTimer();
        timer = new Timer();
        mProgressTimerTask = new ProgressTimerTask();
        timer.schedule(mProgressTimerTask, 0L, 1000L);
    }

    public void cancelProgressTimer() {
        if (timer != null) {
            timer.cancel();
        }
        if (mProgressTimerTask != null) {
            mProgressTimerTask.cancel();
        }
    }

    private void setProgressAndText() {
        if (mMediaPlayer != null) {
            int current = mMediaPlayer.getCurrentPosition();
            mCurrentTimeTextView.setText(StringFormatUtil.getTimeString(current));
            long pos = 100L * current / duration;
            mSeekBar.setProgress((int) pos);
        }
    }

    private void immersionSwitch() {
        if (!isImmersion) {
            alphaUnShowAnim(mRelativeLayout);
//            alphaUnShowAnim(mButtonBack);
//            alphaUnShowAnim(mTVTitle);
//            alphaUnShowAnim(mButtonPlay);
//            alphaUnShowAnim(mMediaControllerBottom);
            isImmersion = true;
        } else {
            alphaShowAnim(mRelativeLayout);
//            alphaShowAnim(mButtonBack);
//            alphaShowAnim(mTVTitle);
//            alphaShowAnim(mButtonPlay);
//            alphaShowAnim(mMediaControllerBottom);
            isImmersion = false;
            timerImmersion();
        }
    }

    /*
    * bug 已解决
    * 用户在操作seekBar时有可能隐藏
    * */
    private void timerImmersion() {
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 2500L);
    }

    private class TimerImmerRunnable implements Runnable {

        @Override
        public void run() {
            if (!isImmersion && mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                immersionSwitch();
            }
        }
    }

    private void alphaUnShowAnim(final View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.1f);
        animator.setDuration(500L);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                if (first) {
                    mButtonPlay.setVisibility(View.VISIBLE);
                    mMediaControllerBottom.setVisibility(View.VISIBLE);
                    first = false;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void alphaShowAnim(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 1f);
        animator.setDuration(500L);
        view.setVisibility(View.VISIBLE);
        animator.start();
    }
}
