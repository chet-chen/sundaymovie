package com.sunday.sundaymovie.person;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;
import com.sunday.sundaymovie.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/26.
 */

public interface PersonContract {
    interface View extends BaseView<Presenter> {

        void hideProgressBar();

        void showName(String name);

        void showMainImage(String url);

        void showBasicInfo(String enName, String address, int birthYear, int birthMonth, int birthDay, String profession);

        void showContent(String content);

        void hideContent();

        void showImages(List<String> urls);

        void hideImages();

        /**
         * @param rating 若为0则没有评分
         */
        void showHotMovie(String imgUrl, String nameCn, String nameEn, String movieType, double rating);

        void hideHotMovie();

        void showHotMovie(int movieId);

        void showExpriences(String imgUrl, int year, String title, String content);

        void hideExpriences();

        void showAllExpriences(ArrayList<Person.ExpriencesBean> list);

        void showRelationPersons(List<Person.RelationPersonsBean> list);

        void hideRelationPersons();

        void toast(String text);

        void finish();
    }

    interface Presenter extends BasePresenter {
        void loadPerson();

        void modelToView();

        void openHotMovie();

        void openAllExpriences();
    }
}
