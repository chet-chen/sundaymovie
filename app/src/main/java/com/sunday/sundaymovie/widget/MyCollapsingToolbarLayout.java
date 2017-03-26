package com.sunday.sundaymovie.widget;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;

/**
 * Created by agentchen on 2017/3/9.
 * Email agentchen97@gmail.com
 * 此类为了避免 CollapsingToolbarLayout 的一个bug
 * bug: 在有CollapsingToolbarLayout的activity下,启动一个不同orientation的activity，原activity在滑动时title的位置不对
 * 参考: http://stackoverflow.com/questions/39663363/bug-in-appbarlayout-wrong-title-behavior-when-initially-collapsed
 *
 * 2017/3/22 今天更新了 com.android.support:design 库，发现使用此类又会出现bug，反之则不出现，应该是google修复了bug
 */

public class MyCollapsingToolbarLayout extends CollapsingToolbarLayout {
    public MyCollapsingToolbarLayout(Context context) {
        super(context);
    }

    public MyCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            super.onLayout(true, left, top, right, bottom);
        }
    }
}
