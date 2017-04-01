package com.sunday.sundaymovie.util;

import android.content.Context;

/**
 * Created by agentchen on 2017/3/31.
 * Email agentchen97@gmail.com
 */

public class PixelUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
