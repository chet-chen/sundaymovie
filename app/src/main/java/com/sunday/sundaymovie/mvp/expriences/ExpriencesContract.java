package com.sunday.sundaymovie.mvp.expriences;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;
import com.sunday.sundaymovie.bean.Person;

import java.util.ArrayList;

/**
 * Created by agentchen on 2017/8/1.
 */

interface ExpriencesContract {
    interface View extends BaseView<Presenter> {
        void showExpriences(ArrayList<Person.ExpriencesBean> list);

        void showPhoto(ArrayList<String> urls, int position);
    }

    interface Presenter extends BasePresenter {
        void openPhoto(int position);
    }
}
