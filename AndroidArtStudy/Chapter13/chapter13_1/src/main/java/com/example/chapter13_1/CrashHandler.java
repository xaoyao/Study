package com.example.chapter13_1;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liu on 2016/12/15 0015.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath()
            + "/CrashTest/log/";
    private static final String FILE_NAME = "crash";
//    private static final String FILE_NAME_SUFFIX = ".crash";
    private static final String FILE_NAME_SUFFIX = ".txt";

    private static CrashHandler mInstance = new CrashHandler();

    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        dumpExceptionToSDCard(e);
        e.printStackTrace();
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable e) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.d(TAG, "SDCard 不存在，跳过保存异常信息");
                return;
            }
        }
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(time);
            dumpPhoneInfo(out);
            out.println();
            e.printStackTrace(out);
            out.close();
        } catch (Exception e1) {
            Log.e(TAG, "保存异常信息失败");
        }


    }

    private void dumpPhoneInfo(PrintWriter out)
            throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName()
                , PackageManager.GET_ACTIVITIES);
        //App 版本
        out.println(String.format("App Version: %s_%s", packageInfo.versionName, packageInfo.versionCode));
        //系统版本
        out.println(String.format("OS Version: %s_%s", Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
        //手机制造商
        out.println(String.format("Vendor: %s", Build.MANUFACTURER));
        //手机型号
        out.println(String.format("Model: %s", Build.MODEL));
        //CPU架构
        out.println(String.format("CPU ABI: %s", Build.CPU_ABI));

    }
}
