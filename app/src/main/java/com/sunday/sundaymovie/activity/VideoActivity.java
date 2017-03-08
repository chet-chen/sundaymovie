package com.sunday.sundaymovie.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.util.StringFormatUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {
    private String url;
    private String title;

    private int duration;
    private boolean prepared = false;
    private boolean isImmersion = false;
    private boolean isRemoveRunnable = false;

    private Handler mHandler = new Handler();
    private TimerImmerRunnable mRunnable = new TimerImmerRunnable();
    private static Timer timer;
    private ProgressTimerTask mProgressTimerTask;
    private MediaPlayer mMediaPlayer;

    private SurfaceView mSurfaceView;
    private TextView mCurrentTimeTextView, mTotalTimeTextView, mMovieVideoTitle;
    private ProgressBar mProgressBar;
    private SeekBar mSeekBar;
    private ImageButton mImageButton;
    private View mMediaControllerBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieVideoTitle.setText(title);
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
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(url));
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
                    prepared = true;
                    duration = mp.getDuration();
                    mTotalTimeTextView.setText(StringFormatUtil.getTimeString(duration));
                    startProgressTimer();
                    mp.start();
                    timerImmersion();
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mSurfaceView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            immersionSwitch();
                        }
                    });
                    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser && !isRemoveRunnable) {
                                mHandler.removeCallbacks(mRunnable);
                                isRemoveRunnable = true;
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            mMediaPlayer.seekTo(seekBar.getProgress() * duration / 100);
                            mHandler.postDelayed(mRunnable, 2500L);
                            isRemoveRunnable = false;
                        }
                    });
                }
            });
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null) {
            url = bundle.getString("url");
            title = bundle.getString("title");
        }
        isFullScreen = true;
        isAllowScreenRotate = true;
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_video);
        mMediaPlayer = new MediaPlayer();
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view_video);
        mSeekBar = (SeekBar) findViewById(R.id.media_controller_seek_bar);
        mImageButton = (ImageButton) findViewById(R.id.btn_play_video);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_white_48dp));
                } else {
                    mMediaPlayer.start();
                    mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_white_48dp));
                    timerImmersion();
                }
            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.progress_video_load);
        mCurrentTimeTextView = (TextView) findViewById(R.id.current);
        mTotalTimeTextView = (TextView) findViewById(R.id.total);
        mMediaControllerBottom = findViewById(R.id.media_controller_bottom);
        mMovieVideoTitle = (TextView) findViewById(R.id.tv_movie_video_title);

        mImageButton.setVisibility(View.INVISIBLE);
        mMediaControllerBottom.setVisibility(View.INVISIBLE);
    }

    public static void startMe(Context context, String url, String title) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
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
    protected void onResume() {
        super.onResume();
        if (prepared && !mMediaPlayer.isPlaying()) {
            mImageButton.callOnClick();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (prepared && mMediaPlayer.isPlaying()) {
            mImageButton.callOnClick();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public class ProgressTimerTask extends TimerTask {
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
        timer.schedule(mProgressTimerTask, 0, 1000);
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
        if (mMediaPlayer == null) {
            return;
        }
        int current = mMediaPlayer.getCurrentPosition();
        mCurrentTimeTextView.setText(StringFormatUtil.getTimeString(current));
        long pos = 100L * current / duration;
        mSeekBar.setProgress((int) pos);
    }

    private void immersionSwitch() {
        if (!isImmersion) {
            alphaUnShowAnim(mMovieVideoTitle);
            alphaUnShowAnim(mImageButton);
            alphaUnShowAnim(mMediaControllerBottom);
            isImmersion = true;
        } else {
            alphaShowAnim(mMovieVideoTitle);
            alphaShowAnim(mImageButton);
            alphaShowAnim(mMediaControllerBottom);
            isImmersion = false;
            timerImmersion();
        }
    }

    /*
    * bug 已解决
    * 用户在操作seekbar是有可能隐藏
    * */
    private void timerImmersion() {
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 2500L);
    }

    private class TimerImmerRunnable implements Runnable {

        @Override
        public void run() {
            if (!isImmersion && mMediaPlayer.isPlaying()) {
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
