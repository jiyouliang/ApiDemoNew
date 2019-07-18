package com.example.android.apis.mydemo.vectordrawable;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.apis.R;

public class BezierWaveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_wave);
        Toast.makeText(this, "点击显示波浪动画", Toast.LENGTH_SHORT).show();
    }
}
