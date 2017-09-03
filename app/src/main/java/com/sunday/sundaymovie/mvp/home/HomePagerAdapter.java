package com.sunday.sundaymovie.mvp.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/3/28.
 * Email agentchen97@gmail.com
 */

class HomePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void addTab(Fragment fragment, String title) {
        mFragments.add(fragment);
        mTitles.add(title);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
