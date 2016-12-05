package com.example.liu.chapter04

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by liu on 2016/12/5 0005.
 */
class KTCircleView : View {
    private val mDefaultWidth = 200
    private val mDefaultHeight = 200

    private var mColor: Int = Color.RED

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.KTCircleView)
        mColor = a.getColor(R.styleable.KTCircleView_cl_color, Color.RED)
        a.recycle()
        init()
    }

    private fun init() {
        mPaint.color = mColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMeasureSpecMode == MeasureSpec.AT_MOST &&
                heightMeasureSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, mDefaultHeight)
        } else if (heightMeasureSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSpecSize, mDefaultHeight)
        } else if (widthMeasureSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, heightMeasureSpecSize)
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var w = width - paddingLeft - paddingRight
        var h = height - paddingTop - paddingBottom
        var r = Math.min(w / 2, h / 2)
        canvas.drawCircle((paddingLeft + w / 2).toFloat(), (paddingTop + h / 2).toFloat(), r.toFloat(), mPaint)

    }

}