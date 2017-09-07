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
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                StarMovie starMovie = new StarMovie(id, name, imageURL);
                mHelper.insert(starMovie);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void unstarMovie(final int id) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                mHelper.delete(id);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }


    public Observable<Boolean> isStar(final int id) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(mHelper.queryIsExist(id));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
