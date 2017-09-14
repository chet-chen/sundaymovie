package com.sunday.sundaymovie.mvp.person;

import com.sunday.sundaymovie.bean.Person;
import com.sunday.sundaymovie.model.PersonModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by agentchen on 2017/7/26.
 */

class PersonPresenter implements PersonContract.Presenter {
    private final PersonContract.View mView;
    private final int mId;
    private final PersonModel mPersonModel;
    private Person mPerson;
    private ArrayList<String> mPhotos;
    private Disposable mDisposable;

    PersonPresenter(PersonContract.View view, int id) {
        mView = view;
        mId = id;
        mView.setPresenter(this);
        mPersonModel = new PersonModel();
    }

    @Override
    public void subscribe() {
        loadPerson();
    }

    @Override
    public void unsubscribe() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    private void loadPerson() {
        mPersonModel.getPerson(mId).subscribe(new Observer<Person>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Person person) {
                if (person != null) {
                    mView.removeProgressBar();
                    mPerson = person;
                    modelToView();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.toast("有点问题");
                mView.finish();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void modelToView() {
        mView.showName(mPerson.getNameCn());
        mView.showMainImage(mPerson.getImage());
        mView.showBasicInfo(mPerson.getNameEn(), mPerson.getAddress(), mPerson.getBirthYear()
                , mPerson.getBirthMonth(), mPerson.getBirthDay(), mPerson.getProfession());
        if (mPerson.getContent() == null || mPerson.getContent().isEmpty()) {
            mView.removeContent();
        } else {
            mView.showContent(mPerson.getContent());
        }
        if (mPerson.getImages() != null && mPerson.getImages().size() > 0) {
            mPhotos = new ArrayList<>();
            for (Person.ImagesBean imagesBean : mPerson.getImages()) {
                mPhotos.add(imagesBean.getImage());
            }
            mView.showImages(mPhotos);
        } else {
            mView.removeImages();
        }
        Person.HotMovieBean hotMovie = mPerson.getHotMovie();
        if (hotMovie.getMovieId() == 0) {
            mView.removeHotMovie();
        } else {
            mView.showHotMovie(hotMovie.getMovieCover(), hotMovie.getMovieTitleCn()
                    , hotMovie.getMovieTitleEn(), hotMovie.getType());
            double rating = hotMovie.getRatingFinal();
            if (rating > 0) {
                mView.showHotMovieRating(rating);
            } else {
                mView.hideHotMovieRating();
            }
        }
        List<Person.ExpriencesBean> mExpriences = mPerson.getExpriences();
        if (mExpriences.size() == 0) {
            mView.removeExpriences();
        } else {
            Person.ExpriencesBean expriences = mExpriences.get(0);
            mView.showExpriences(expriences.getImg(), expriences.getYear()
                    , expriences.getTitle(), expriences.getContent());
        }
        List<Person.RelationPersonsBean> personsBeen = mPerson.getRelationPersons();
        if (personsBeen.size() == 0) {
            mView.removeRelationPersons();
        } else {
            mView.showRelationPersons(personsBeen);
        }
    }

    @Override
    public void openHotMovie() {
        mView.showHotMovie(mPerson.getHotMovie().getMovieId());
    }

    @Override
    public void openAllExpriences() {
        mView.showAllExpriences((ArrayList<Person.ExpriencesBean>) mPerson.getExpriences());
    }

    @Override
    public void openPhoto(int position) {
        mView.showPhoto(mPhotos, position);
    }
}
