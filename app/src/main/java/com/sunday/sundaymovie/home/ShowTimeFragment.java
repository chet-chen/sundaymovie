package com.sunday.sundaymovie.home;

import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.sunday.sundaymovie.bean.ShowTimeMovies;

import java.util.List;

import static com.sunday.sundaymovie.home.ShowTimeAdapter.GROUP_SHOW_TIME;
import static com.sunday.sundaymovie.home.ShowTimeAdapter.ID_STAR;
import static com.sunday.sundaymovie.home.ShowTimeAdapter.ID_UN_STAR;

/**
 * Created by agentchen on 2017/7/23.
 */

public class ShowTimeFragment extends BaseFragment<ShowTimeMovies.MsBean> {
    private ShowTimeAdapter mAdapter;

    @Override
    protected void recreatePresenter() {
        new ShowTimePresenter(this);
    }

    @Override
    public void showMovies(List<ShowTimeMovies.MsBean> list) {
        if (mAdapter == null) {
            //在这里setLayoutManager为了解决没网状态下启动Fragment，将不能下拉刷新，原因未知
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new ShowTimeAdapter(getActivity(), list, this);
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
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() != GROUP_SHOW_TIME) {
            return false;
        }
        ShowTimeMovies.MsBean msBean = mAdapter.getSelectedMovie();
        String hint;
        switch (item.getItemId()) {
            case ID_STAR:
                mPresenter.star(msBean.getId(), msBean.getTCn(), msBean.getImg());
                hint = "已收藏";
                break;
            case ID_UN_STAR:
                mPresenter.unStar(msBean.getId());
                hint = "已取消收藏";
                break;
            default:
                return super.onContextItemSelected(item);
        }
        snackBar(hint);
        return true;
    }

}
