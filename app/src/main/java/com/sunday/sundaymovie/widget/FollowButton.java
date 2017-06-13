package com.sunday.sundaymovie.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sunday.sundaymovie.R;

/**
 * Created by agentchen on 2017/2/20.
 * Email agentchen97@gmail.com
 */

public class FollowButton extends FrameLayout {
    protected boolean isFollowed;
    private TextView followTv;
    private TextView unFollowTv;
    float centerX;
    float centerY;
    float revealRadius = 1080;
    private Path path = new Path();
    private ValueAnimator animator;

    public FollowButton(Context paramContext) {
        super(paramContext);
        init();
    }

    public FollowButton(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public FollowButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private boolean isValidClick(float x, float y) {
        return x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight()
                && (animator == null || !animator.isRunning());
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return isValidClick(event.getX(), event.getY());
            case MotionEvent.ACTION_UP:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                centerX = event.getX();
                centerY = event.getY();
                revealRadius = 0;
                followTv.setVisibility(View.VISIBLE);
                unFollowTv.setVisibility(View.VISIBLE);
                setFollowed(!isFollowed, true);
                callOnClick();
                return true;
        }
        return false;
    }

    private void init() {
        unFollowTv = new TextView(getContext());
        unFollowTv.setText(R.string.un_follow);
        unFollowTv.setGravity(17);
        unFollowTv.setSingleLine();
        unFollowTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        unFollowTv.setTextColor(Color.WHITE);
        addView(this.unFollowTv);
        followTv = new TextView(getContext());
        followTv.setText(R.string.follow);
        followTv.setGravity(17);
        followTv.setSingleLine();
        followTv.setBackgroundColor(getResources().getColor(R.color.secondaryBG));
        followTv.setTextColor(getResources().getColor(R.color.colorTextBlack_4));
        addView(this.followTv);

        setFollowed(false, false);
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
        setFollowed(isFollowed, false);
    }

    public boolean getFollowed() {
        return isFollowed;
    }

    protected void setFollowed(boolean isFollowed, boolean needAnimate) {
        this.isFollowed = isFollowed;
        if (isFollowed) {
            unFollowTv.setVisibility(View.VISIBLE);
            followTv.setVisibility(View.VISIBLE);
            followTv.bringToFront();
        } else {
            unFollowTv.setVisibility(View.VISIBLE);
            followTv.setVisibility(View.VISIBLE);
            unFollowTv.bringToFront();
        }
        if (needAnimate) {
            animator = ValueAnimator.ofFloat(0.0F, (float) Math.hypot(getMeasuredWidth(), getMeasuredHeight()));
            animator.setDuration(500L);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    revealRadius = (Float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }

    private boolean drawBackground(View paramView) {
        if (isFollowed && paramView == unFollowTv) {
            return true;
        } else if (!isFollowed && paramView == followTv) {
            return true;
        }
        return false;
    }

    protected boolean drawChild(Canvas canvas, View paramView, long paramLong) {
        if (drawBackground(paramView)) {
            return super.drawChild(canvas, paramView, paramLong);
        }
        int i = canvas.save();
        path.reset();
        path.addCircle(centerX, centerY, revealRadius, Path.Direction.CW);
        canvas.clipPath(this.path);
        boolean bool2 = super.drawChild(canvas, paramView, paramLong);
        canvas.restoreToCount(i);
        return bool2;
    }

}
