package com.sunday.sundaymovie.photo;

import com.sunday.sundaymovie.base.BasePresenter;
import com.sunday.sundaymovie.base.BaseView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/7/31.
 */

interface PhotoContract {
    interface View extends BaseView<Presenter> {
        void showPosition(int position, int count);

        void showSnackBar(String text, String actionText);

        void showDownloadImage(File file);

        void showPhotos(List<String> urls, int startPosition);

        int getCurrentItem();

        void toast(String text);
    }

    interface Presenter extends BasePresenter {
        void openDownloadImage();

        void downloadImage(int position);

        void onPageSelected(int position);

        void dataChange(ArrayList<String> urls);
    }
}
