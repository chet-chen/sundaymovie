package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.ExpriencesAdapter;
import com.sunday.sundaymovie.bean.Person;

import java.util.ArrayList;

/**
 * Created by agentchen on 2017/4/15.
 * Email agentchen97@gmail.com
 */

public class ExpriencesActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Person.ExpriencesBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setAdapter(new ExpriencesAdapter(mList, this));
    }

    @Override
    protected void initParams(Bundle bundle) {
        mList = bundle.getParcelableArrayList("list");
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_expriences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        setTitle("个人经历");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_expriences);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    public static void startMe(Context context, ArrayList<Person.ExpriencesBean> list) {
        Intent intent = new Intent(context, ExpriencesActivity.class);
        intent.putParcelableArrayListExtra("list", list);
        context.startActivity(intent);
    }
}
