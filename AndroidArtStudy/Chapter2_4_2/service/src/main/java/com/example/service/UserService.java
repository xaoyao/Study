package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserService extends Service {

    private CopyOnWriteArrayList mUsers = new CopyOnWriteArrayList();

    private RemoteCallbackList<IOnUserChangeListener> mListeners = new RemoteCallbackList<>();


    private IUserManager mUserManager = new IUserManager.Stub() {

        @Override
        public void addUser(User user) throws RemoteException {
            mUsers.add(user);
            bookChanged();
        }

        @Override
        public List<User> getUser() throws RemoteException {
            return mUsers;
        }

        @Override
        public void registerOnUserChangeListener(IOnUserChangeListener listener) throws RemoteException {
            mListeners.register(listener);
        }

        @Override
        public void unregisterOnUserChangeListener(IOnUserChangeListener listener) throws RemoteException {
            mListeners.unregister(listener);
        }
    };

    private void bookChanged() {
        final int n = mListeners.beginBroadcast();
        for (int i = 0; i < n; i++) {
            IOnUserChangeListener listener = mListeners.getBroadcastItem(i);
            if (listener != null) {
                try {
                    listener.onUserChanged();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListeners.finishBroadcast();
    }

    public UserService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mUserManager.asBinder();
    }
}
