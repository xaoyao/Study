package com.example.demo01

import android.app.Application
import android.os.Process
import android.util.Log

/**
 * Created by liu on 2016/11/24 0024.
 */
class MyApplication : Application() {
    companion object {
        val TAG = "MyApplication"
    }

    override fun onCreate() {
        super.onCreate()
        val processName = getProcessName(applicationContext, Process.myPid())
        Log.d(TAG, "application start, process name: " + processName)
    }
}