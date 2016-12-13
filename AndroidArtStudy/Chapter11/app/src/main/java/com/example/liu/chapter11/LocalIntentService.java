package com.example.liu.chapter11;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class LocalIntentService extends IntentService {
    private static final String TAG = "LocalIntentService";

    public LocalIntentService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LocalIntentService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String extra = intent.getStringExtra("test");
        Log.d(TAG, "onHandleIntent: " + extra);
        SystemClock.sleep(1000);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "service onDestroy: ");
        super.onDestroy();
    }
}