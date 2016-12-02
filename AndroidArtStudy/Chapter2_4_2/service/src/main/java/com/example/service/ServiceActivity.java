package com.example.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = "ServiceActivity";

    private Button mStartService;
    private Button mStopService;

    private Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mStartService = (Button) findViewById(R.id.start_service);
        mStopService = (Button) findViewById(R.id.stop_service);

        mServiceIntent = new Intent(this, UserService.class);

        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(mServiceIntent);
                Log.d(TAG, "onClick: startService");
            }
        });

        mStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(mServiceIntent);
                Log.d(TAG, "onClick: stopService");
            }
        });

    }


}
