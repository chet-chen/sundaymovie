package com.sunday.sundaymovie.mvp.allphoto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.adapter.GridPhotosAdapter;
import com.sunday.sundaymovie.base.BaseActivity;
import com.sunday.sundaymovie.mvp.photo.PhotoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/31.
 */

public class AllPhotoActivity extends BaseActivity implements AllPhotoContract.View, GridPhotosAdapter.ItemListener {
    private AllPhotoContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void setPresenter(AllPhotoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initParams(Bundle bundle) {
        new AllPhotoPresenter(this, bundle.getInt("movieId"), bundle.getString("title"));
    }

    @Override
    protected void initView(Context context) {
        setContentView(R.layout.activity_all_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.image_all_toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public static void startMe(Context context, int movieId, String title) {
        Intent intent = new Intent(context, AllPhotoActivity.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestroy();
    }

    @Override
    public void showTitle(String title) {
        setTitle(title);
    }

    @Override
    public void removeProgressBar() {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(300L);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ViewGroup) mProgressBar.getParent()).removeView(mProgressBar);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mProgressBar.startAnimation(animation);
    }

    @Override
    public void showAllImage(List<String> urls) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        int space = getResources().getDimensionPixelSize(R.dimen.images_space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
        GridPhotosAdapter adapter = new GridPhotosAdapter(urls, this, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhoto(ArrayList<String> urls, int position) {
        PhotoActivity.startMe(this, urls, position);
    }

    @Override
    public void onClickPhoto(int position) {
        mPresenter.openPhoto(position);
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int mSpace;

        SpaceItemDecoration(int space) {
            mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            outRect.top = mSpace;
            if (position % 3 == 1) {
                outRect.left = mSpace;
                outRect.right = mSpace;
            }
        }
    }
}
