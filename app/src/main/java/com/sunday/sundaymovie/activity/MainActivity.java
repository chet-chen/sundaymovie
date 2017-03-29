package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.MainPagerAdapter;
import com.sunday.sundaymovie.fragment.ComingFragment;
import com.sunday.sundaymovie.fragment.ShowTimeFragment;

import static com.sunday.sundaymovie.R.id.drawer_layout;

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private String[] mTitles = new String[]{"正在热映", "即将上映"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), mTitles, this);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected void initParams(Bundle bundle) {

    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_main);
        setTitle("主页");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(drawer_layout);
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
        }
        return true;
    }

    private void toggleSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
    protected void onDestroy() {
        super.onDestroy();
        mTabLayout.removeOnTabSelectedListener(this);
    }
}
