package com.example.demo05

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log

/**
 * Created by liu on 2016/11/27 0027.
 */
class MessengerService : Service() {
    companion object {
        val TAG = "MessengerService"

        val MSG_FROM_CLIENT = 0
        val MSG_FROM_SERVICE = 1

    }

    private class MessengerHandler : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_FROM_CLIENT -> {
                    Log.i(TAG, "receive msg from Client:" + msg.data.getString("msg"))
                    val client=msg.replyTo
                    val relpyNessage=Message.obtain(null, MSG_FROM_SERVICE)
                    val bundle=Bundle()
                    bundle.putString("reply","嗯，你的消息我已收到，稍后会回复你。")
                    relpyNessage.data=bundle

                    try {
                        client.send(relpyNessage)
                    } catch(e: Exception) {
                        e.printStackTrace()
                    }

                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private val mMessenger = Messenger(MessengerHandler())

    override fun onBind(p0: Intent?): IBinder? {
        return mMessenger.binder
    }
}