package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.apis.R;

public class NumberKeyboardViewContainerActivity extends Activity {

    private TextView mTvTitle;
    private KeyboardContainerView mKeyContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_keyboard_view_container);
        initView();
        setListener();
    }


    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mKeyContainerView = (KeyboardContainerView) findViewById(R.id.key_container_view);
    }
    private void setListener() {
        mKeyContainerView.setOnTextChangedListener(new KeyboardContainerView.OnTextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                mTvTitle.setText("输入内容:"+text);
            }
        });
    }

}
