package com.example.chapter2_4_6;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpClientActivity extends AppCompatActivity {
    private static final int MESSAGE_RECEIVER_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private TextView mMessageTextView;
    private EditText mMessageEditText;
    private Button mSendButton;

    private Socket mClientSocket;
    private PrintWriter mPrintWriter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVER_NEW_MSG:
                    mMessageTextView.setText(mMessageTextView.getText() + (String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    mSendButton.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);

        mMessageEditText = (EditText) findViewById(R.id.msg);
        mSendButton = (Button) findViewById(R.id.send);
        mMessageTextView = (TextView) findViewById(R.id.msg_container);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == mSendButton) {
                    final String msg = mMessageEditText.getText().toString().trim();
                    if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
                        new Thread(){
                            @Override
                            public void run() {
                                mPrintWriter.println(msg);
                            }
                        }.start();
                        mMessageEditText.setText("");
                        String time = formatDateTime(System.currentTimeMillis());
                        String showedMsg = "sel " + time + ":" + msg + "\n";
                        mMessageTextView.setText(mMessageTextView.getText() + showedMsg);
                    }
                }
            }
        });


        Intent serviceIntent = new Intent(this, TCPServerService.class);
        startService(serviceIntent);

        new Thread() {
            @Override
            public void run() {
                connectTcpServer();
            }
        }.start();


    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    private void connectTcpServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connect server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                System.out.println("连接失败，重试中...");
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TcpClientActivity.this.isFinishing()) {
                String msg = br.readLine();
                System.out.println("receive: " + msg);
                if (msg != null) {
                    String time = formatDateTime(System.currentTimeMillis());
                    String showedMsg = "server " + time + ":" + msg + "\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVER_NEW_MSG, showedMsg).sendToTarget();
                }
            }
            System.out.println("quit...");
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }
}
