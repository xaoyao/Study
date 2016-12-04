package com.example.chapter2_4_6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {

    private boolean mIsServiceDestroy = false;
    private String[] mDefinedMessages = new String[]{
            "你好",
            "你叫什么名字",
            "今天天气不错",
            "给你讲个笑话"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    public TCPServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroy = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                System.err.println("绑定端口失败:8688");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestroy) {
                try {
                    final Socket client = serverSocket.accept();
                    System.out.println("accept");

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void responseClient(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                client.getOutputStream())),true);
        out.println("欢迎来到聊天室");
        while (!mIsServiceDestroy) {
            String str = in.readLine();
            System.out.println("msg from client: " + str);
            if (str == null) {
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            System.out.println("send: " + msg);
        }

        System.out.println("client quit.");
        out.close();
        in.close();
        client.close();
    }
}
