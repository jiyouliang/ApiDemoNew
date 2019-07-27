package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.apis.R;

public class CountDownActivity extends Activity implements View.OnClickListener {

    private TextView mTv;
    private Button mBtnStart;
    private Button mBtnTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        initView();
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv_countdown);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnTop = (Button) findViewById(R.id.btn_top);

        mBtnStart.setOnClickListener(this);
        mBtnTop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startCountdown();
                break;
            case R.id.btn_top:
                stopCountdown();
                break;
        }
    }

    private void startCountdown() {
        mTimer.start();

    }

    private void stopCountdown() {
        mTimer.cancel();
    }

    private CountDownThreadTimer mTimer = new CountDownThreadTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long mills) {
            Log.d("CountDownThreadTimer", String.valueOf(mills /1000));
            mTv.setText(String.format("倒计时%s秒", mills / 1000));
        }

        @Override
        public void onFinish() {
            mTv.setText("倒计时结束");
        }
    };
}
