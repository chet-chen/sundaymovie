package com.sunday.sundaymovie.bean;

/**
 * Created by agentchen on 2017/2/15.
 * Email agentchen97@gmail.com
 * 搜索结果为电影 类
 */
public class SearchMovie extends Search {
    /**
     * movieId : 10502
     * movieTitle : 碟中谍 Mission: Impossible (1996)
     * cover : http://img31.mtime.cn/mt/2014/02/22/224348.27912134_96X128.jpg
     * movieLength : 110
     * genreTypes : 动作 / 冒险 / 惊悚
     * movieRating : 7.9
     */

    private int movieId;
    private String movieTitle;
    private int movieLength;
    private String genreTypes;
    private String movieRating;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(int movieLength) {
        this.movieLength = movieLength;
    }

    public String getGenreTypes() {
        return genreTypes;
    }

    public void setGenreTypes(String genreTypes) {
        this.genreTypes = genreTypes;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

}