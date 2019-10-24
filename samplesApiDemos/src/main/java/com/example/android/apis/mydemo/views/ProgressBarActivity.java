package com.example.android.apis.mydemo.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.apis.R;

public class ProgressBarActivity extends Activity {
    private static final String TAG = ProgressBarActivity.class.getSimpleName();
    private ProgressBar mProgressBar;
    private EditText mEtNumber;
    private ValueAnimator anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        initView();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEtNumber = (EditText) findViewById(R.id.et_number);
    }

    public void start(View v) {
        if (mEtNumber.getText() == null || TextUtils.isEmpty(mEtNumber.getText().toString())) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        int duration = Integer.valueOf(mEtNumber.getText().toString());
        mProgressBar.setMax(duration * 100);

        //动画
        anim = ValueAnimator.ofInt(0, mProgressBar.getMax());
        anim.setDuration(duration*1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                Log.d(TAG, "value=" + value);
                mProgressBar.setProgress(value);
            }
        });
        anim.start();


    }

    public void stop(View v){
        if(anim == null){
            return;
        }
        anim.cancel();
        mProgressBar.setProgress(0);
    }

}
