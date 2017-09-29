package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.base.BaseApplication;
import com.sunday.sundaymovie.bean.StarMovie;
import com.sunday.sundaymovie.db.StarTableHelper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by agentchen on 2017/7/23.
 */

public class StarModel {
    private static StarTableHelper mHelper;

    public StarModel() {
        mHelper = new StarTableHelper(BaseApplication.getContext());
    }

    public Observable<List<StarMovie>> getAllStarMovie() {
        return Observable.create(new ObservableOnSubscribe<List<StarMovie>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<StarMovie>> e) throws Exception {
                e.onNext(mHelper.queryAll());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void starMovie(final int id, final String name, final String imageURL) {
        StarMovie starMovie = new StarMovie(id, name, imageURL);
        mHelper.insert(starMovie);
    }

    public void unstarMovie(final int id) {
        mHelper.delete(id);
    }

    public boolean isStar(int id) {
        return mHelper.queryIsExist(id);
    }
}
