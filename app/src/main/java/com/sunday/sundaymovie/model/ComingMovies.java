package com.sunday.sundaymovie.model;

import java.util.List;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 * 此类为即将上映电影的集合
 */
public class ComingMovies {
    //    最受关注
    private List<ComingMovie> attention;
    //    所有
    private List<ComingMovie> moviecomings;

    public List<ComingMovie> getAttention() {
        return attention;
    }

    public void setAttention(List<ComingMovie> attention) {
        this.attention = attention;
    }

    public List<ComingMovie> getMoviecomings() {
        return moviecomings;
    }

    public void setMoviecomings(List<ComingMovie> moviecomings) {
        this.moviecomings = moviecomings;
    }
}
