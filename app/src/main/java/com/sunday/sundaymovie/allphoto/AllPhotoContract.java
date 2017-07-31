package com.sunday.sundaymovie.allphoto;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/31.
 */

interface AllPhotoContract {
    interface View extends BaseView<Presenter> {
        void showTitle(String title);

        void hideProgressBar();

        void showAllImage(List<String> urls);

        void toast(String text);

        void showPhoto(ArrayList<String> urls, int position);
    }

    interface Presenter extends BasePresenter {
        void loadAllPhoto();

        void modelToView();

        void openPhoto(int postition);
    }
}
