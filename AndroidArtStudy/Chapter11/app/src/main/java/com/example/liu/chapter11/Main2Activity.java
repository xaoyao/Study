package com.example.liu.chapter11;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        startActivity(new Intent(this, Main3Activity.class));
    }

    public void start(View v) {
//        new MyTask("AsyncTask#1").execute();
//        new MyTask("AsyncTask#2").execute();
//        new MyTask("AsyncTask#3").execute();

        new MyTask("AsyncTask#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyTask("AsyncTask#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyTask("AsyncTask#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }

    public class MyTask extends AsyncTask<String, Integer, String> {

        private final String mName;

        public MyTask(String name) {
            super();
            mName = name;
        }

        @Override
        protected String doInBackground(String... strings) {
            SystemClock.sleep(3000);
            return mName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d(TAG, s + " onPostExecute: " + format.format(new Date()));
        }
    }
}
