package com.example.demo04

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object {
        val CHAPTER2_PATH = Environment.getExternalStorageDirectory().path + "/singwhatiwanna/chapter_2/"
        val CACHE_FILE_PATH = CHAPTER2_PATH + "usercache"

        val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        } else {
            persistToFile()
        }


    }

    private fun persistToFile() {

        thread {
            Log.d(TAG, Environment.isExternalStorageRemovable().toString())

            val user = User(1, "hello world", false)
            val dir = File(CHAPTER2_PATH)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val cacheFile = File(CACHE_FILE_PATH)

            try {
                val out = ObjectOutputStream(FileOutputStream(cacheFile))
                try {
                    out.writeObject(user)
                    Log.d(TAG, "persist user:" + user)
                } finally {
                    out.close()
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }

            SystemClock.sleep(1000)
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    persistToFile()
                }
            }
        }
    }
}
