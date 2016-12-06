package com.example.liu.chapter04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View v){
        Toast.makeText(this,"button1 click",Toast.LENGTH_SHORT).show();
    }

    public void click2(View v){
        Toast.makeText(this,"button2 click",Toast.LENGTH_SHORT).show();
    }
}
