package com.example.android.apis.mydemo.vectordrawable;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.apis.R;

/**
 * 贝塞尔曲线实现加载loading效果
 */
public class BezierLoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_loading);
        Toast.makeText(this, "点击显示添加购物车效果", Toast.LENGTH_SHORT).show();
    }
}
