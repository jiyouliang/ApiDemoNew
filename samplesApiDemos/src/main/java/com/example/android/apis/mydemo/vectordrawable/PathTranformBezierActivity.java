package com.example.android.apis.mydemo.vectordrawable;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.apis.R;

public class PathTranformBezierActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_tranform_bezier);
        Toast.makeText(this, "点击屏幕查看路径变换动画", Toast.LENGTH_SHORT).show();
    }
}
