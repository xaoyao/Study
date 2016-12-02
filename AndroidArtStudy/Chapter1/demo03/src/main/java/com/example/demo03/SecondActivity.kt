package com.example.demo03

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById(R.id.btn).setOnClickListener{
            startActivity(Intent(this@SecondActivity,ThirdActivity::class.java))
        }

    }
}
