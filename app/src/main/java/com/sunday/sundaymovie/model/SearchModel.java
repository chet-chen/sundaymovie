package com.sunday.sundaymovie.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.SearchCallBack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by agentchen on 2017/7/28.
 */

public class SearchModel {
    private OkManager mOkManager;
    private Context mContext;

    public SearchModel(Context context) {
        mOkManager = OkManager.getInstance();
        mContext = context;
    }

    public void doSearch(String param, SearchCallBack callBack) {
        mOkManager.asyncGet(Api.getSearchUrl(param), callBack);
    }

    public void saveSearchHistory(String text) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("search_history", MODE_PRIVATE).edit();
        editor.putString(text, text);
        editor.apply();
    }

    public void cleanSearchHistory() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("search_history", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public List<String> getSearchHistory() {
        SharedPreferences preferences = mContext.getSharedPreferences("search_history", MODE_PRIVATE);
        Collection<?> collection = preferences.getAll().values();
        List<String> list = new ArrayList<>(collection.size());
        for (Object o : collection) {
            list.add((String) o);
        }
        return list;
    }

}
