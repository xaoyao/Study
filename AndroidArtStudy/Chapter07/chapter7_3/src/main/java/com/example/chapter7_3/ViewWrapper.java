package com.example.chapter7_3;

import android.view.View;

/**
 * Created by liu on 2016/12/9 0009.
 */

public class ViewWrapper {
    private View mTarget;

    public ViewWrapper(View target) {
        this.mTarget = target;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }

}
