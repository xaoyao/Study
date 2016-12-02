package com.example.showuserapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.service.IOnUserChangeListener;
import com.example.service.IUserManager;

public class ShowUserActivity extends AppCompatActivity {
    private static final String TAG = "ShowUserActivity";

    private TextView mShowUserSize;
    private IUserManager mUserManager;

    private int mSize=0;

    private IOnUserChangeListener moOnUserChangeListener = new IOnUserChangeListener.Stub() {

        @Override
        public void onUserChanged() throws RemoteException {
            Log.i(TAG, "onUserChanged: ");

            mSize=mUserManager.getUser().size();

        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected: ");
            mUserManager = IUserManager.Stub.asInterface(iBinder);
            try {
                mUserManager.registerOnUserChangeListener(moOnUserChangeListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected: ");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        mShowUserSize = (TextView) findViewById(R.id.show);

        Intent serviceIntent=new Intent("com.example.service.user_service");
        serviceIntent.setPackage("com.example.service");
        bindService(serviceIntent,mConnection,BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mShowUserSize.setText(mSize+"");
    }

    @Override
    protected void onDestroy() {
        if (mUserManager != null) {
            try {
                mUserManager.unregisterOnUserChangeListener(moOnUserChangeListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
