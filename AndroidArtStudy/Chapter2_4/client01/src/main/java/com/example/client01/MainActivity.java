package com.example.client01;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.service.Book;
import com.example.service.IBookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            final IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);

            new Thread(new Runnable() {
                int i = 0;

                @Override
                public void run() {
                    while (true) {
                        SystemClock.sleep(1000);
                        try {
                            bookManager.addBook(new Book(i, "book#" + i));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                }
            }).start();

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

        Intent intent = new Intent("com.example.service.BookManagerService");
        intent.setPackage("com.example.service");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
