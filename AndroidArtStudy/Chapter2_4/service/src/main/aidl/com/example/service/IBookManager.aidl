// IBookManager.aidl
package com.example.service;
import com.example.service.Book;
import com.example.service.IOnBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);

    void registerOnBookArrivedListener(IOnBookArrivedListener listener);
    void unregisterOnBookArrivedListener(IOnBookArrivedListener listener);


}
