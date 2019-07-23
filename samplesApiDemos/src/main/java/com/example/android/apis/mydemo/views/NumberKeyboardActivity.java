package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.apis.R;

public class NumberKeyboardActivity extends Activity implements NumberKeyboardView.OnKeyboardClickListener {

    private NumberKeyboardView mNumKeyboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_keyboard);
        initView();
    }

    private void initView() {
        mNumKeyboardView = (NumberKeyboardView) findViewById(R.id.num_keyboard_view);

        mNumKeyboardView.setOnKeyboardClickListener(this);
    }

    @Override
    public void onNumberKeyClick(String num) {
        Toast.makeText(this, ""+num, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick() {
        Toast.makeText(this, "点击删除", Toast.LENGTH_SHORT).show();
    }
}
