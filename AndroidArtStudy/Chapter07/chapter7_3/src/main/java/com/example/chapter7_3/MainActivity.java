package com.example.chapter7_3;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mBtn;
    private ValueAnimator colorAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.btn_test);

    }

    public void translation(View v) {
        ObjectAnimator.ofFloat(mBtn, "translationY", mBtn.getHeight()).start();
    }

    public void bgColor(View v) {
        ValueAnimator colorAnim =  ObjectAnimator.ofInt(mBtn, "backgroundColor", Color.RED, Color.BLUE);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    public void animatorSet(View v) {
//        mBtn.animate().cancel();

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(mBtn, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(mBtn, "rotationY", 0, 360),
                ObjectAnimator.ofFloat(mBtn, "rotation", 0, -90),
                ObjectAnimator.ofFloat(mBtn, "translationX", 0, 90),
                ObjectAnimator.ofFloat(mBtn, "translationY", 0, 90),
                ObjectAnimator.ofFloat(mBtn, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(mBtn, "scaleY", 0, 0.5f),
                ObjectAnimator.ofFloat(mBtn, "alpha", 1, 0.25f, 1)
        );
        set.setDuration(5 * 1000).start();
    }

    public void changeWidth(View v) {
        ViewWrapper wrapper = new ViewWrapper(mBtn);
        ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(5000).start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
