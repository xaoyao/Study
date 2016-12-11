package com.example.liu.chapter10;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    private Handler mThread1Handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBooleanThreadLocal.set(true);
        Log.d(TAG, "[Thread#main]mBooleanThreadLocal=" + mBooleanThreadLocal.get());

        mThread1.start();
        mThread2.start();

    }

    private Thread mThread1 = new Thread("Thread#1") {

        @Override
        public void run() {
            Looper.prepare();
            mThread1Handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            Log.d(TAG, "[Thread#1] handleMessage: " + msg.obj);
                            break;
                        case 2:
                            mBooleanThreadLocal.set(false);
                            Log.d(TAG, "[Thread#1]mBooleanThreadLocal=" + mBooleanThreadLocal.get());
                            break;
                        case 3:
                            Log.d(TAG, "[Thread#1]: looper quit");
                            getLooper().quit();
                            break;
                    }
                }
            };
            Looper.loop();
            Log.d(TAG, "[Thread#1]: thread quit");

        }
    };
    private Thread mThread2 = new Thread("Thread#2") {
        @Override
        public void run() {
            Log.d(TAG, "[Thread#2]mBooleanThreadLocal=" + mBooleanThreadLocal.get());

            while (mThread1Handler == null) {

            }
            Log.d(TAG, "run: [Thread#2] send message");
            Message msg = mThread1Handler.obtainMessage();
            msg.what = 1;
            msg.obj = "from Thread#2 msg";
            mThread1Handler.sendMessage(msg);
            mThread1Handler.sendEmptyMessage(2);
            mThread1Handler.sendEmptyMessage(3);

            Log.d(TAG, "[Thread#2] thread quit");

        }
    };
}
