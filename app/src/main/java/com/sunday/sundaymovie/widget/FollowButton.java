package com.sunday.sundaymovie.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sunday.sundaymovie.R;

/**
 * Created by agentchen on 2017/2/20.
 * Email agentchen97@gmail.com
 */

public class FollowButton extends FrameLayout {
    protected boolean isFollowed;
    private String followText = "已收藏";
    private String unFollowText = "收藏";
    private TextView followTv;
    private TextView unFollowTv;
    float actionUpX;
    float actionUpY;
    float revealRadius = 1080;
    private Path path = new Path();
    private ValueAnimator animator;
    private float downY;
    private int touchSlop;
    private Drawable foreTr;
    private Drawable foreBl;

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
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isValidClick(event.getX(), event.getY())) {
                    downY = event.getY();
                    setForeground(foreBl);
                    return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                return Math.abs(event.getY() - downY) < touchSlop &&
                        (x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight());
            case MotionEvent.ACTION_UP:
                if (isValidClick(event.getX(), event.getY())) {
                    setForeground(foreTr);
                    actionUpX = event.getX();
                    actionUpY = event.getY();
                    revealRadius = 0;
                    setFollowed(!isFollowed, true);
                    callOnClick();
                    return true;
                }
                return false;
            case MotionEvent.ACTION_CANCEL:
                setForeground(foreTr);
                return true;
            default:
                return false;
        }
    }

    private void init() {
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        foreTr = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        foreBl = new ColorDrawable(getResources().getColor(R.color.colorTextBlack_4));
        unFollowTv = new TextView(getContext());
        unFollowTv.setText(unFollowText);
        unFollowTv.setGravity(17);
        unFollowTv.setSingleLine();
        unFollowTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        unFollowTv.setTextColor(Color.WHITE);
        addView(this.unFollowTv);
        followTv = new TextView(getContext());
        followTv.setText(followText);
        followTv.setGravity(17);
        followTv.setSingleLine();
        followTv.setBackgroundColor(getResources().getColor(R.color.secondaryBG));
        followTv.setTextColor(getResources().getColor(R.color.colorTextBlack_4));
        addView(this.followTv);
        setFollowed(false, false);
    }

    public void setText(String followText, String unFollowText) {
        this.followText = followText;
        this.unFollowText = unFollowText;
    }

    public boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean isFollowed, boolean needAnimate) {
        this.isFollowed = isFollowed;
        if (isFollowed) {
            followTv.bringToFront();
        } else {
            unFollowTv.bringToFront();
        }
        if (needAnimate) {
            animator = ValueAnimator.ofFloat(0.0F, (float) Math.hypot(getMeasuredWidth(), getMeasuredHeight()));
            animator.setDuration(400L);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
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
        path.addCircle(actionUpX, actionUpY, revealRadius, Path.Direction.CW);
        canvas.clipPath(this.path);
        boolean bool2 = super.drawChild(canvas, paramView, paramLong);
        canvas.restoreToCount(i);
        return bool2;
    }

}
