package com.example.demo06;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";
    private Messenger mMessenger;

    private EditText mInput;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMessenger = new Messenger(iBinder);
            msg = Message.obtain(null, MessengerService.MSG_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "连接中...");
            msg.setData(data);
            msg.replyTo = mGetServiceMessenger;

            try {
                mMessenger.send(msg);
                Log.i(TAG, "请求连接...");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "连接关闭");

        }
    };

    private Messenger mGetServiceMessenger = new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessengerService.MSG_SERVICE:
                    Log.i(TAG, msg.getData().getString("reply"));
                    break;
            }
        }
    }

    private Message msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        mInput = (EditText) findViewById(R.id.input);


    }

    public void send(View view) {
        String str = mInput.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("msg", str);
        msg.setData(bundle);
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
