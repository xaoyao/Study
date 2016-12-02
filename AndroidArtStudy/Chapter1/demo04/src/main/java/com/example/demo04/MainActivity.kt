package com.example.demo04

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent=Intent()
        intent.setAction(Intent.ACTION_SEND)
        val resolveActivity = intent.resolveActivity(packageManager)

        if (resolveActivity==null){
            Log.d("main","为空")
        }

        if(resolveActivity!=null){
            Log.d("Main","不为空")
        }
  
    }
}
