package com.example.adduserapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.service.User;
import com.example.service.IUserManager;

public class AddUserActivity extends AppCompatActivity {
    private static final String TAG = "AddUserActivity";

    private EditText mInputUserId;
    private EditText mInputUserName;
    private Button mAddUser;

    private IUserManager mUserManager;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected:");
            mUserManager = IUserManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected:");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mInputUserId = (EditText) findViewById(R.id.input_userId);
        mInputUserName = (EditText) findViewById(R.id.input_user_name);
        mAddUser = (Button) findViewById(R.id.add_user);

        Intent serviceIntent = new Intent("com.example.service.user_service");
        serviceIntent.setPackage("com.example.service");
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);


        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = Integer.parseInt(mInputUserId.getText().toString().trim());
                String userName = mInputUserName.getText().toString().trim();
                User u = new User(userId, userName);
                if (mUserManager != null) {
                    try {
                        mUserManager.addUser(u);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Toast.makeText(AddUserActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "onClick: 添加成功");
                    Toast.makeText(AddUserActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
