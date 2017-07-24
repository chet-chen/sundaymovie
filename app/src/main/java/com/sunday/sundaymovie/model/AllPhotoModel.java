package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.ImageAllCallBack;

/**
 * Created by agentchen on 2017/7/24.
 */

public class AllPhotoModel {
    public void getAllPhoto(int id, ImageAllCallBack callBack) {
        OkManager.getInstance().asyncGet(Api.getImageAllUrl(id), callBack);
    }
}
