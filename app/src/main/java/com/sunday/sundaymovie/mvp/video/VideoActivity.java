package com.sunday.sundaymovie.mvp.video;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
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
        mTVTitle = (TextView) findViewById(R.id.tv_movie_video_title);
        mButtonBack = (ImageButton) findViewById(R.id.btn_close);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.media_controller_all);

        mButtonPlay.setEnabled(false);
        mSeekBar.setEnabled(false);
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
    public void enabledPlayButton() {
        mButtonPlay.setEnabled(true);
    }

    @Override
    public void enabledSeekBar() {
        mSeekBar.setEnabled(true);
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
        view.clearAnimation();
        view.animate().alpha(1f).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void alphaHideView(final View view) {
        view.animate().alpha(0f).setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
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
