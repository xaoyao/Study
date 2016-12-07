package com.example.chapter5demo

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.NotificationCompat
import android.util.Log
import android.widget.Button
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = "MainActivity"
    }

    private val mBtnDefault by lazy { findViewById(R.id.btn_default) as Button }
    private val mBtnLargeText by lazy { findViewById(R.id.btn_large_text) as Button }
    private val mBtnLargeImage by lazy { findViewById(R.id.btn_large_image) as Button }
    private val mBtnProgress by lazy { findViewById(R.id.btn_progress) as Button }

    private val mNotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButton()

    }

    private fun initButton() {

        mBtnDefault.setOnClickListener {
            Log.d(TAG, "普通通知")
            val builder = NotificationCompat.Builder(this@MainActivity)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setContentTitle("通知标题")
                    .setContentText("通知内容")
                    .setAutoCancel(true)
                    .setNumber(1)
                    .setDefaults(Notification.DEFAULT_ALL)
            val notification = builder.build()
            mNotificationManager.notify(1, notification)

        }

        mBtnLargeText.setOnClickListener {
            Log.d(TAG, "大文本模式")
            val builder = NotificationCompat.Builder(this@MainActivity)
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)

            val bigTextStyle = android.support.v4.app.NotificationCompat.BigTextStyle()
            bigTextStyle.bigText("大文本:在使用NotificationManager.notify()" +
                    "发送通知的时候，需要传递一个标识符，用于唯一标识这个通知。" +
                    "对于有些场景，并不是无限的添加新的通知，有时候需要更新原有通知的信息，" +
                    "这个时候可以重写构建Notification，而使用与之前通知相同标识符来发送通知，" +
                    "这个时候旧的通知就被被新的通知所取代，起到更新通知的效果。")
            bigTextStyle.setBigContentTitle("大标题")
            builder.setStyle(bigTextStyle)
            mNotificationManager.notify(1, builder.build())
        }

        mBtnLargeImage.setOnClickListener {
            Log.d(TAG, "大图模式")
            val builder = NotificationCompat.Builder(this@MainActivity)
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("通知内容")
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
            val bigPictureStyle = android.support.v4.app.NotificationCompat.BigPictureStyle()
            bigPictureStyle.setBigContentTitle("大图片")
            bigPictureStyle.setSummaryText("1111111111")
            bigPictureStyle.bigPicture(BitmapFactory.decodeResource(resources, R.drawable.tt))
            builder.setStyle(bigPictureStyle)
            mNotificationManager.notify(1, builder.build())
        }

        mBtnProgress.setOnClickListener {
            Log.d(TAG, "进度模式")
            val builder = NotificationCompat.Builder(this@MainActivity)
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setShowWhen(false)
                    .setColor(Color.parseColor("#8CC04F"))
            thread {
                for (i in 0..100) {
                    builder.setContentText("下载中 $i%")
                    builder.setProgress(100, i, false)
                    mNotificationManager.notify(2, builder.build())
                    SystemClock.sleep((i * 10).toLong())
                }
                builder.setContentText("下载成功").setProgress(0, 0, false)
                mNotificationManager.notify(2, builder.build())
                mNotificationManager.cancel(2)
            }

        }


    }

}
