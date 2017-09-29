package com.sunday.sundaymovie.mvp.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    private static final String KEY_URL = "url";
    private static final String KEY_TITLE = "title";
    private VideoContract.Presenter mPresenter;
    private FrameLayout mFrameLayout;
    private SurfaceView mSurfaceView;
    private TextView mCurrentTimeTextView, mTotalTimeTextView, mTVTitle;
    private ProgressBar mProgressBar;
    private SeekBar mSeekBar;
    private ImageButton mButtonPlay, mButtonDownload, mButtonBack;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFrameLayout.setOnClickListener(this);
        mSurfaceView.getHolder().addCallback(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mButtonPlay.setOnClickListener(this);
        mButtonDownload.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(VideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        String url = bundle.getString(KEY_URL);
        String title = bundle.getString(KEY_TITLE);
        new VideoPresenter(this, url, title);
        isFullScreen = true;
        isAllowScreenRotate = true;
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_video);
        mFrameLayout = (FrameLayout) findViewById(R.id.content_view);
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
    }

    public static void startMe(Context context, String url, String title) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    public void setSurfaceSize(int width, int height) {
        ViewGroup.LayoutParams params = mSurfaceView.getLayoutParams();
        params.width = width;
        params.height = height;
        mSurfaceView.requestLayout();
    }

    @Override
    public void showTitle(String title) {
        mTVTitle.setText(title);
    }

    private Runnable mStartAction = new Runnable() {
        @Override
        public void run() {
            mRelativeLayout.setVisibility(View.VISIBLE);
        }
    };

    private Runnable mEndAction = new Runnable() {
        @Override
        public void run() {
            mRelativeLayout.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public void showMediaController() {
        mRelativeLayout.animate().alpha(1f).withStartAction(mStartAction);
    }

    @Override
    public void hideMediaController() {
        mRelativeLayout.animate().alpha(0f).withEndAction(mEndAction);
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
    public void enabledMediaController(boolean enabled) {
        mButtonPlay.setEnabled(enabled);
        mSeekBar.setEnabled(enabled);
    }

    @Override
    public void showPlayIcon() {
        mButtonPlay.setImageResource(R.drawable.ic_play_arrow_white_56dp);
    }

    @Override
    public void showPauseIcon() {
        mButtonPlay.setImageResource(R.drawable.ic_pause_white_56dp);
    }

    @Override
    public void showTotalTime(String totalTime) {
        mTotalTimeTextView.setText(totalTime);
    }

    @Override
    public void showCurrentTime(int progress, String currentTime) {
        mSeekBar.setProgress(progress);
        mCurrentTimeTextView.setText(currentTime);
    }

    @Override
    public void showSecondaryProgress(int progress) {
        mSeekBar.setSecondaryProgress(progress);
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                finish();
                break;
            case R.id.content_view:
                mPresenter.onClickContentView();
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
        mPresenter.onSurfaceChanged(surfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mPresenter.onSurfaceDestroyed();
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
