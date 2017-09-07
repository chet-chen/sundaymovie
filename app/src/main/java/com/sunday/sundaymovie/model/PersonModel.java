package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.bean.Person;
import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.converter.PersonConverter;

import io.reactivex.Observable;

/**
 * Created by agentchen on 2017/7/26.
 */

public class PersonModel {
    public Observable<Person> getPerson(int id) {
        return OkManager.getInstance().get(Api.getPersonUrl(id), new PersonConverter());
    }
}
