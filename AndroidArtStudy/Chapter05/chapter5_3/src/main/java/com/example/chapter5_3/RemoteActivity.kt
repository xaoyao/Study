package com.example.chapter5_3

import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews

class RemoteActivity : AppCompatActivity() {

    companion object {
        val REMOTE_ACTION = "com.example.chapter5_3.REMOTE_ACTION"
        val EXTRA_REMOTE_VIEWS = "extra_views"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)

        val remoteViews = RemoteViews(packageName, R.layout.notification)
        remoteViews.setImageViewResource(R.id.R_id_remote_image, R.drawable.test)
        val pendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, TargetActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.R_id_remote_image, pendingIntent)

        val intent = Intent(REMOTE_ACTION)
        intent.putExtra(EXTRA_REMOTE_VIEWS, remoteViews)
        sendBroadcast(intent)
    }
}
