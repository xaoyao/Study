package com.example.chapter245;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ProviderActivity extends AppCompatActivity {
    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        Uri bookUri = Uri.parse("content://com.example.chapter245.book_provider/book");
        ContentValues values=new ContentValues();
        values.put("_id",6);
        values.put("name","程序艺术");
        getContentResolver().insert(bookUri,values);
        Cursor bookCursor = getContentResolver()
                .query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()){
            int id=bookCursor.getInt(0);
            String name=bookCursor.getString(1);
            Log.d(TAG, "query book: "+id+" "+name);
        }
        bookCursor.close();

    }
}
