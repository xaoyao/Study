package com.example.client02;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.service.Book;
import com.example.service.IBookManager;
import com.example.service.IOnBookArrivedListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IBookManager mBookManager;

    private IOnBookArrivedListener mListener = new IOnBookArrivedListener.Stub() {

        @Override
        public void onBookArrived(Book book) throws RemoteException {
            Log.d(TAG, "onBookArrived: " + book);
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                mBookManager.registerOnBookArrivedListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("com.example.service.BookManagerService");
        intent.setPackage("com.example.service");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        if (mBookManager != null) {
            try {
                mBookManager.unregisterOnBookArrivedListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);

        super.onDestroy();
    }
}
