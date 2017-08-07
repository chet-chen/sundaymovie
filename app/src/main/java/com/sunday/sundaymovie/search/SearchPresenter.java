package com.sunday.sundaymovie.search;

import com.sunday.sundaymovie.bean.Search;
import com.sunday.sundaymovie.bean.SearchResult;
import com.sunday.sundaymovie.model.SearchModel;
import com.sunday.sundaymovie.net.callback.SearchCallBack;

import java.util.List;

/**
 * Created by agentchen on 2017/7/28.
 */

class SearchPresenter implements SearchContract.Presenter {
    private final SearchContract.View mView;
    private final SearchModel mSearchModel;
    private SearchResult mSearchResult;
    private boolean mIsSearching = false;
    private boolean mIsFirstFocus = true;
    private String mSearchText;

    SearchPresenter(SearchContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mSearchModel = new SearchModel(view.getContext());
    }

    @Override
    public void setSearchText(String searchText) {
        mSearchText = searchText;
    }

    @Override
    public void start() {
        if (mSearchText != null && !mSearchText.isEmpty()) {
            doSearch(mSearchText);
        }
    }

    @Override
    public void doSearch(String query) {
        if (query.isEmpty()) {
            return;
        }
        mView.clearSearchFocus();
        if (!mIsSearching) {
            mIsSearching = true;
            mView.showProgressBar();
            mSearchModel.doSearch(query, new SearchCallBack() {
                @Override
                public void onResponse(SearchResult response) {
                    if (response != null) {
                        mSearchResult = response;
                        mView.hideProgressBar();
                        modelToView();
                        mIsSearching = false;
                    } else {
                        onError();
                    }
                }

                @Override
                public void onError() {
                    mView.hideProgressBar();
                    mView.toast("有点问题");
                    mIsSearching = false;
                }
            });
            mSearchModel.saveSearchHistory(query);
        }
    }

    private void modelToView() {
        List<Search> searches = mSearchResult.getList();
        mView.showSearchResult(mSearchResult.getType(), searches);
        if (searches.size() == 0) {
            mView.showSearchNull();
        } else {
            mView.hideSearchNull();
        }
    }

    @Override
    public void cleanSearchHistory() {
        mView.hideHistory(true);
        mView.cleanSearchHistory();
        mSearchModel.cleanSearchHistory();
    }

    @Override
    public void searchFocusChange(boolean hasFocus) {
        if (hasFocus) {
            mView.flushHistory(mSearchModel.getSearchHistory());
            if (mView.getHistoryCount() > 0) {
                if (mIsFirstFocus) {
                    mView.showHistory(false);
                    mIsFirstFocus = false;
                } else {
                    mView.showHistory(true);
                }
            } else {
                mView.hideHistory(false);
                if (mIsFirstFocus) {
                    mIsFirstFocus = false;
                }
            }
        } else {
            mView.hideHistory(true);
        }
    }

    @Override
    public void searchBGClick() {
        mView.clearSearchFocus();
    }
}
