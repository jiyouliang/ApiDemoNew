package com.example.android.apis.mydemo.glide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.apis.R;

public class GlideLoadGifActivity extends Activity {

    private ImageView mIvGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_load_gif);
        initView();
    }

    private void initView() {
        mIvGif = (ImageView) findViewById(R.id.iv_gif);
        Glide.with(this).load(R.drawable.amap_loading).into(mIvGif);
    }
}
