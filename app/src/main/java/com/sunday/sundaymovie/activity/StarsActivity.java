package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.StarsMovieAdapter;
import com.sunday.sundaymovie.db.StarsTableHelper;
import com.sunday.sundaymovie.model.StarsMovie;

import java.util.ArrayList;
import java.util.List;

public class StarsActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private StarsTableHelper mHelper;
    private StarsMovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mHelper = new StarsTableHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAdapter == null) {
            mAdapter = new StarsMovieAdapter(this, mHelper.queryAll());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.refresh(mHelper.queryAll());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initParams(Bundle bundle) {

    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_stars);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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

    public static void startMe(Context context) {
        Intent intent = new Intent(context, StarsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.close();
    }
}
