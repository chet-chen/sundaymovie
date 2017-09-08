package com.sunday.sundaymovie.mvp.search;

import android.animation.Animator;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.bean.Search;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by agentchen on 2017/7/28.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View {
    private SearchContract.Presenter mPresenter;
    private SearchView mSearchView;
    private ListView mListViewHistory;
    private ArrayAdapter<String> mHistoryAdapter;
    private SearchAdapter mSearchAdapter;
    private TextView mTVSearchNull;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private CardView mCardViewHistory;
    private View mViewBGSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView TVClean = (TextView) LayoutInflater.from(this).inflate(R.layout.clean_search_history, mListViewHistory, false);
        mListViewHistory.addFooterView(TVClean);
        mHistoryAdapter = new ArrayAdapter<>(this, R.layout.item_search_history);
        mListViewHistory.setAdapter(mHistoryAdapter);
        mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mHistoryAdapter.getCount()) {
                    setQuery(mHistoryAdapter.getItem(position));
                } else {
                    mPresenter.cleanSearchHistory();
                    cleanSearchHistory();
                }
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.doSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mPresenter.searchFocusChange(hasFocus);
            }
        });
        mViewBGSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.searchBGClick();
            }
        });
        mPresenter.start();
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        SearchContract.Presenter presenter = new SearchPresenter(this);
        if (bundle != null && bundle.containsKey(SearchManager.QUERY)) {
            String text = bundle.getString(SearchManager.QUERY);
            if (text != null && text.isEmpty()) {
                presenter.initSearchText(text);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Override
    protected void initView(Context context) {
//        flymeSetStatusBarLightMode(getWindow(), true);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListViewHistory = (ListView) findViewById(R.id.lv_search_history);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_search);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCardViewHistory = (CardView) findViewById(R.id.cv_search_parent);
        mViewBGSearch = findViewById(R.id.bg_search);
        mTVSearchNull = (TextView) findViewById(R.id.tv_search_null);
    }

    public static void startMe(Context context, String query) {
        Intent intent = new Intent(context, SearchActivity.class);
        if (query != null && !query.isEmpty()) {
            intent.putExtra(SearchManager.QUERY, query);
        }
        context.startActivity(intent);
    }

    @Override
    public void setQuery(String text) {
        mSearchView.setQuery(text, true);
    }

    @Override
    public void showSearchResult(int type, List<Search> list) {
        if (mSearchAdapter == null) {
            mSearchAdapter = new SearchAdapter(this, list, type);
            mRecyclerView.setAdapter(mSearchAdapter);
        } else {
            mSearchAdapter.replaceData(list, type);
        }
    }

    @Override
    public void showSearchNull() {
        mTVSearchNull.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchNull() {
        mTVSearchNull.setVisibility(View.INVISIBLE);
    }

    @Override
    public void flushHistory(List<String> list) {
        if (mHistoryAdapter.getCount() > 0) {
            mHistoryAdapter.clear();
        }
        mHistoryAdapter.addAll(list);
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public int getHistoryCount() {
        return mHistoryAdapter.getCount();
    }

    @Override
    public void clearSearchFocus() {
        mSearchView.clearFocus();
    }

    private Animator.AnimatorListener mAnimatorListenerShow;
    private Animator.AnimatorListener mAnimatorListenerHide;

    @Override
    public void showHistory(boolean needAnimate) {
        if (needAnimate) {
            if (mAnimatorListenerShow == null) {
                mAnimatorListenerShow = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mCardViewHistory.setVisibility(View.VISIBLE);
                        mViewBGSearch.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };
            }
            mCardViewHistory.animate()
                    .translationY(-6f)
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(mAnimatorListenerShow);
            mViewBGSearch.animate()
                    .alpha(1f)
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(mAnimatorListenerShow);
        } else {
            mCardViewHistory.setVisibility(View.VISIBLE);
            mViewBGSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideHistory(boolean needAnimate) {
        if (needAnimate) {
            if (mAnimatorListenerHide == null) {
                mAnimatorListenerHide = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCardViewHistory.setVisibility(View.INVISIBLE);
                        mViewBGSearch.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };
            }
            mCardViewHistory.animate()
                    .translationY(-mCardViewHistory.getHeight())
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(mAnimatorListenerHide);
            mViewBGSearch.animate()
                    .alpha(0f)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(mAnimatorListenerHide);
        } else {
            mCardViewHistory.setVisibility(View.INVISIBLE);
            mViewBGSearch.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void cleanSearchHistory() {
        if (mHistoryAdapter.getCount() > 0) {
            mHistoryAdapter.clear();
        }
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
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private boolean flymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.search_activity_out);
    }
}
