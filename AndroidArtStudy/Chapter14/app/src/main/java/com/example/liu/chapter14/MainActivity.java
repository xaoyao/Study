package com.example.liu.chapter14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public static void javaStaticMethod(String str) {
        Log.d(TAG, "javaMathod: " + str);
    }

    public void javaMethod(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void javaCallC(View v) {
        TextView tv = (TextView) findViewById(R.id.sample_text);
        String str = stringFromJNI();
        tv.setText(str);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

    }

    public void cCallJava(View v) {
        callJavaStaticMethod();
        callJavaMethod();
    }

    public void gotoKotlin(View v) {
        startActivity(new Intent(this, KotlinActivity.class));
    }

    public native String stringFromJNI();

    public native int max(int a, int b);

    public native void callJavaStaticMethod();

    public native void callJavaMethod();
}
