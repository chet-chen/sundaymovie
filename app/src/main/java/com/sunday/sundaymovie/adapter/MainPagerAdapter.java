package com.sunday.sundaymovie.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sunday.sundaymovie.fragment.ComingFragment;
import com.sunday.sundaymovie.fragment.ShowTimeFragment;

/**
 * Created by agentchen on 2017/3/28.
 * Email agentchen97@gmail.com
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private Context mContext;

    public MainPagerAdapter(FragmentManager fm, String[] titles, Context context) {
        super(fm);
        mTitles = titles;
        mContext = context;

    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ShowTimeFragment();
            case 1:
                return new ComingFragment();
            default:
                return null;
        }
    }

}
