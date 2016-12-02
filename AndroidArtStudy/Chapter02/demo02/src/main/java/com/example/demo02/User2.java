package com.example.demo02;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liu on 2016/11/24 0024.
 */

public class User2 implements Parcelable {
    public int userId;
    public String userName;
    public boolean isMale;

    public User2(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    protected User2(Parcel in) {
        userId=in.readInt();
        userName=in.readString();
        isMale=in.readInt()==1;

    }

    public static final Creator<User2> CREATOR = new Creator<User2>() {
        @Override
        public User2 createFromParcel(Parcel in) {
            return new User2(in);
        }

        @Override
        public User2[] newArray(int size) {
            return new User2[size];
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
}
