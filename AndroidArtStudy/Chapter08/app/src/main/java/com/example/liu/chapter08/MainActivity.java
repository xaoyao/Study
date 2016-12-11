package com.example.liu.chapter08;


import android.content.Intent;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private Button mFloatingButton;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFloatingButton = new Button(this);
        mFloatingButton.setText("button");

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mFloatingButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mLayoutParams.x = rawX;
                        mLayoutParams.y = rawY;
                        mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
                        break;
                }
                return false;
            }
        });


    }

    public void add(View v) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                showButton();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
            showButton();
        }
    }

    private void showButton() {
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mWindowManager.addView(mFloatingButton, mLayoutParams);
    }

    public void remove(View v) {
        mWindowManager.removeView(mFloatingButton);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    showButton();
                }
            }
        }
    }
}
