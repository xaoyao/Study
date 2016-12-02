package com.example.demo07;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";

    private AtomicBoolean mIsServiceDestroyed=new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList=new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList=new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(!mListenerList.contains(listener)){
//                mListenerList.add(listener);
//            }else {
//                Log.d(TAG, "already exists.");
//            }
//            Log.d(TAG, "registerListener, size: "+mListenerList.size());
            mListenerList.register(listener);

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(mListenerList.contains(listener)){
//                mListenerList.remove(listener);
//                Log.d(TAG, "unregister Listener succeed.");
//            }else{
//                Log.d(TAG, "not found, can not unregister");
//            }
//            Log.d(TAG, "unregisterListener, current size: "+mListenerList.size());
            mListenerList.unregister(listener);

        }
    };

    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Ios"));

        new Thread(new ServiceWorker()).start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
//        mBookList.add(book);
//        Log.d(TAG, "onNewBookArrived: notify listener: "+mListenerList.size());
//        for (IOnNewBookArrivedListener listener : mListenerList) {
//            Log.d(TAG, "onNewBookArrived: notify listener: "+listener);
//            listener.onNewBookArrived(book);
//        }

        mBookList.add(book);
        final int N=mListenerList.beginBroadcast();
        for(int i=0;i<N;i++){
            IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
            if(l!=null){
                l.onNewBookArrived(book);
            }

        }
        mListenerList.finishBroadcast();


    }

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()){
                SystemClock.sleep(5000);
                int bookId=mBookList.size()+1;
                Book newBook=new Book(bookId,"new book#"+bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
