package com.example.android.apis.mydemo.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;

import com.example.android.apis.R;

/**
 * 自定义圆环进度条
 */
public class CircleProgressBarActivity extends Activity implements ValueAnimator.AnimatorUpdateListener {

    private CircleProgressBar mCircleProgressBar;
    private ValueAnimator mAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress_bar);
        initView();
    }

    private void initView() {
        mCircleProgressBar = (CircleProgressBar) findViewById(R.id.circleProgressBar);
        //mCircleProgressBar.setMax(100);

        // 动画模拟进度条更新
        mAnim = ValueAnimator.ofFloat(0, 1);
        mAnim.setDuration(10000);
        mAnim.addUpdateListener(this);
//        mCircleProgressBar.setProgress(50);
        mAnim.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        float current = mCircleProgressBar.getMax() * 1.0F * value;
        mCircleProgressBar.setProgress(Math.round(current));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAnim != null && mAnim.isRunning()){
            mAnim.cancel();
            mAnim = null;
        }
    }
}
