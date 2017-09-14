package com.sunday.sundaymovie.mvp.expriences;

import com.sunday.sundaymovie.bean.Person;

import java.util.ArrayList;

/**
 * Created by agentchen on 2017/8/1.
 */

class ExpriencesPresenter implements ExpriencesContract.Presenter {
    private final ExpriencesContract.View mView;
    private ArrayList<Person.ExpriencesBean> mList;

    ExpriencesPresenter(ExpriencesContract.View view, ArrayList<Person.ExpriencesBean> list) {
        mView = view;
        mView.setPresenter(this);
        mList = list;
    }

    @Override
    public void subscribe() {
        mView.showExpriences(mList);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void openPhoto(int position) {
        ArrayList<String> imgUrls = new ArrayList<>(mList.size());
        for (Person.ExpriencesBean bean : mList) {
            imgUrls.add(bean.getImg());
        }
        mView.showPhoto(imgUrls, position);
    }
}
