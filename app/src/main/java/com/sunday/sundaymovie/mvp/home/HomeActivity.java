package com.sunday.sundaymovie.mvp.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.mvp.search.SearchActivity;
import com.sunday.sundaymovie.mvp.star.StarActivity;
import com.sunday.sundaymovie.ui.AboutActivity;
import com.sunday.sundaymovie.ui.SettingsActivity;

public class HomeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener
        , NavigationView.OnNavigationItemSelectedListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private HomePagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        mAdapter = new HomePagerAdapter(fm);
        ShowTimeFragment showTimeFragment = (ShowTimeFragment) fm.findFragmentByTag(makeFragmentName(mViewPager.getId(), 0));
        if (showTimeFragment == null) {
            showTimeFragment = new ShowTimeFragment();
        }
        new ShowTimePresenter(showTimeFragment);
        mAdapter.addTab(showTimeFragment, "正在热映");

        ComingFragment comingFragment = (ComingFragment) fm.findFragmentByTag(makeFragmentName(mViewPager.getId(), 1));
        if (comingFragment == null) {
            comingFragment = new ComingFragment();
        }
        new ComingPresenter(comingFragment);
        mAdapter.addTab(comingFragment, "即将上映");

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        toggleSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                SearchActivity.startMe(this, null);
                break;
            case android.R.id.home:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
                break;
        }
        return true;
    }

    private void toggleSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout
                    , mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                ShowTimeFragment sf = (ShowTimeFragment) mAdapter.instantiateItem(mViewPager, 0);
                sf.smoothScrollToTop();
                break;
            case 1:
                ComingFragment cf = (ComingFragment) mAdapter.instantiateItem(mViewPager, 1);
                cf.smoothScrollToTop();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        mTabLayout.removeOnTabSelectedListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(Gravity.START);
        switch (item.getItemId()) {
            case R.id.menu_home:
                break;
            case R.id.menu_stars:
                StarActivity.startMe(this);
                break;
            case R.id.menu_settings:
                SettingsActivity.startMe(this);
                break;
            case R.id.menu_about:
                AboutActivity.startMe(this);
                break;
        }
        return true;
    }
}
