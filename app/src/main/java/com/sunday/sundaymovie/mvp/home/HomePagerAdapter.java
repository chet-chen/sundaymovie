package com.sunday.sundaymovie.mvp.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by agentchen on 2017/3/28.
 * Email agentchen97@gmail.com
 */

class HomePagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    HomePagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
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
                ShowTimeFragment showTimeFragment = new ShowTimeFragment();
                showTimeFragment.setPresenter(new ShowTimePresenter(showTimeFragment));
                return showTimeFragment;
            case 1:
                ComingFragment comingFragment = new ComingFragment();
                comingFragment.setPresenter(new ComingPresenter(comingFragment));
                return comingFragment;
            default:
                return null;
        }
    }

}
