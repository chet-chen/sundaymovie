package com.sunday.sundaymovie.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sunday.sundaymovie.R;

/**
 * Created by agentchen on 2017/2/20.
 * Email agentchen97@gmail.com
 */

public class FollowButton extends FrameLayout {
    protected boolean mIsFollowed;
    private String mFollowText = "已收藏";
    private String mUnFollowText = "收藏";
    private TextView mFollowTv;
    private TextView mUnFollowTv;
    float mActionUpX;
    float mActionUpY;
    float mRevealRadius;
    private boolean mIsFirstDraw = true;
    private Path mPath = new Path();
    private ValueAnimator mAnimator;
    private Drawable mTransparent;
    private Drawable mAlphaBlack;

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
                && (mAnimator == null || !mAnimator.isRunning());
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mAnimator == null || !mAnimator.isRunning()) {
                    setForeground(mAlphaBlack);
                    mIsFirstDraw = false;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (!(x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight())) {
                    setForeground(mTransparent);
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (isValidClick(event.getX(), event.getY())) {
                    setForeground(mTransparent);
                    mActionUpX = event.getX();
                    mActionUpY = event.getY();
                    mRevealRadius = 0;
                    setFollowed(!mIsFollowed, true);
                    callOnClick();
                    return true;
                }
                return false;
            case MotionEvent.ACTION_CANCEL:
                setForeground(mTransparent);
                return true;
            default:
                return false;
        }
    }

    private void init() {
        mTransparent = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        mAlphaBlack = new ColorDrawable(getResources().getColor(R.color.colorTextBlack_4));
        mUnFollowTv = new TextView(getContext());
        mUnFollowTv.setText(mUnFollowText);
        mUnFollowTv.setGravity(17);
        mUnFollowTv.setSingleLine();
        mUnFollowTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mUnFollowTv.setTextColor(Color.WHITE);
        addView(this.mUnFollowTv);
        mFollowTv = new TextView(getContext());
        mFollowTv.setText(mFollowText);
        mFollowTv.setGravity(17);
        mFollowTv.setSingleLine();
        mFollowTv.setBackgroundColor(getResources().getColor(R.color.secondaryBG));
        mFollowTv.setTextColor(getResources().getColor(R.color.colorTextBlack_4));
        addView(this.mFollowTv);
        setFollowed(false, false);
    }

    public void setText(String followText, String unFollowText) {
        this.mFollowText = followText;
        this.mUnFollowText = unFollowText;
    }

    public boolean getFollowed() {
        return mIsFollowed;
    }

    public void setFollowed(boolean isFollowed, boolean needAnimate) {
        this.mIsFollowed = isFollowed;
        if (isFollowed) {
            mFollowTv.bringToFront();
        } else {
            mUnFollowTv.bringToFront();
        }
        if (needAnimate) {

            mAnimator = ValueAnimator.ofFloat(0.0F, (float) Math.hypot(getMeasuredWidth(), getMeasuredHeight()));
            mAnimator.setDuration(400L);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRevealRadius = (Float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.start();
        }
    }

    private boolean drawBackground(View paramView) {
        if (mIsFirstDraw) {
            return true;
        }
        if (mIsFollowed && paramView == mUnFollowTv) {
            return true;
        } else if (!mIsFollowed && paramView == mFollowTv) {
            return true;
        }
        return false;
    }

    protected boolean drawChild(Canvas canvas, View paramView, long paramLong) {
        if (drawBackground(paramView)) {
            return super.drawChild(canvas, paramView, paramLong);
        }
        int i = canvas.save();
        mPath.reset();
        mPath.addCircle(mActionUpX, mActionUpY, mRevealRadius, Path.Direction.CW);
        canvas.clipPath(this.mPath);
        boolean bool2 = super.drawChild(canvas, paramView, paramLong);
        canvas.restoreToCount(i);
        return bool2;
    }

}
