package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.android.apis.R;

public class NumberKeyboardActivity extends Activity implements NumberKeyboardView.OnKeyboardClickListener {

    private static final String TAG = "NumberKeyboardActivity";

    private NumberKeyboardView mNumKeyboardView;
    private KeyboardInputView mKeyboardInputView;
    private StringBuilder sb;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_keyboard);
        initView();
    }

    private void initView() {
        mNumKeyboardView = (NumberKeyboardView) findViewById(R.id.num_keyboard_view);

        mNumKeyboardView.setOnKeyboardClickListener(this);
        mKeyboardInputView = (KeyboardInputView) findViewById(R.id.keyboard_input_view);


        mKeyboardInputView.setInputMaxSize(4);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        mKeyboardInputView.setOnTextChangedListener(new KeyboardInputView.OnTextChangedListener() {
            @Override
            public void beforeTextChanged(String text) {

            }

            @Override
            public void afterTextChanged(String text) {

            }
        });
    }

    @Override
    public void onNumberKeyClick(String num) {
        if (TextUtils.isEmpty(num)) {
            return;
        }
        boolean result = mKeyboardInputView.setText(num);
       /* if (!result) {
            mStringBuilder.deleteCharAt(mStringBuilder.length() - 1);
        }*/

        mTvTitle.setText(mKeyboardInputView.getText());
    }

    @Override
    public void onDeleteClick() {
        mKeyboardInputView.deleteText();
        /*if (mStringBuilder.length() > 0) {
            //删除文字
            mStringBuilder.deleteCharAt(mStringBuilder.length() - 1);
        }*/
        mTvTitle.setText(mKeyboardInputView.getText());
    }
}
