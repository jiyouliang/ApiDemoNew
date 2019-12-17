package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.apis.R;

/**
 * 演示滑动计算进度：用于视频播放滑动快进/快退功能
 */
public class TouchProgressActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_progress);
    }
}
