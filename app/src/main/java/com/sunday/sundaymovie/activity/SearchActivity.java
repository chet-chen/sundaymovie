package com.sunday.sundaymovie.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.SearchAdapter;
import com.sunday.sundaymovie.model.SearchResult;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.SearchCallBack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchActivity extends BaseActivity {
    int activityCloseExitAnimation;
    private OkManager mOkManager;
    private String query;
    private SearchView mSearchView;
    private ListView mListViewHistory;
    private ArrayAdapter<String> mHistoryAdapter;
    private TextView mTextView, mTVSearchNull;
    private RecyclerView mRecyclerView;
    private SearchResult mSearchResult;
    private ProgressBar mProgressBar;
    private boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<String> history = getSearchHistory();
        mHistoryAdapter = new ArrayAdapter<>(this, R.layout.item_search_history, history);
        mListViewHistory.setAdapter(mHistoryAdapter);
        mOkManager = OkManager.getInstance();
        mTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.clean_search_history, mListViewHistory, false);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryAdapter.clear();
                mListViewHistory.removeFooterView(mTextView);
                mHistoryAdapter.notifyDataSetChanged();
                cleanSearchHistory();
            }
        });
        if (mHistoryAdapter.getCount() != 0) {
            mListViewHistory.addFooterView(mTextView);
        }
        mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < history.size()) {
                    mSearchView.setQuery(history.get(position), true);
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
        if (!"".equals(query)) {
            mSearchView.setQuery(query, true);
        }
    }

    @Override
    protected void initParams(Bundle bundle) {
        if (bundle != null && bundle.containsKey(SearchManager.QUERY)) {
            query = bundle.getString(SearchManager.QUERY);
        }
//        使activity退出anim生效
        TypedArray activityStyle = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId,
                new int[]{android.R.attr.activityCloseEnterAnimation,
                        android.R.attr.activityCloseExitAnimation});
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doMySearch(String query) {
        if (!isSearching) {
            isSearching = true;
            mProgressBar.setVisibility(View.VISIBLE);
            mOkManager.asyncGet(Api.getSearchUrl(query), new SearchCallBack() {
                @Override
                public void onResponse(SearchResult response) {
                    mProgressBar.setVisibility(View.GONE);
                    mSearchResult = response;
                    if (mSearchResult != null) {
                        modelToView();
                    }
                    mListViewHistory.setVisibility(View.GONE);
                    isSearching = false;
                }

                @Override
                public void onError(Exception e) {
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    isSearching = false;
                }
            });
        }
    }

    private void modelToView() {
        mRecyclerView.setAdapter(
                new SearchAdapter(this, mSearchResult.getList(), mSearchResult.getType()));
        if (mSearchResult.getList().size() == 0) {
            if (mTVSearchNull == null) {
                mTVSearchNull = (TextView) findViewById(R.id.tv_search_null);
            }
            mTVSearchNull.setVisibility(View.VISIBLE);
        } else if (mTVSearchNull != null) {
            mTVSearchNull.setVisibility(View.GONE);
        }
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
        super.finish();
        overridePendingTransition(0, activityCloseExitAnimation);
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

}
