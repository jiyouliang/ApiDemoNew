package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.apis.R;

/**
 * 用于控制显示手势滑动，视频播放状态:语音、视频进度、亮度，
 */
public class VideoGestureStateView extends RelativeLayout {
    private static final String TAG = "VideoGestureStateView";
    private ImageView mIvBrightness;
    private ImageView mIvVolume;
    private TextView mTvTime;
    private ProgressBar mProgressBar;
    private LinearLayout mProgressContainer;

    public VideoGestureStateView(Context context) {
        this(context, null, 0);
    }

    public VideoGestureStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoGestureStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.video_gesture_view_layout, this, true);
        initView();
    }

    private void initView() {
        mProgressContainer = findViewById(R.id.progress_container);
        mProgressBar = findViewById(R.id.progressBar);
        mTvTime = findViewById(R.id.tv_time);
        mIvVolume = findViewById(R.id.iv_volume);
        // 亮度图标
        mIvBrightness = findViewById(R.id.iv_brightness);
    }


    public void setVideoMaxProgress(int max) {
        if(mProgressBar != null){
            mProgressBar.setMax(max);
        }
    }

    public int getVideoMaxProgress(){
        if(mProgressBar != null){
            return mProgressBar.getMax();
        }
        return 0;
    }

    public int getVideoProgress(){
        if(mProgressBar != null){
            return mProgressBar.getProgress();
        }
        return 0;
    }
    /**
     * 设置视频进度
     */
    public void setVideoProgress(int progress) {
        if(progress < 0){
            return;
        }
        hideBrightnessView();
        hideVolumeView();
        showProgressBar();
        if (mProgressBar.getVisibility() != View.VISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        mProgressBar.setProgress(progress);
    }

    /**
     * 显示亮度View
     * @param brightness 亮度百分百
     */
    public void setBrightness(int brightness){
        if(brightness < 0){
            return;
        }
        hideVolumeView();
        hideProgressBar();
        showBrightnessView();
        mTvTime.setText(String.format("%s%s", brightness, "%"));
    }

    /**
     * 显示音量控件
     * @param volume 音量百分百
     */
    public void setVolume(int volume){
        if(volume < 0){
            return;
        }
        hideProgressBar();
        hideBrightnessView();
        showVolumeView();
        mTvTime.setText(String.format("%s%s", volume, "%"));
    }

    /**
     * 设置时间
     * @param currentTime 当前时间秒钟
     * @param duration 总时长秒钟
     */
    public void setTimeText(String currentTime, String duration){
        if(mTvTime != null){
            mTvTime.setText(String.format("%s/%s", currentTime, duration));
        }
    }

    /**
     * 清空TextView文字信息
     */
    public void clearText(){
        if(mTvTime != null){
            mTvTime.setText("");
        }
    }

    /**
     * 显示文字百分百
     * @param percent
     */
    public void setTextPercent(int percent){
        if(mTvTime != null){
            mTvTime.setText(String.format("%s%s", percent, "%"));
        }
    }

    public void hideBrightnessView() {
        if (mIvBrightness != null && mIvBrightness.getVisibility() != View.GONE) {
            mIvBrightness.setVisibility(View.GONE);
        }
    }

    public void showBrightnessView() {
        if (mIvBrightness != null && mIvBrightness.getVisibility() != View.VISIBLE) {
            mIvBrightness.setVisibility(View.VISIBLE);
        }
    }

    public void hideVolumeView() {
        if (mIvVolume != null && mIvVolume.getVisibility() != View.GONE) {
            mIvVolume.setVisibility(View.GONE);
        }
    }

    public void showVolumeView() {
        if (mIvVolume != null && mIvVolume.getVisibility() != View.VISIBLE) {
            mIvVolume.setVisibility(View.VISIBLE);
        }
    }

    public void showProgressBar(){
        if(mProgressBar != null && mProgressBar.getVisibility() != View.VISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar(){
        if(mProgressBar != null && mProgressBar.getVisibility() != View.GONE){
            mProgressBar.setVisibility(View.GONE);
        }
    }


}
