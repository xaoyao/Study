// IOnBookArrivedListener.aidl
package com.example.service;
import com.example.service.Book;

// Declare any non-default types here with import statements

interface IOnBookArrivedListener {

    void onBookArrived(in Book book);

}
