package com.example.demo07;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED=1;

    private IBookManager mRemoteBookManage;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "receive new book: "+msg.obj);
                    break;
            }

        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                mRemoteBookManage=bookManager;

                List<Book> list = bookManager.getBookList();
                Log.i(TAG, "query book list,list type: " + list.getClass().getCanonicalName());
                Log.i(TAG, "query book list: " + list.toString());

                Book newBook = new Book(3, "Android 开发艺术探索");
                bookManager.addBook(newBook);
                Log.i(TAG, "add book: " + newBook);
                List<Book> newList = bookManager.getBookList();
                Log.i(TAG, "query book list: " + newList.toString());

                bookManager.registerListener(mOnNewBookArrivedListener);



            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteBookManage=null;
            Log.e(TAG, "binder died");

        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener=new IOnNewBookArrivedListener.Stub(){

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);

        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if(mRemoteBookManage!=null&&mRemoteBookManage.asBinder().isBinderAlive()){
            Log.i(TAG, "unregister lostener: "+mOnNewBookArrivedListener);
            try {
                mRemoteBookManage.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
