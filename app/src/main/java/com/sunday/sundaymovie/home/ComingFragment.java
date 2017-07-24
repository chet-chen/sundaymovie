package com.sunday.sundaymovie.home;

import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.sunday.sundaymovie.bean.ComingMovie;

import java.util.List;

import static com.sunday.sundaymovie.home.ComingAdapter.GROUP_COMING;
import static com.sunday.sundaymovie.home.ComingAdapter.ID_STAR;
import static com.sunday.sundaymovie.home.ComingAdapter.ID_UN_STAR;

/**
 * Created by agentchen on 2017/7/23.
 */

public class ComingFragment extends BaseFragment<ComingMovie> {
    private ComingAdapter mAdapter;

    @Override
    public void showMovies(List<ComingMovie> list) {
        if (mAdapter == null) {
            //在这里setLayoutManager为了解决没网状态下启动Fragment，将不能下拉刷新，原因未知
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new ComingAdapter(getActivity(), list, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.replaceData(list);
        }
    }

    @Override
    public void smoothScrollToTop() {
        if (mAdapter != null && mAdapter.getItemCount() > 0) {
            if (((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() > 5) {
                mRecyclerView.scrollToPosition(5);
            }
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public ComingMovie getSelectedMovie() {
        return mAdapter.getSelectedMovie();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() != GROUP_COMING) {
            return false;
        }
        ComingMovie movie = mAdapter.getSelectedMovie();
        String hint;
        switch (item.getItemId()) {
            case ID_STAR:
                mPresenter.star(movie.getId(), movie.getTitle(), movie.getImage());
                hint = "已收藏";
                break;
            case ID_UN_STAR:
                mPresenter.unStar(movie.getId());
                hint = "已取消收藏";
                break;
            default:
                return super.onContextItemSelected(item);

        }
        snackbar(hint);
        return true;
    }

}