// IOnNewBookArriverListener.aidl
package com.example.demo07;

import com.example.demo07.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book newBook);
}
