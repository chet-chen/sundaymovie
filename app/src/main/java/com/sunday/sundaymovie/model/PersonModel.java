package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.PersonCallBack;

/**
 * Created by agentchen on 2017/7/26.
 */

public class PersonModel {
    public void getPerson(int id, PersonCallBack callBack) {
        OkManager.getInstance().asyncGet(Api.getPersonUrl(id), callBack);
    }
}
