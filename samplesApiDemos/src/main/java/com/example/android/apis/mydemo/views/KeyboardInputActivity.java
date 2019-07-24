package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.os.Bundle;

import com.example.android.apis.R;

public class KeyboardInputActivity extends Activity {

    private KeyboardInputView mKeyboardInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_input);
        initView();

        mKeyboardInput.setText("12");
    }

    private void initView() {
        mKeyboardInput = (KeyboardInputView) findViewById(R.id.keyboard_input);
    }
}
