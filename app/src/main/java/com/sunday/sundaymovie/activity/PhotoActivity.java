package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.PhotoViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {
    int startPosition;
    private ViewPager mViewPager;
    private TextView mTextView;
    private List<String> mImgURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFull();
        setContentView(R.layout.activity_photo);
        init();
        Intent intent = getIntent();
        mImgURLs = intent.getExtras().getStringArrayList("imgURLs");
        startPosition = intent.getExtras().getInt("position");
        mViewPager.setAdapter(new PhotoViewPagerAdapter(this, mImgURLs));
        mViewPager.setCurrentItem(startPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                upDatePositionHint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        upDatePositionHint(startPosition);
    }

    private void upDatePositionHint(int position) {
        mTextView.setText(position + 1 + " / " + mImgURLs.size());
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.photo_view_pager);
        mTextView = (TextView) findViewById(R.id.tv_photo_position_hint);
    }

    public static void startMe(Context context, ArrayList<String> imgURLs, int position) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putStringArrayListExtra("imgURLs", imgURLs);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    private void setFull() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        mViewPager.clearOnPageChangeListeners();
        super.onDestroy();
    }
}
