package com.example.liu.chapter14

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class KotlinActivity : AppCompatActivity() {

    init {
        System.loadLibrary("native-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
    }

    fun kotlinMethod(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    fun kotlinCallC(v: View) {
        Toast.makeText(this, kotlinGetString(), Toast.LENGTH_SHORT).show()
    }

    fun cCallKotlin(v: View) {
        callKotlinMethod()
    }

    external fun kotlinGetString(): String
    external fun callKotlinMethod()

}
