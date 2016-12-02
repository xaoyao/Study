package com.example.demo04

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import kotlin.concurrent.thread

class SecondActivity : AppCompatActivity() {
    companion object {
        val TAG = "SecondActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    override fun onResume() {
        super.onResume()
        recoverFromFile()
    }

    private fun recoverFromFile() {
        thread {
            val cacheFile = File(MainActivity.CACHE_FILE_PATH)
            if (cacheFile.exists()) {
                try {
                    val ins = ObjectInputStream(FileInputStream(cacheFile))

                    try {
                        val user = ins.readObject() as User
                        Log.d(TAG, "recover user: " + user)
                    } finally {
                        ins.close()
                    }
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }

        }.start()
    }
}
