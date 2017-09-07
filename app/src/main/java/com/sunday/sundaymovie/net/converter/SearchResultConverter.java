package com.sunday.sundaymovie.net.converter;

import com.sunday.sundaymovie.bean.SearchMovie;
import com.sunday.sundaymovie.bean.SearchPerson;
import com.sunday.sundaymovie.bean.SearchResult;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by agentchen on 2017/9/7.
 */

public class SearchResultConverter implements Converter<SearchResult> {
    @Override
    public SearchResult parseResponse(Response response) throws Exception {
        String responseString = response.body().string();
        //此处有bug，搜索super man，responseString可能为空字符串
        if (responseString.length() < 25) {
            return null;
        }
        String jsonStr = responseString.substring(22, responseString.length() - 3);
        JSONObject object = new JSONObject(jsonStr).getJSONObject("value");
        int type = object.getInt("type");
        SearchResult searchResult = new SearchResult(type);
        JSONArray array;
        switch (type) {
            case 1:
                array = object.getJSONObject("movieResult").getJSONArray("moreMovies");
                SearchMovie movie;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    movie = new SearchMovie();
                    if (o.has("movieId")) {
                        movie.setMovieId(o.getInt("movieId"));
                    }
                    if (o.has("movieTitle")) {
                        movie.setMovieTitle(o.getString("movieTitle"));
                    }
                    if (o.has("cover")) {
                        movie.setCover(o.getString("cover"));
                    }
                    if (o.has("movieLength")) {
                        movie.setMovieLength(o.getInt("movieLength"));
                    }
                    if (o.has("genreTypes")) {
                        movie.setGenreTypes(o.getString("genreTypes"));
                    }
                    if (o.has("movieRating")) {
                        movie.setMovieRating(o.getString("movieRating"));
                    }
                    searchResult.addSearch(movie);
                }
                break;
            case 3:
                array = object.getJSONObject("personResult").getJSONArray("morePersons");
                SearchPerson person;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    person = new SearchPerson();
                    if (o.has("personId")) {
                        person.setPersonId(o.getInt("personId"));
                    }
                    if (o.has("love")) {
                        person.setLove(o.getInt("love"));
                    }
                    if (o.has("cover")) {
                        person.setCover(o.getString("cover"));
                    }
                    if (o.has("personTitle")) {
                        person.setPersonTitle(o.getString("personTitle"));
                    }
                    if (o.has("personFilmography")) {
                        person.setPersonFilmography(o.getString("personFilmography"));
                    }
                    if (o.has("birth")) {
                        person.setBirth(o.getString("birth"));
                    }
                    searchResult.addSearch(person);
                }
                break;
        }
        return searchResult;
    }
}
