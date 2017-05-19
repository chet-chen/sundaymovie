package com.sunday.sundaymovie.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by agentchen on 2017/5/18.
 * Email agentchen97@gmail.com
 */

public class MyBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {
    public MyBehavior() {
        super();
    }

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        float translationY = getFabTranslationYForSnackBar(parent, child);
        child.setTranslationY(translationY);
        return true;
    }

    private float getFabTranslationYForSnackBar(CoordinatorLayout parent,
                                                RelativeLayout fab) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(fab);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                //view.getHeight()固定为144
                //ViewCompat.getTranslationY(view)从144-0，再从0-144
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(view) - view.getHeight());
            }
        }

        return minOffset;
    }
}
