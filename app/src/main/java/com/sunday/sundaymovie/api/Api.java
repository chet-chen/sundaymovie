package com.sunday.sundaymovie.api;

/**
 * Created by agentchen on 2017/2/15.
 * Email agentchen97@gmail.com
 */
public class Api {
    private static final String SEARCH_HEAD = "http://service.channel.mtime.com/Search.api" +
            "?Ajax_CallBack=true" +
            "&Ajax_CallBackType=Mtime.Channel.Services" +
            "&Ajax_CallBackMethod=GetSearchResult" +
            "&Ajax_CrossDomain=1" +
            "&Ajax_CallBackArgument0=";
    private static final String SEARCH_END = "&Ajax_CallBackArgument1=0" +
            "&Ajax_CallBackArgument2=790" +
            "&Ajax_CallBackArgument3=0" +
            "&Ajax_CallBackArgument4=1";
    private static final String PERSON_URL_HEAD = "https://ticket-api-m.mtime.cn/person/detail.api?personId=";
    private static final String PERSON_URL_END = "&cityId=290";
    private static final String MOVIE_URL = "https://ticket-api-m.mtime.cn/movie/detail.api?locationId=290&movieId=";
    private static final String IMAGE_ALL = "https://api-m.mtime.cn/Movie/ImageAll.api?movieId=";
    private static final String VIDEO_ALL = "https://api-m.mtime.cn/Movie/Video.api?pageIndex=1&movieId=";
    public static final String SHOW_TIME = "https://api-m.mtime.cn/Showtime/LocationMovies.api?locationId=290";
    public static final String COMING_MOVIE = "https://api-m.mtime.cn/Movie/MovieComingNew.api?locationId=290";

    public static String getPersonUrl(int id) {
        return PERSON_URL_HEAD + id + PERSON_URL_END;
    }

    public static String getSearchUrl(String param) {
        return SEARCH_HEAD + param + SEARCH_END;
    }

    public static String getMovieUrl(int id) {
        return MOVIE_URL + id;
    }

    public static String getImageAllUrl(int id) {
        return IMAGE_ALL + id;
    }

    public static String getVideoAllYrl(int id) {
        return VIDEO_ALL + id;
    }
}
