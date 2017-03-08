package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.PhotoViewPagerAdapter;
import com.sunday.sundaymovie.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends BaseActivity {
    int startPosition;
    private ViewPager mViewPager;
    private TextView mTextView;
    private List<String> mImgURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private void upDatePositionHint(int position) {
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
}