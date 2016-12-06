package com.example.liu.chapter04

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller

/**
 * Created by liu on 2016/12/6 0006.
 */
class KTHorizontalScrollViewEx : ViewGroup {

    companion object {
        val TAG = "KTHorizontalScrollView"
    }

    private var mLastXIntercept = 0
    private var mLastYIntercept = 0
    private var mLastX = 0
    private var mLastY = 0

    private val mScroller by lazy { Scroller(context) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth = 0
        var measuredHeight = 0

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (widthMeasureSpecMode == MeasureSpec.AT_MOST
                && heightMeasureSpecMode == MeasureSpec.AT_MOST) {
            for (i in 0..childCount - 1) {
                val childView = getChildAt(i)
                measuredWidth += childView.measuredWidth
                if (childView.measuredHeight > measuredHeight) {
                    measuredHeight = childView.measuredHeight
                }
            }
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else if (widthMeasureSpecMode == MeasureSpec.AT_MOST) {
            for (i in 0..childCount) {
                val childView = getChildAt(i)
                measuredWidth += childView.measuredWidth
            }
            setMeasuredDimension(measuredWidth, heightMeasureSpecSize)
        } else if (heightMeasureSpecMode == MeasureSpec.AT_MOST) {
            for (i in 0..childCount) {
                val childView = getChildAt(i)
                if (childView.measuredHeight > measuredHeight) {
                    measuredHeight = childView.measuredHeight
                }
            }
            setMeasuredDimension(widthMeasureSpecSize, measuredHeight)
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        for (i in 0..childCount - 1) {
            val childView = getChildAt(i)
            if (childView.visibility != View.GONE) {
                childView.layout(childLeft, 0, childLeft + childView.measuredWidth,
                        childView.measuredHeight)
                childLeft += childView.measuredWidth
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted = false;
        val x = ev.getX().toInt()
        val y = ev.getY().toInt()

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                intercepted = false
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                    intercepted = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastXIntercept
                val deltaY = y - mLastYIntercept

                intercepted = Math.abs(deltaX) > Math.abs(deltaY)
            }
            MotionEvent.ACTION_UP -> {
                intercepted = false
            }
        }
        mLastXIntercept = x
        mLastYIntercept = y
        mLastX=x
        mLastY=y

        Log.d(TAG, "onIntercepted: intercepted=" + intercepted)
        return intercepted
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastX
                scrollBy(-deltaX, 0)
            }
            MotionEvent.ACTION_UP -> {
                if (scrollX < 0) {
                    smoothScroll(-scrollX, 0)
                }
            }
        }

        mLastX = x
        mLastY = y

        return true
    }

    private fun smoothScroll(dx: Int, dy: Int) {
        mScroller.startScroll(scrollX, 0, dx, 0, 500)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidate()
        }
    }

}