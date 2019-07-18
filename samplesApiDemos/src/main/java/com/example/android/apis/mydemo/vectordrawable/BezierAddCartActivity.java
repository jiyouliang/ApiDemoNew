package com.example.android.apis.mydemo.vectordrawable;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.apis.R;

public class BezierAddCartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_add_cart);
        Toast.makeText(this, "点击显示添加购物车效果", Toast.LENGTH_SHORT).show();
    }
}
