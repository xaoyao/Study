package com.example.demo05

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MessengerActivity : AppCompatActivity() {
    companion object {
        val TAG = "MessengerActivity"

    }
    private var mService: Messenger? = null;

    private val mGetReplayMessenger=Messenger(MessengerHandler())

    private class MessengerHandler : Handler(){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                MessengerService.MSG_FROM_SERVICE ->{
                    Log.i(TAG,"receivie msg from Service: "+msg.data.getString("reply"))
                }
                else -> super.handleMessage(msg)
            }
        }
    }


    private val mConnection = object : ServiceConnection {

        override fun onServiceDisconnected(p0: ComponentName?) {

        }

        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            mService= Messenger(service)
            val msg=Message.obtain(null,MessengerService.MSG_FROM_CLIENT)
            val data=Bundle()
            data.putString("msg","hello, this is client.")
            msg.data=data

            msg.replyTo=mGetReplayMessenger

            try {
                mService?.send(msg)
            } catch(e: RemoteException) {
                e.printStackTrace()
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        val intent = Intent(this, MessengerService::class.java)
        bindService(intent,mConnection,Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(mConnection)
        super.onDestroy()
    }
}
