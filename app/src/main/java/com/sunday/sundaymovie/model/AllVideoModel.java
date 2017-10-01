package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.bean.VideoAll;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.converter.GsonConverter;

import io.reactivex.Observable;

/**
 * Created by agentchen on 2017/8/3.
 */

public class AllVideoModel {
    private OkManager mOkManager;

    public AllVideoModel() {
        mOkManager = OkManager.getInstance();
    }

    public Observable<VideoAll> getAllVideo(int id, int pageIndex) {
        return mOkManager.get(Api.getVideoAllUrl(id, pageIndex), new GsonConverter<>(VideoAll.class));
    }
}
