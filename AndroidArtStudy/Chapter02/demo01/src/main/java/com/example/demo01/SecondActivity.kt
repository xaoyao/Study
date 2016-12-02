package com.example.demo01

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SecondActivity : AppCompatActivity() {
    companion object{
        val TAG="SecondActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Log.d(TAG,"sUserId="+UserManager.sUserId)

//        startActivity(Intent(this,ThirdActivity::class.java))
    }
}
