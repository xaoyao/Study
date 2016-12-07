package com.example.chapter5_1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Created by liu on 2016/12/7 0007.
 */
class MyAppWidgetProvider : AppWidgetProvider() {

    companion object {
        val TAG = "MyAppWidgetProvider"
        val CLICK_ACTION = "com.example.chapter5_1.action.CLICK"
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d(TAG, "onReceive : action=" + intent.action)

        if (intent.action.equals(CLICK_ACTION)) {
            Toast.makeText(context, "clicked it", Toast.LENGTH_SHORT).show()


            Thread({
                val srcbBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.test)
                val appWidgetManager = AppWidgetManager.getInstance(context)
                for (i in 0..36) {
                    val degree: Float = ((i * 10) % 360).toFloat()
                    val remoteViews = RemoteViews(context.packageName, R.layout.widget)
                    remoteViews.setImageViewBitmap(R.id.widget_imageView,
                            rotateBitmap(context, srcbBitmap, degree))

                    val intentClick = Intent()
                    intentClick.action = CLICK_ACTION
                    val pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0)
                    remoteViews.setOnClickPendingIntent(R.id.widget_imageView, pendingIntent)
                    appWidgetManager.updateAppWidget(ComponentName(
                            context, MyAppWidgetProvider::class.java), remoteViews)
                    SystemClock.sleep(30)
                }

            }).start()

        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i(TAG, "onUpdate")

        val counter = appWidgetIds.size
        Log.i(TAG, "counter=" + counter)
        for (id in appWidgetIds) {
            onWidgetUpdate(context, appWidgetManager, id)
        }
    }

    private fun onWidgetUpdate(context: Context, appWidgetManager: AppWidgetManager, id: Int) {
        Log.i(TAG,"appWidgetId= "+id)
        val remoteViews=RemoteViews(context.packageName,R.layout.widget)

        val intentClick=Intent()
        intentClick.action= CLICK_ACTION
        val pendingIntent=PendingIntent.getBroadcast(context,0,intentClick,0)
        remoteViews.setOnClickPendingIntent(R.id.widget_imageView,pendingIntent)
        appWidgetManager.updateAppWidget(id,remoteViews)
    }

    private fun rotateBitmap(context: Context, srcbBitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.reset()
        matrix.setRotate(degree)
        val tmpBitmap = Bitmap.createBitmap(srcbBitmap, 0, 0,
                srcbBitmap.width, srcbBitmap.height, matrix, true)
        return tmpBitmap
    }

}