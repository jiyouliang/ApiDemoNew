package com.example.android.apis.mydemo.keyboard;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.android.apis.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

/**
 * 软键盘状态监控
 * 目前最靠谱的解决方案：https://github.com/yshrsmz/KeyboardVisibilityEvent
 * 源码也不复杂，有空可以看看
 */
public class KeyboardChangedActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText mEtContent;
    private View mBtnSend;
    private ImageView mIvMore;
    private boolean fistEnter = true;
    private static final String TAG = KeyboardChangedActivity.class.getSimpleName();
    private ConstraintLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_changed);
        initView();
        registerKeyboardListener();
    }

    private void initView() {
        mEtContent = (EditText) findViewById(R.id.et_content);
        mBtnSend = findViewById(R.id.btn_send);
        mIvMore = (ImageView) findViewById(R.id.iv_more);

        mBtnSend.setOnClickListener(this);

        // 监听软键盘状态
        mEtContent.setOnFocusChangeListener(this);
        mContainer = (ConstraintLayout) findViewById(R.id.container);
        mContainer.setOnClickListener(this);

        ViewTreeObserver observer = mEtContent.getViewTreeObserver();

    }

    private void registerKeyboardListener() {
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                mBtnSend.setVisibility(isOpen ? View.VISIBLE : View.GONE);
                mIvMore.setVisibility(isOpen ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:

                break;
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && fistEnter) {
            // 首次进入屏蔽自动弹出软键盘
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            fistEnter = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Unregistrar unregistrar = KeyboardVisibilityEvent.registerEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {

            }
        });
        unregistrar.unregister();

    }
}
