package com.sunday.sundaymovie.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.SearchAdapter;
import com.sunday.sundaymovie.bean.SearchResult;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.SearchCallBack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchActivity extends BaseActivity implements Animation.AnimationListener {
    int mActivityCloseExitAnimation;
    private OkManager mOkManager;
    private String mQuery;
    private SearchView mSearchView;
    private ListView mListViewHistory;
    private ArrayAdapter<String> mHistoryAdapter;
    private SearchAdapter mSearchAdapter;
    private TextView mTVSearchNull;
    private RecyclerView mRecyclerView;
    private SearchResult mSearchResult;
    private ProgressBar mProgressBar;
    private CardView mCardViewHistory;
    private View mViewBGSearch;
    private boolean mIsSearching = false;
    private boolean mIsFirstSearch = true;
    private TranslateAnimation mAnimShowCard;
    private AlphaAnimation mAnimShowBG;
    private TranslateAnimation mAnimHideCard;
    private AlphaAnimation mAnimHideBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkManager = OkManager.getInstance();
        TextView TVClean = (TextView) LayoutInflater.from(this).inflate(R.layout.clean_search_history, mListViewHistory, false);
        mListViewHistory.addFooterView(TVClean);
        mHistoryAdapter = new ArrayAdapter<>(this, R.layout.item_search_history);
        mListViewHistory.setAdapter(mHistoryAdapter);
        mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mHistoryAdapter.getCount()) {
                    mSearchView.setQuery(mHistoryAdapter.getItem(position), true);
                } else {
                    hideHistory();
                    mHistoryAdapter.clear();
                    mHistoryAdapter.notifyDataSetChanged();
                    cleanSearchHistory();
                }
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doMySearch(query);
                saveSearchHistory(query);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mHistoryAdapter.getCount() != 0) {
                        mHistoryAdapter.clear();
                    }
                    mHistoryAdapter.addAll(getSearchHistory());
                    mHistoryAdapter.notifyDataSetChanged();
                    if (mHistoryAdapter.getCount() > 0) {
                        showHistory();
                    } else {
                        if (mCardViewHistory.getVisibility() == View.VISIBLE) {
                            mCardViewHistory.setVisibility(View.GONE);
                        }
                        if (mIsFirstSearch) {
                            mIsFirstSearch = false;
                        }
                    }
                } else {
                    hideHistory();
                }
            }
        });
        mViewBGSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.clearFocus();
            }
        });
        if (!"".equals(mQuery)) {
            mSearchView.setQuery(mQuery, true);
        }
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null && bundle.containsKey(SearchManager.QUERY)) {
            mQuery = bundle.getString(SearchManager.QUERY);
        }
//        使activity退出anim生效
        TypedArray activityStyle = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId,
                new int[]{android.R.attr.activityCloseEnterAnimation,
                        android.R.attr.activityCloseExitAnimation});
        mActivityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void initView(Context context) {
        flymeSetStatusBarLightMode(getWindow(), true);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doMySearch(String query) {
        if (!mIsSearching) {
            mIsSearching = true;
            mProgressBar.setVisibility(View.VISIBLE);
            mOkManager.asyncGet(Api.getSearchUrl(query), new SearchCallBack() {
                @Override
                public void onResponse(SearchResult response) {
                    if (response != null) {
                        mProgressBar.setVisibility(View.GONE);
                        mSearchResult = response;
                        modelToView();
                        mIsSearching = false;
                    } else {
                        onError();
                    }
                }

                @Override
                public void onError() {
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, "有点问题", Toast.LENGTH_SHORT).show();
                    mIsSearching = false;
                }
            });
        }
    }

    private void modelToView() {
        if (mSearchAdapter == null) {
            mSearchAdapter = new SearchAdapter(this, mSearchResult.getList(), mSearchResult.getType());
        } else {
            mSearchAdapter.replaceData(mSearchResult.getList(), mSearchResult.getType());
        }
        mRecyclerView.setAdapter(mSearchAdapter);
        if (mSearchResult.getList().size() == 0) {
            if (mTVSearchNull == null) {
                mTVSearchNull = (TextView) findViewById(R.id.tv_search_null);
            }
            mTVSearchNull.setVisibility(View.VISIBLE);
        } else if (mTVSearchNull != null) {
            mTVSearchNull.setVisibility(View.GONE);
        }
    }

    private void showHistory() {
        if (mIsFirstSearch) {
            mCardViewHistory.setVisibility(View.VISIBLE);
            mViewBGSearch.setVisibility(View.VISIBLE);
            mIsFirstSearch = false;
            return;
        }
        mAnimShowCard = new TranslateAnimation(0, 0, -mCardViewHistory.getHeight(), 0);
        mAnimShowCard.setDuration(300L);
        mAnimShowCard.setInterpolator(new DecelerateInterpolator());
        mAnimShowCard.setAnimationListener(this);

        if (mAnimShowBG == null) {
            mAnimShowBG = new AlphaAnimation(0, 1);
            mAnimShowBG.setDuration(300L);
            mAnimShowBG.setInterpolator(new DecelerateInterpolator());
            mAnimShowBG.setAnimationListener(this);
        }

        mCardViewHistory.startAnimation(mAnimShowCard);
        mViewBGSearch.startAnimation(mAnimShowBG);
    }

    private void hideHistory() {
        if (mCardViewHistory.getVisibility() == View.GONE) {
            return;
        }
        mAnimHideCard = new TranslateAnimation(0, 0, 0, -mCardViewHistory.getHeight());
        mAnimHideCard.setInterpolator(new AccelerateInterpolator());
        mAnimHideCard.setDuration(300L);
        mAnimHideCard.setAnimationListener(this);

        if (mAnimHideBG == null) {
            mAnimHideBG = new AlphaAnimation(1, 0);
            mAnimHideBG.setDuration(300L);
            mAnimHideBG.setInterpolator(new AccelerateInterpolator());
            mAnimHideBG.setAnimationListener(this);
        }
        mCardViewHistory.startAnimation(mAnimHideCard);
        mViewBGSearch.startAnimation(mAnimHideBG);
    }

    public static void startMe(Context context, String query) {
        Intent intent = new Intent(context, SearchActivity.class);
        if (query != null && !query.isEmpty()) {
            intent.putExtra(SearchManager.QUERY, query);
        }
        context.startActivity(intent);
    }

    @Override
    public void finish() {
        overridePendingTransition(0, mActivityCloseExitAnimation);
        super.finish();
    }

    private void saveSearchHistory(String history) {
        SharedPreferences.Editor editor = getSharedPreferences("search_history", MODE_PRIVATE).edit();
        editor.putString(history, history);
        editor.apply();
    }

    private void cleanSearchHistory() {
        SharedPreferences.Editor editor = getSharedPreferences("search_history", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    private List<String> getSearchHistory() {
        SharedPreferences preferences = getSharedPreferences("search_history", MODE_PRIVATE);
        Collection<?> collection = preferences.getAll().values();
        List<String> list = new ArrayList<>(collection.size());
        for (Object o : collection) {
            list.add((String) o);
        }
        return list;
    }

    //针对flyme设置light状态栏
    public boolean flymeSetStatusBarLightMode(Window window, boolean dark) {
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
    public void onAnimationStart(Animation animation) {
        if (animation == mAnimShowCard) {
            mCardViewHistory.setVisibility(View.VISIBLE);
        } else if (animation == mAnimShowBG) {
            mViewBGSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == mAnimHideCard) {
            mCardViewHistory.setVisibility(View.GONE);
        } else if (animation == mAnimHideBG) {
            mViewBGSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
