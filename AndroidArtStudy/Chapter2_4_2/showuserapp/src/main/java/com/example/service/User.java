package com.example.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liu on 2016/12/2 0002.
 */

public class User implements Parcelable {
    public int userId;
    public String userName;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }



    protected User(Parcel in) {
        userId=in.readInt();
        userName=in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(userName);
    }
}
