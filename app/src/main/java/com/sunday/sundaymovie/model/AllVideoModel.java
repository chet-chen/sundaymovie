package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.VideoAllCallBack;

/**
 * Created by agentchen on 2017/8/3.
 */

public class AllVideoModel {
    private OkManager mOkManager;

    public AllVideoModel() {
        mOkManager = OkManager.getInstance();
    }

    public void getAllVideo(int id, int pageIndex, VideoAllCallBack callBack) {
        mOkManager.asyncGet(Api.getVideoAllUrl(id, pageIndex), callBack);
    }
}
