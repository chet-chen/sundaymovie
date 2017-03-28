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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.RecyclerSearchAdapter;
import com.sunday.sundaymovie.api.Api;
import com.sunday.sundaymovie.model.SearchResult;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.SearchCallBack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    int activityCloseExitAnimation;
    private String query;
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayAdapter<String> mHistoryAdapter;
    private TextView mTextView, mTVSearchNull;
    private RecyclerView mRecyclerView;
    private SearchResult mSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<String> history = getSearchHistory();
        mHistoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
        mListView.setAdapter(mHistoryAdapter);

        mTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.clean_search_history, null, false);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryAdapter.clear();
                mListView.removeFooterView(mTextView);
                mHistoryAdapter.notifyDataSetChanged();
                cleanSearchHistory();
            }
        });
        if (mHistoryAdapter.getCount() != 0) {
            mListView.addFooterView(mTextView);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.lv_search_history);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_search);
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
        return true;
    }

    private void doMySearch(String query) {
        OkManager.getInstance().asyncGet(Api.getSearchUrl(query), new SearchCallBack() {
            @Override
            public void onResponse(SearchResult response) {
                mSearchResult = response;
                modelToView();
                mListView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(SearchActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void modelToView() {
        mRecyclerView.setAdapter(
                new RecyclerSearchAdapter(this, mSearchResult.getList(), mSearchResult.getType()));
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

}
