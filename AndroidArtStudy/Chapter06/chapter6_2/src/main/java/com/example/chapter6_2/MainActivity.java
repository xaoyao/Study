package com.example.chapter6_2;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private boolean isAlive = true;
    int level = 0;
    private ImageView mLevelImageView;
    private ImageView mTransitionImageView;
    private TransitionDrawable mTransitionDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLevelImageView = (ImageView) findViewById(R.id.lev_image);
        mTransitionImageView = (ImageView) findViewById(R.id.transition_image);

        ImageView image = (ImageView) findViewById(R.id.customDrawable);
        image.setImageDrawable(new CustomDrawable(Color.RED));

        mTransitionDrawable = (TransitionDrawable) mTransitionImageView.getBackground();

        testLevelListDrawable(mLevelImageView);
        testTransitionDrawable();
    }


    private void testLevelListDrawable(final ImageView image) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAlive) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageLevel(level);
                        }
                    });
                    level = (level + 10) > 30 ? 0 : level + 10;

                    SystemClock.sleep(1000);
                }

            }
        }
        ).start();
    }

    private void testTransitionDrawable() {
        new Thread() {
            @Override
            public void run() {
                while (isAlive) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTransitionDrawable.startTransition(1000);
                        }
                    });

                    SystemClock.sleep(1500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTransitionDrawable.reverseTransition(1000);
                        }
                    });
                    SystemClock.sleep(1500);
                }

            }
        }.start();

    }


    @Override
    protected void onPause() {
        isAlive = false;
        super.onPause();
    }
}
