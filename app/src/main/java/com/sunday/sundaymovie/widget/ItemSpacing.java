package com.sunday.sundaymovie.widget;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by agentchen on 2017/11/28.
 */

public class ItemSpacing extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public ItemSpacing(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            getGridOffsets(outRect, position, (GridLayoutManager) parent.getLayoutManager());
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            getLinearOffsets(outRect, position, (LinearLayoutManager) parent.getLayoutManager());
        }
    }

    private void getGridOffsets(Rect outRect, int position, GridLayoutManager layoutManager) {
        final int spanCount = layoutManager.getSpanCount();
        final float total = mSpace * (spanCount - 1) / spanCount;
        final float ahead = mSpace * (position % spanCount) / spanCount;
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            if (position > spanCount - 1) {
                outRect.top = Math.round(mSpace);
            }
            if (position % spanCount == 0) {
                outRect.right = Math.round(total);
            } else if (position % spanCount == spanCount - 1) {
                outRect.left = Math.round(total);
            } else {
                outRect.left = Math.round(ahead);
                outRect.right = Math.round(total - ahead);
            }
        } else if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            if (position > spanCount - 1) {
                outRect.left = Math.round(mSpace);
            }
            if (position % spanCount == 0) {
                outRect.bottom = Math.round(total);
            } else if (position % spanCount == spanCount - 1) {
                outRect.top = Math.round(total);
            } else {
                outRect.top = Math.round(ahead);
                outRect.bottom = Math.round(total - ahead);
            }
        }
    }

    private void getLinearOffsets(Rect outRect, int position, LinearLayoutManager layoutManager) {
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            if (position > 0) {
                outRect.top = mSpace;
            }
        } else if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            if (position > 0) {
                outRect.left = mSpace;
            }
        }
    }
}
