package com.example.chapter5_3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RemoteViews

class MainActivity : AppCompatActivity() {
    private val mRemoteViewsContent by lazy { findViewById(R.id.remote_views_content) as LinearLayout }

    private val mRemoteViewsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val remoteViews = intent.getParcelableExtra<RemoteViews>(RemoteActivity.EXTRA_REMOTE_VIEWS)
            if (remoteViews != null) {
                updateUI(remoteViews)
            }
        }
    }

    private fun updateUI(remoteViews: RemoteViews) {
        val view = remoteViews.apply(this, mRemoteViewsContent)
        mRemoteViewsContent.addView(view)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter(RemoteActivity.REMOTE_ACTION)
        registerReceiver(mRemoteViewsReceiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(mRemoteViewsReceiver)
        super.onDestroy()
    }

    public fun startRemote(v: View) {
        startActivity(Intent(this, RemoteActivity::class.java))
    }
}
