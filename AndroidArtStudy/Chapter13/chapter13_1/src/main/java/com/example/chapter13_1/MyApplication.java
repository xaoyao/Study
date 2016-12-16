package com.example.chapter13_1;

import android.app.Application;

/**
 * Created by liu on 2016/12/15 0015.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
