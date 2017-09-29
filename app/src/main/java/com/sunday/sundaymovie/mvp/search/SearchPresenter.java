package com.sunday.sundaymovie.mvp.search;

import com.sunday.sundaymovie.bean.Search;
import com.sunday.sundaymovie.bean.SearchResult;
import com.sunday.sundaymovie.model.SearchModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by agentchen on 2017/7/28.
 */

class SearchPresenter implements SearchContract.Presenter {
    private final SearchContract.View mView;
    private final SearchModel mSearchModel;
    private SearchResult mSearchResult;
    private boolean mIsFirstFocus = true;
    private String mSearchText;
    private CompositeDisposable mDisposable;
    private Observer<SearchResult> mObserver;

    SearchPresenter(SearchContract.View view) {
        mView = view;
        mSearchModel = new SearchModel();
        mDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void initSearchText(String searchText) {
        mSearchText = searchText;
    }

    @Override
    public void subscribe() {
        if (mSearchText != null && !mSearchText.isEmpty()) {
            doSearch(mSearchText);
        }
    }

    @Override
    public void unsubscribe() {
        mDisposable.clear();
    }

    @Override
    public void doSearch(String query) {
        if (query.isEmpty()) {
            return;
        }
        mView.clearSearchFocus();
        mDisposable.clear();
        mView.showProgressBar();
        if (mObserver == null) {
            mObserver = new Observer<SearchResult>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    mDisposable.add(d);
                }

                @Override
                public void onNext(@NonNull SearchResult searchResult) {
                    if (searchResult != null) {
                        mSearchResult = searchResult;
                        mView.hideProgressBar();
                        modelToView();
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                    mView.hideProgressBar();
                    mView.toast("有点问题");
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
        mView.cleanSearchHistory();
        mView.clearSearchFocus();
        mSearchModel.cleanSearchHistory();
    }

    @Override
    public void searchFocusChange(boolean hasFocus) {
        if (hasFocus) {
            mView.flushHistory(mSearchModel.getSearchHistory());
            if (mView.getHistoryCount() > 0) {
                if (mIsFirstFocus) {
                    mView.showHistory(false);
                } else {
                    mView.showHistory(true);
                }
            }
        } else {
            mView.hideHistory(true);
        }
        mIsFirstFocus = false;
    }

    @Override
    public void searchBGClick() {
        mView.clearSearchFocus();
    }
}
