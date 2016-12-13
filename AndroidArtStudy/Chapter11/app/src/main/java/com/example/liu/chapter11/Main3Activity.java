package com.example.liu.chapter11;

import android.app.IntentService;
import android.content.Intent;
import android.nfc.Tag;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent = LocalIntentService.newIntent(this);
        intent.putExtra("test", "Task#1");
        startService(intent);
        intent.putExtra("test", "Task#2");
        startService(intent);
        intent.putExtra("test", "Task#3");
        startService(intent);
    }


}
