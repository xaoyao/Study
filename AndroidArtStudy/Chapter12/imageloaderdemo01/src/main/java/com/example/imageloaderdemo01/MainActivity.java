package com.example.imageloaderdemo01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.imageloader.ImageLoader;

public class MainActivity extends AppCompatActivity {
    private ImageLoader mImageLoader;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageLoader = ImageLoader.instance(this);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageLoader.bindBitmap("http://f.hiphotos.baidu.com/image/pic/item/00e93901213fb80e0ee553d034d12f2eb9389484.jpg"
                , mImageView);
    }
}
