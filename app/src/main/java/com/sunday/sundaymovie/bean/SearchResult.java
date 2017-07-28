package com.sunday.sundaymovie.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 * 搜索结果类
 */
public class SearchResult {
    /*
    * type=1    电影
    * type=3    影人
    * */
    public static final int TYPE_MOVIE = 1;
    public static final int TYPE_PERSON = 3;
    private int type;
    private List<Search> list;

    public SearchResult(int type) {
        this.type = type;
        list = new ArrayList<>();
    }

    public void addSearch(Search search) {
        list.add(search);
    }

    public int getType() {
        return type;
    }

    public List<Search> getList() {
        return list;
    }
}
