package com.sunday.sundaymovie.mvp.actor;

import com.sunday.sundaymovie.bean.Person;
import com.sunday.sundaymovie.model.PersonModel;
import com.sunday.sundaymovie.net.callback.PersonCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/26.
 */

class ActorPresenter implements ActorContract.Presenter {
    private final ActorContract.View mView;
    private final int mId;
    private final PersonModel mPersonModel;
    private Person mPerson;
    private ArrayList<String> mPhotos;

    ActorPresenter(ActorContract.View view, int id) {
        mView = view;
        mId = id;
        mView.setPresenter(this);
        mPersonModel = new PersonModel();
    }

    @Override
    public void start() {
        loadPerson();
    }

    private void loadPerson() {
        mPersonModel.getPerson(mId, new PersonCallBack() {
            @Override
            public void onResponse(Person response) {
                if (response != null) {
                    mView.removeProgressBar();
                    mPerson = response;
                    modelToView();
                } else {
                    onError();
                }
            }

            @Override
            public void onError() {
                mView.toast("有点问题");
                mView.finish();
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
        if (mPerson.getImages().size() > 0) {
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
