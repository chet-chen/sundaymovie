package com.sunday.sundaymovie.mvp.star;

import com.sunday.sundaymovie.bean.StarMovie;
import com.sunday.sundaymovie.model.StarModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by agentchen on 2017/8/1.
 */

class StarPresenter implements StarContract.Presenter {
    private final StarContract.View mView;
    private final StarModel mStarModel;
    private List<StarMovie> mList;
    private CompositeDisposable mDisposable;

    StarPresenter(StarContract.View view) {
        mView = view;
        mStarModel = new StarModel();
        mDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadStarMovie();
    }

    @Override
    public void unsubscribe() {
        mDisposable.clear();
    }

    private void loadStarMovie() {
        mStarModel.getAllStarMovie().subscribe(new Observer<List<StarMovie>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull List<StarMovie> starMovies) {
                mList = starMovies;
                mView.showStarMovie(mList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void openMovie(int position) {
        mView.showMovie(mList.get(position).getId());
    }
}
