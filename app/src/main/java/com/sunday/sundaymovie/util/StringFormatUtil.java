package com.sunday.sundaymovie.util;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Created by agentchen on 2017/2/23.
 * Email agentchen97@gmail.com
 */

public class StringFormatUtil {

    private static StringBuilder mFormatBuilder = new StringBuilder();
    private static Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    public static String getTimeString(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

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
