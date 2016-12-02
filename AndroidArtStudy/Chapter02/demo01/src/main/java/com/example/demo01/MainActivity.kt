package com.example.demo01

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object{
        val TAG="MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UserManager.sUserId++
        Log.d(TAG,"sUserId="+UserManager.sUserId)

        startActivity(Intent(this,SecondActivity::class.java))

    }
}
