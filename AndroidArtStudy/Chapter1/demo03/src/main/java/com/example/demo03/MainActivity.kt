package com.example.demo03

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    companion object{
        val TAG="MainActivity"
    }

    val mBtnStart by lazy { findViewById(R.id.btn_start) as Button }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnStart.setOnClickListener{
            val intent=Intent(this@MainActivity,SecondActivity::class.java)
            intent.putExtra("time",System.currentTimeMillis())
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG,"onNewIntent,time= "+intent?.getLongExtra("time",0))
    }

}
