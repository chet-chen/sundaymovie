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
    private static final String KEY_ID = "id";
    private static final String KEY_URLS = "urls";
    public static final String KEY_TITLE = "title";

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
        String title = bundle.getString(KEY_TITLE);
        if (bundle.containsKey(KEY_URLS)) {
            new AllPhotoPresenter(this, bundle.getStringArrayList(KEY_URLS), title);
        } else if (bundle.containsKey(KEY_ID)) {
            new AllPhotoPresenter(this, bundle.getInt(KEY_ID), title);
        }
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
        Intent intent = getBaseIntent(context, title);
        intent.putExtra(KEY_ID, movieId);
        context.startActivity(intent);
    }

    public static void startMe(Context context, ArrayList<String> urls, String title) {
        Intent intent = getBaseIntent(context, title);
        intent.putExtra(KEY_URLS, urls);
        context.startActivity(intent);
    }

    private static Intent getBaseIntent(Context context, String title) {
        Intent intent = new Intent(context, AllPhotoActivity.class);
        intent.putExtra(KEY_TITLE, title);
        return intent;
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
        mProgressBar.animate().alpha(0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                ((ViewGroup) mProgressBar.getParent()).removeView(mProgressBar);
                mProgressBar = null;
            }
        });
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
