package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnBookArrivedListener> mListenerList = new RemoteCallbackList<>();


    private IBookManager mBookManager = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            onBookArrived(book);
        }

        @Override
        public void registerOnBookArrivedListener(IOnBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
            Log.d(TAG, "registerOnBookArrivedListener: " + listener);
        }

        @Override
        public void unregisterOnBookArrivedListener(IOnBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
            Log.d(TAG, "unregisterOnBookArrivedListener: " + listener);
        }
    };

    private void onBookArrived(Book newBook) {
        mBookList.add(newBook);
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                try {
                    listener.onBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();

    }


    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBookManager.asBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
