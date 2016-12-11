package com.example.chapter7_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view);

        init();
    }

    private void init() {
        for (int i = 0; i < 10; i++) {
            mList.add("item " + i);
        }

        mArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mArrayAdapter);


    }

    public void click(View v) {
        mList.add("item " + mList.size());
        mArrayAdapter.notifyDataSetChanged();
    }

    public void toAnother(View view) {
        startActivity(new Intent(this, Main2Activity.class));
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
    }
}
