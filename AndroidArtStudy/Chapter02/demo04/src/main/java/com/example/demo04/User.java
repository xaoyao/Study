package com.example.demo04;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by liu on 2016/11/27 0027.
 */

public class User implements Parcelable ,Serializable{
    public int userId;
    public String userName;
    public boolean isMale;

    public User(int userId, String userName, boolean isFamel) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isFamel;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
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
        parcel.writeInt(isMale ? 1 : 0);
    }

    @Override
    public String toString() {
        return "User " + userId + " " + userName;
    }
}
