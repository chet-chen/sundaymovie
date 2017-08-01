package com.sunday.sundaymovie.expriences;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.activity.BaseActivity;
import com.sunday.sundaymovie.bean.Person;
import com.sunday.sundaymovie.photo.PhotoActivity;

import java.util.ArrayList;

/**
 * Created by agentchen on 2017/8/1.
 */

public class ExpriencesActivity extends BaseActivity implements ExpriencesContract.View, ExpriencesAdapter.ItemListener {
    private ExpriencesContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void setPresenter(ExpriencesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        ArrayList<Person.ExpriencesBean> mList = bundle.getParcelableArrayList("list");
        new ExpriencesPresenter(this, mList);
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
    public void showExpriences(ArrayList<Person.ExpriencesBean> list) {
        mRecyclerView.setAdapter(new ExpriencesAdapter(list, this, this));
    }

    public static void startMe(Context context, ArrayList<Person.ExpriencesBean> list) {
        Intent intent = new Intent(context, ExpriencesActivity.class);
        intent.putParcelableArrayListExtra("list", list);
        context.startActivity(intent);
    }

    @Override
    public void onClickImage(int position) {
        mPresenter.openPhoto(position);
    }

    @Override
    public void showPhoto(ArrayList<String> urls, int position) {
        PhotoActivity.startMe(this, urls, position);
    }
}
