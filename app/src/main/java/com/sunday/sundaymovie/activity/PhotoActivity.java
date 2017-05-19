package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.PhotoViewPagerAdapter;
import com.sunday.sundaymovie.widget.HackyViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PhotoActivity extends BaseActivity implements View.OnClickListener {
    int startPosition;
    private ViewPager mViewPager;
    private TextView mTextView;
    private ImageButton mButtonDownloadImg;
    private List<String> mImgURLs;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoViewPagerAdapter pagerAdapter = new PhotoViewPagerAdapter(this, mImgURLs);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(startPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setPositionHint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setPositionHint(startPosition);
        mButtonDownloadImg.setOnClickListener(this);
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null) {
            mImgURLs = bundle.getStringArrayList("imgURLs");
            startPosition = bundle.getInt("position");
        }
        isFullScreen = true;
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_photo);
        mViewPager = (HackyViewPager) findViewById(R.id.photo_hacky_view_pager);
        mTextView = (TextView) findViewById(R.id.tv_photo_position_hint);
        mButtonDownloadImg = (ImageButton) findViewById(R.id.btn_download_img);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
    }

    private void setPositionHint(int position) {
        mTextView.setText(position + 1 + " / " + mImgURLs.size());
    }

    public static void startMe(Context context, ArrayList<String> imgURLs, int position) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putStringArrayListExtra("imgURLs", imgURLs);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mViewPager.clearOnPageChangeListeners();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download_img:
                String url = mImgURLs.get(mViewPager.getCurrentItem());
                new Download().execute(url);
                break;
            default:
                break;
        }
    }

    private class Download extends AsyncTask<String, Void, File> {
        @Override
        protected File doInBackground(String... params) {
            File file = null;
            try {
                Bitmap bitmap = Glide.with(getApplicationContext())
                        .load(params[0])
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                File parent = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "SundayMovie");
                parent.mkdirs();
                file = new File(parent, System.currentTimeMillis() + ".jpg");
                saveFile(file, bitmap);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return file;
        }

        @Override
        protected void onPostExecute(final File file) {
            Snackbar.make(mCoordinatorLayout, "图片已保存", Snackbar.LENGTH_LONG)
                    .setAction("打开", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file), "image/*");
                            startActivity(intent);
                        }
                    }).show();

        }

        private void saveFile(File file, Bitmap bitmap) {
            OutputStream out = null;
            try {
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}