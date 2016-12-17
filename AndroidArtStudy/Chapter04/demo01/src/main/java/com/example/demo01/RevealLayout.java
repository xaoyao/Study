package com.example.demo01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by liu on 2016/12/16 0016.
 */

public class RevealLayout extends LinearLayout {

    private static long INVALIDATE_DURATION = 4;

    private View mTargetView;
    private int mCenterX;
    private int mCenterY;
    private int mTargetHeight;
    private int mTargetWidth;
    private boolean mIsPressed;
    private boolean mShouldDoAnimation;
    private int mMaxRadius;
    private int mRevealRadius;
    private int mRevealRadiusGap;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RevealLayout(Context context) {
        super(context);
        init();
    }


    public RevealLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public RevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint.setColor(Color.GRAY);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                View touchTarget = getTouchTarget(this, x, y);
                if (touchTarget == null) {
                    break;
                }
                if (touchTarget.isClickable() && touchTarget.isEnabled()) {
                    mTargetView = touchTarget;
                    initParametersForChild(ev);
                    postInvalidateDelayed(INVALIDATE_DURATION);
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                mIsPressed = false;
                postInvalidateDelayed(INVALIDATE_DURATION);
            }
            break;
            case MotionEvent.ACTION_CANCEL:
                mIsPressed = false;
                postInvalidateDelayed(INVALIDATE_DURATION);
                break;
        }


        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mShouldDoAnimation || mTargetWidth <= 0 || mTargetView == null) {
            return;
        }

        mRevealRadius += mRevealRadiusGap;

        canvas.save();
        canvas.clipRect(mTargetView.getLeft()
                , mTargetView.getTop(), mTargetView.getRight(), mTargetView.getBottom());
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);
        canvas.restore();
        if (mRevealRadius < mMaxRadius) {
            postInvalidateDelayed(INVALIDATE_DURATION);
        } else if (!mIsPressed) {
            mShouldDoAnimation = false;
            postInvalidateDelayed(INVALIDATE_DURATION, mTargetView.getLeft(), mTargetView.getTop()
                    , mTargetView.getRight(), mTargetView.getBottom());
        }

    }

    private void initParametersForChild(MotionEvent ev) {
        mCenterX = (int) ev.getRawX();
        mCenterY = (int) ev.getRawY();

        mTargetWidth = mTargetView.getMeasuredWidth();
        mTargetHeight = mTargetView.getMeasuredHeight();
        mRevealRadius = 0;
        mRevealRadiusGap = Math.min(mTargetWidth, mTargetHeight) / 8;
        mIsPressed = true;
        mShouldDoAnimation = true;

        int[] location = new int[2];
        mTargetView.getLocationOnScreen(location);
        mTargetView.getLocalVisibleRect(new Rect());
        int maxX = Math.max(mCenterX - mTargetView.getLeft(), mTargetView.getRight() - mCenterX);
        int maxY = Math.max(mCenterY - mTargetView.getTop(), mTargetView.getBottom() - mCenterY);
        mMaxRadius = Math.max(maxX, maxY);
    }

    /**
     * 获取被点击的View
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> touchables = view.getTouchables();
        for (View child : touchables) {
            if (isTouchPointView(child, x, y)) {
                target = child;
                break;
            }
        }
        return target;
    }

    /**
     * 判断View是否为当前被点击的View
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchPointView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && x >= left && x <= right && y >= top && y <= bottom) {
            return true;
        }
        return false;
    }

}
