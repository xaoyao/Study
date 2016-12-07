package com.example.chapter5_1

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import android.widget.Button
import android.widget.RemoteViews

class MainActivity : AppCompatActivity() {
    val mBtnDefaultNotification by lazy { findViewById(R.id.default_notification) as Button }
    val mBtnDiyNotification by lazy { findViewById(R.id.diy_notification) as Button }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intiOnClick()

    }

    private fun intiOnClick() {

        mBtnDefaultNotification.setOnClickListener {
            val intent = Intent(this@MainActivity, TargetActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(this@MainActivity)
            val notification = builder
                    .setContentTitle("标题")
                    .setContentText("内容")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.icon)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setColor(Color.parseColor("#8CC04F"))
                    .build()
            manager.notify(1, notification)
        }

        mBtnDiyNotification.setOnClickListener {
            val builder = NotificationCompat.Builder(this@MainActivity)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setAutoCancel(true)

            val remoteViews = RemoteViews(packageName, R.layout.notification)
            remoteViews.setImageViewResource(R.id.image, R.drawable.test)
            val pendingIntent = PendingIntent.getActivity(this@MainActivity, 0,
                    Intent(this@MainActivity, TargetActivity::class.java),
                    PendingIntent.FLAG_CANCEL_CURRENT)
            remoteViews.setOnClickPendingIntent(R.id.image, pendingIntent)

            builder.setContent(remoteViews)
            builder.setCustomBigContentView(remoteViews)
            val notification = builder.build()
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.notify(1, notification)


        }
    }
}
