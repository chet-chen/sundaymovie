package com.sunday.sundaymovie.model;

import com.sunday.sundaymovie.net.Api;
import com.sunday.sundaymovie.net.OkManager;
import com.sunday.sundaymovie.net.callback.MovieCallBack;

import java.util.List;

/**
 * Created by agentchen on 2017/7/24.
 */

public class MovieDetailModel {
    public void getMovieDetail(int movieId, MovieCallBack callBack) {
        OkManager.getInstance().asyncGet(Api.getMovieUrl(movieId), callBack);
    }

    public static String getMovieReleaseText(String releaseDate, String releaseArea) {
        if (releaseDate.length() < 4) {
            return releaseDate + releaseArea;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("上映日期: ");
        sb.append(releaseDate.substring(0, 4))
                .append("-");
        if ("0".equals(releaseDate.substring(4))) {
            sb.append(releaseDate.substring(5));
        } else {
            sb.append(releaseDate.substring(4, 6));
        }
        sb.append("-");
        if ("0".equals(releaseDate.substring(6))) {
            sb.append(releaseDate.substring(7));
        } else {
            sb.append(releaseDate.substring(6, 8));
        }
        sb.append("(")
                .append(releaseArea)
                .append(")");
        return sb.toString();
    }

    public static String getMovieType(List<String> list) {
        if (list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            sb.append("/");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
