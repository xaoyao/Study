package com.example.demo01

import android.app.ActivityManager
import android.content.Context

/**
 * Created by liu on 2016/11/24 0024.
 */

fun getProcessName(cxt: Context, pid: Int): String? {

    val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningApps = am.runningAppProcesses
    if (runningApps == null) {
        return null
    } else {
        runningApps
                .filter { it.pid == pid }
                .forEach { return it.processName }

    }
    return null
}