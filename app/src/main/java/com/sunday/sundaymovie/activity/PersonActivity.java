package com.sunday.sundaymovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.ImgGridViewAdapter;
import com.sunday.sundaymovie.adapter.RecyclerPersonAdapter;
import com.sunday.sundaymovie.model.Person;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.PersonCallBack;
import com.sunday.sundaymovie.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/4/13.
 * Email agentchen97@gmail.com
 */

public class PersonActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private int mId;
    private String mTitle;
    private OkManager mOkManager;
    private Person mPerson;
    private SwipeRefreshLayout mRefreshLayout;
    private GridView mGridView;
    private Button mBtnShowMore;
    private RecyclerView mRecyclerView;
    private ImageView mIVMainImg, mIVHotMovieImg, mIVExpriences;
    private ExpandableTextView mExpandableTextView;
    private View mHotMovieGroup;
    private RatingBar mRatingBar;
    private TextView mTVNameCn, mTVNameEn, mTVAddress, mTVBirth, mTVProfession, mTVHotMovieNameCn, mTVHotMovieNameEn, mTVHotMovieType, mTVHotMovieRating, mTVExpriencesTitle, mTVExpriencesContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTitle);
        mOkManager = OkManager.getInstance();
        mRefreshLayout.setOnRefreshListener(this);

        mRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    protected void initParams(Bundle bundle) {
        mId = bundle.getInt("id");
        mTitle = bundle.getString("title");
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mGridView = (MyGridView) findViewById(R.id.grid_view_img);
        mBtnShowMore = (Button) findViewById(R.id.btn_show_more);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_relation_persons);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mIVMainImg = (ImageView) findViewById(R.id.iv_main_img);
        mIVHotMovieImg = (ImageView) findViewById(R.id.iv_hot_movie_img);
        mIVExpriences = (ImageView) findViewById(R.id.iv_expriences_img);
        mTVNameCn = (TextView) findViewById(R.id.tv_name_cn);
        mTVNameEn = (TextView) findViewById(R.id.tv_name_en);
        mTVAddress = (TextView) findViewById(R.id.tv_address_s);
        mTVBirth = (TextView) findViewById(R.id.tv_birth_s);
        mTVProfession = (TextView) findViewById(R.id.tv_profession_s);
        mExpandableTextView = (ExpandableTextView) findViewById(R.id.expandable);
        mTVHotMovieNameCn = (TextView) findViewById(R.id.tv_movie_t);
        mTVHotMovieNameEn = (TextView) findViewById(R.id.tv_movie_t_en);
        mTVHotMovieType = (TextView) findViewById(R.id.tv_movie_type);
        mTVHotMovieRating = (TextView) findViewById(R.id.tv_rating);
        mTVExpriencesTitle = (TextView) findViewById(R.id.tv_expriences_title);
        mTVExpriencesContent = (TextView) findViewById(R.id.tv_expriences_content);
        mRatingBar = (RatingBar) findViewById(R.id.rb_rating);
        mHotMovieGroup = findViewById(R.id.hot_movie_group);
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

    private void modelToView() {
        if (!this.isFinishing()) {
            Glide.with(this).load(mPerson.getImage()).placeholder(R.drawable.img_load).into(mIVMainImg);
        }
        mTVNameCn.setText(mPerson.getNameCn());
        mTVNameEn.setText(mPerson.getNameEn());
        mTVAddress.setText(mPerson.getAddress());
        mTVBirth.setText(String.format("生日: %d-%d-%d", mPerson.getBirthYear(), mPerson.getBirthMonth(), mPerson.getBirthDay()));
        mTVProfession.setText(mPerson.getProfession());
        if (mPerson.getContent() == null || mPerson.getContent().isEmpty()) {
            mExpandableTextView.setVisibility(View.GONE);
            findViewById(R.id.tv_hint_content).setVisibility(View.GONE);
        } else {
            mExpandableTextView.setText(mPerson.getContent());
        }
        List<String> imgs = new ArrayList<>();
        for (Person.ImagesBean imagesBean : mPerson.getImages()) {
            imgs.add(imagesBean.getImage());
        }
        mGridView.setAdapter(new ImgGridViewAdapter(imgs, this, 2));
        Person.HotMovieBean hotMovieBean = mPerson.getHotMovie();
        if (hotMovieBean.getMovieId() == 0) {
            mHotMovieGroup.setVisibility(View.GONE);
            findViewById(R.id.tv_hint_hot).setVisibility(View.GONE);
        } else {
            if (!this.isFinishing()) {
                Glide.with(this)
                        .load(hotMovieBean.getMovieCover())
                        .placeholder(R.drawable.img_load)
                        .into(mIVHotMovieImg);
            }
            mTVHotMovieNameCn.setText(hotMovieBean.getMovieTitleCn());
            mTVHotMovieNameEn.setText(hotMovieBean.getMovieTitleEn());
            mTVHotMovieType.setText(hotMovieBean.getType());
            if (hotMovieBean.getRatingFinal() > 0) {
                mTVHotMovieRating.setText(String.valueOf(hotMovieBean.getRatingFinal()));
                mRatingBar.setRating((float) (hotMovieBean.getRatingFinal() / 2));
            } else {
                mRatingBar.setVisibility(View.GONE);
                mTVHotMovieRating.setTextColor(getResources().getColor(R.color.colorTextBlack_3));
                mTVHotMovieRating.setText("暂无评分");
            }
            mHotMovieGroup.setOnClickListener(this);
        }
        List<Person.ExpriencesBean> mExpriences = mPerson.getExpriences();
        if (mExpriences.size() == 0) {
            findViewById(R.id.exprience_group).setVisibility(View.GONE);
        } else {
            Person.ExpriencesBean expriencesBean = mExpriences.get(0);
            if (!this.isFinishing()) {
                Glide.with(this)
                        .load(expriencesBean.getImg())
                        .placeholder(R.drawable.img_load)
                        .into(mIVExpriences);
            }
            mTVExpriencesTitle.setText(String.format("%d年 %s", expriencesBean.getYear(), expriencesBean.getTitle()));
            mTVExpriencesContent.setText(expriencesBean.getContent());
        }
        mBtnShowMore.setOnClickListener(this);
        if (mPerson.getRelationPersons().size() == 0) {
            findViewById(R.id.tv_hint_relation).setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setAdapter(new RecyclerPersonAdapter(mPerson.getRelationPersons(), this));
        }
        setTop();
    }

    @Override
    public void onRefresh() {
        mOkManager.asyncGet(Api.getPersonUrl(mId), new PersonCallBack() {
            @Override
            public void onResponse(Person response) {
                mRefreshLayout.setRefreshing(false);
                mPerson = response;
                if (mPerson == null) {
                    finish();
                }
                modelToView();
            }

            @Override
            public void onError(Exception e) {
                mRefreshLayout.setRefreshing(false);
                e.printStackTrace();
                Toast.makeText(PersonActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void startMe(Context context, int id, String title) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_more:
                ExpriencesActivity.startMe(this, (ArrayList<Person.ExpriencesBean>) mPerson.getExpriences());
                break;
            case R.id.hot_movie_group:
                MovieDetailActivity.startMe(this, mPerson.getHotMovie().getMovieId());
                break;
            default:
                break;
        }
    }

    private void setTop() {
        /*
        * 问题 activity启动，不处在最顶部
        * 解决 获取最顶部view焦点
        */
        mTVNameCn.setFocusable(true);
        mTVNameCn.setFocusableInTouchMode(true);
        mTVNameCn.requestFocus();
    }
}
