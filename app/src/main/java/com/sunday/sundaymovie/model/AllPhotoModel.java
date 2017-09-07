package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.bean.AllPhoto;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.converter.AllPhotoConverter;

import io.reactivex.Observable;

/**
 * Created by agentchen on 2017/7/24.
 */

public class AllPhotoModel {
    public Observable<AllPhoto> getAllPhoto(int id) {
        return OkManager.getInstance().get(Api.getImageAllUrl(id), new AllPhotoConverter());
    }
}
