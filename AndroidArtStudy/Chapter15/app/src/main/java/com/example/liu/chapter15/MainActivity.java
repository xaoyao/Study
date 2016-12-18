package com.example.liu.chapter15;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static Context mContext;

    private Button mBtn;
    private ObjectAnimator mAnimator;

    private <T extends View> T $(int resId) {
        return (T) findViewById(resId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //内存泄露
//        mContext = this;
        ((ViewStub) findViewById(R.id.stub_import)).inflate();

        mBtn = $(R.id.btn1);
        mAnimator = ObjectAnimator.ofFloat(mBtn, "rotation", 0, 360).setDuration(2000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }


    @Override
    protected void onRestart() {
        mAnimator.start();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        mAnimator.cancel();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        //防止内存泄露
//        mAnimator.cancel();
        super.onDestroy();
    }
}
