package com.sunday.sundaymovie.mvp.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.base.BaseActivity;

/**
 * Created by agentchen on 2017/8/7.
 */

public class VideoActivity extends BaseActivity implements VideoContract.View, View.OnClickListener, SeekBar.OnSeekBarChangeListener, SurfaceHolder.Callback {
    private VideoContract.Presenter mPresenter;
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
        mSurfaceView.getHolder().addCallback(this);
        mSurfaceView.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mButtonPlay.setOnClickListener(this);
        mButtonDownload.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
        mPresenter.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.onRestart();
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    public void setPresenter(VideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        String url = bundle.getString("url");
        String title = bundle.getString("title");
        new VideoPresenter(this, url, title);
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
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showTitle(String title) {
        mTVTitle.setText(title);
    }

    @Override
    public void showMediaController() {
        alphaShowView(mRelativeLayout);
    }

    @Override
    public void hideMediaController() {
        alphaHideView(mRelativeLayout);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPlay() {
        mButtonPlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBottomMediaController() {
        mMediaControllerBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlayIcon() {
        mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_white_56dp));
    }

    @Override
    public void showPauseIcon() {
        mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_white_56dp));
    }

    @Override
    public void showTotalTime(String totalTime) {
        mTotalTimeTextView.setText(totalTime);
    }

    @Override
    public void showCurrentTime(int percent, String currentTime) {
        mSeekBar.setProgress(percent);
        mCurrentTimeTextView.setText(currentTime);
    }

    @Override
    public void showSecondaryProgress(int percent) {
        mSeekBar.setSecondaryProgress(percent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void alphaShowView(final View view) {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(300L);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    private void alphaHideView(final View view) {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(300L);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.surface_view_video:
                mPresenter.onClickSurface();
                break;
            case R.id.btn_play_video:
                mPresenter.onClickPlay();
                break;
            case R.id.btn_download_video:
                mPresenter.onClickDownload();
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mPresenter.onSurfaceCreated(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mPresenter.onStartTrackingTouch();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mPresenter.onStopTrackingTouch(seekBar.getProgress());
    }
}
