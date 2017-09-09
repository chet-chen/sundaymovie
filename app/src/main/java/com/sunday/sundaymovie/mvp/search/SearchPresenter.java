package com.sunday.sundaymovie.mvp.search;

import com.sunday.sundaymovie.bean.Search;
import com.sunday.sundaymovie.bean.SearchResult;
import com.sunday.sundaymovie.model.SearchModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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
    private Disposable mDisposable;
    private Observer<SearchResult> mObserver;

    SearchPresenter(SearchContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mSearchModel = new SearchModel();
    }

    @Override
    public void initSearchText(String searchText) {
        mSearchText = searchText;
    }

    @Override
    public void start() {
        if (mSearchText != null && !mSearchText.isEmpty()) {
            doSearch(mSearchText);
        }
    }

    @Override
    public void onViewDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void doSearch(String query) {
        if (query.isEmpty()) {
            return;
        }
        mView.clearSearchFocus();
        if (mIsSearching) {
            if (mDisposable != null) {
                mDisposable.dispose();
                mDisposable = null;
            }
        }
        mIsSearching = true;
        mView.showProgressBar();
        if (mObserver == null) {
            mObserver = new Observer<SearchResult>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    mDisposable = d;
                }

                @Override
                public void onNext(@NonNull SearchResult searchResult) {
                    if (searchResult != null) {
                        mSearchResult = searchResult;
                        mView.hideProgressBar();
                        modelToView();
                        mIsSearching = false;
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                    mView.hideProgressBar();
                    mView.toast("有点问题");
                    mIsSearching = false;
                }

                @Override
                public void onComplete() {

                }
            };
        }
        mSearchModel.doSearch(query).subscribe(mObserver);
        mSearchModel.saveSearchHistory(query);
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
