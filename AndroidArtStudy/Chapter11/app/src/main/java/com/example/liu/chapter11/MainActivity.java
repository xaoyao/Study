package com.example.liu.chapter11;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private boolean isCancelled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MyTask().execute();
        startActivity(new Intent(this, Main2Activity.class));
    }

    @Override
    protected void onStop() {
        isCancelled = true;
        super.onStop();
    }

    public class MyTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
                SystemClock.sleep(100);
                if (isCancelled) {
                    break;
                }
            }
            return "finish";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "onProgressUpdate: values=" + Arrays.toString(values));
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: " + s);
        }
    }
}
