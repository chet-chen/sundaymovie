package com.sunday.sundaymovie.util;

import java.util.List;

/**
 * Created by agentchen on 2017/2/23.
 * Email agentchen97@gmail.com
 */

public class StringFormatUtil {
    public static String getMovieReleaseText(String releaseDate, String releaseArea) {
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
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            sb.append("/");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
