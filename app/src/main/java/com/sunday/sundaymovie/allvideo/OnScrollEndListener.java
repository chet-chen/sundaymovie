package com.sunday.sundaymovie.allvideo;

import android.support.v7.widget.RecyclerView;

/**
 * Created by agentchen on 2017/3/14.
 * Email agentchen97@gmail.com
 */

public abstract class OnScrollEndListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (recyclerView == null) return;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange() - 800) {
            onScrollEnd();
        }
    }

    public abstract void onScrollEnd();
}
