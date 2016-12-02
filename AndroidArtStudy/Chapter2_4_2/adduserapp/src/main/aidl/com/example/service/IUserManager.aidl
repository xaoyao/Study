// IUserManager.aidl
package com.example.service;

import com.example.service.User;
import com.example.service.IOnUserChangeListener;


// Declare any non-default types here with import statements

interface IUserManager {

    void addUser(in User user);
    List<User> getUser();

    void registerOnUserChangeListener(IOnUserChangeListener listener);
    void unregisterOnUserChangeListener(IOnUserChangeListener listener);

}
