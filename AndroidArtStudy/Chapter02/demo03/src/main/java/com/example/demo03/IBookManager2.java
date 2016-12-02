package com.example.demo03;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by liu on 2016/11/26 0026.
 */

public interface IBookManager2 extends IInterface {
    static final String DESCRIPTOT = "com.example.demo03.IBookManager2";
    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<Book> getBookList() throws RemoteException;

    public void addBook() throws RemoteException;
}
