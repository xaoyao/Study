package com.example.demo06;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    public static final int MSG_SERVICE = 0;
    public static final int MSG_CLIENT = 1;


    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CLIENT:
                    String str = msg.getData().getString("msg");
                    Log.d(TAG, str);
                    Messenger replyTo = msg.replyTo;
                    Message replyMsg = Message.obtain(null, MSG_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "已收到: " + str);
                    replyMsg.setData(bundle);
                    try {
                        replyTo.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private Messenger messenger = new Messenger(new MessengerHandler());


    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
