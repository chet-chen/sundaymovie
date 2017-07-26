package com.sunday.sundaymovie.person;

import com.sunday.sundaymovie.bean.Person;
import com.sunday.sundaymovie.model.PersonModel;
import com.sunday.sundaymovie.net.callback.PersonCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/26.
 */

public class PersonPresenter implements PersonContract.Presenter {
    private final PersonContract.View mView;
    private final int mId;
    private final PersonModel mPersonModel;
    private Person mPerson;

    public PersonPresenter(PersonContract.View view, int id) {
        mView = view;
        mId = id;
        mView.setPresenter(this);
        mPersonModel = new PersonModel();
    }

    @Override
    public void start() {
        loadPerson();
    }

    @Override
    public void loadPerson() {
        mPersonModel.getPerson(mId, new PersonCallBack() {
            @Override
            public void onResponse(Person response) {
                if (response != null) {
                    mView.hideProgressBar();
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

    @Override
    public void modelToView() {
        mView.showName(mPerson.getNameCn());
        mView.showMainImage(mPerson.getImage());
        mView.showBasicInfo(mPerson.getNameEn(), mPerson.getAddress(), mPerson.getBirthYear()
                , mPerson.getBirthMonth(), mPerson.getBirthDay(), mPerson.getProfession());
        if (mPerson.getContent() == null || mPerson.getContent().isEmpty()) {
            mView.hideContent();
        } else {
            mView.showContent(mPerson.getContent());
        }
        if (mPerson.getImages().size() > 0) {
            List<String> imgs = new ArrayList<>();
            for (Person.ImagesBean imagesBean : mPerson.getImages()) {
                imgs.add(imagesBean.getImage());
            }
            mView.showImages(imgs);
        } else {
            mView.hideImages();
        }
        Person.HotMovieBean hotMovie = mPerson.getHotMovie();
        if (hotMovie.getMovieId() == 0) {
            mView.hideHotMovie();
        } else {
            mView.showHotMovie(hotMovie.getMovieCover(), hotMovie.getMovieTitleCn()
                    , hotMovie.getMovieTitleEn(), hotMovie.getType(), hotMovie.getRatingFinal());
        }
        List<Person.ExpriencesBean> mExpriences = mPerson.getExpriences();
        if (mExpriences.size() == 0) {
            mView.hideExpriences();
        } else {
            Person.ExpriencesBean expriences = mExpriences.get(0);
            mView.showExpriences(expriences.getImg(), expriences.getYear()
                    , expriences.getTitle(), expriences.getContent());
        }
        List<Person.RelationPersonsBean> personsBeen = mPerson.getRelationPersons();
        if (personsBeen.size() == 0) {
            mView.hideRelationPersons();
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
}
