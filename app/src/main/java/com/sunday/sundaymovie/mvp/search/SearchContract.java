package com.sunday.sundaymovie.mvp.search;

import android.content.Context;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;
import com.sunday.sundaymovie.bean.Search;

import java.util.List;

/**
 * Created by agentchen on 2017/7/28.
 */

interface SearchContract {
    interface View extends BaseView<Presenter> {
        Context getContext();

        void clearSearchFocus();

        void showHistory(boolean needAnimate);

        void hideHistory(boolean needAnimate);

        void cleanSearchHistory();

        void showProgressBar();

        void hideProgressBar();

        void toast(String text);

        void showSearchResult(int type, List<Search> list);

        void showSearchNull();

        void hideSearchNull();

        void flushHistory(List<String> list);

        int getHistoryCount();

        void setQuery(String text);
    }

    interface Presenter extends BasePresenter {
        void doSearch(String query);

        void initSearchText(String text);

        void cleanSearchHistory();

        void searchFocusChange(boolean hasFocus);

        void searchBGClick();
    }
}
