package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.apis.R;
import com.jiyouliang.log.Logger;

public class TouchProgressView extends LinearLayout {
    private static final String TAG = "TouchProgressView";

    private TextView mTvTime;
    private ProgressBar mProgressBar;
    private LinearLayout mProgressContainer;
    private GestureDetector mGestureDetector;
    private int mVideoWidth;
    private int mVideoProgress;
    private int mDownProgress;
    /**
     * 视频总时长:秒
     */
    private final static int TOTAL_TIME = 40 * 60;

    /**
     * 当前播放到
     */
    private static int mCurrentTime = 0;
    /**
     * 快进/快退阻尼值
     */
    private int offsetX = 20;

    public TouchProgressView(Context context) {
        this(context, null, 0);
    }

    public TouchProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_touch_progress_layout, this, true);
        mProgressContainer = findViewById(R.id.progress_container);
        mProgressBar = findViewById(R.id.progressBar);
        mTvTime = findViewById(R.id.tv_time);
        mProgressBar.setMax(TOTAL_TIME);

        // 手势监听
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            // 双击，可以暂停视频播放
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                showToast("双击，暂停/播放视频");
                return true;
            }


            //如果双击的话，则onSingleTapConfirmed不会执行
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                showToast("显示/隐藏播放控制控件(底部进度条、顶部标题等)");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent downEvent, MotionEvent moveEvent, float distanceX, float distanceY) {
                if(downEvent == null || moveEvent == null){
                    return false;
                }
                if (Math.abs(downEvent.getX() - moveEvent.getX()) <= offsetX) {
                    return false;
                }
                if(mProgressContainer.getVisibility() != View.VISIBLE){
                    mProgressContainer.setVisibility(View.VISIBLE);
                }
                //修改进度
                float dis = moveEvent.getX() - downEvent.getX();
                float percent = dis /mVideoWidth;
                mVideoProgress = (int) (mDownProgress + percent * 100);


                //设置快进/快退时间
                if (mVideoProgress > mProgressBar.getMax()) {
                    mVideoProgress = mProgressBar.getMax();
                }
                if (mVideoProgress < 0) {
                    mVideoProgress = 0;
                }
                mProgressBar.setProgress(mVideoProgress);
                float percentage = ((float) mVideoProgress) / mProgressBar.getMax();
                float currentTime = (TOTAL_TIME * percentage);
                mTvTime.setText(formattedTime((long) currentTime) + "/" + formattedTime(TOTAL_TIME));
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // 重置状态
                resetState(TouchProgressView.this.getWidth(), mProgressBar.getProgress());
                return true;
            }
        });
        mGestureDetector.setIsLongpressEnabled(false);
    }

    private void resetState(int videoWidth, int downProgress) {
        showLog("resetState:videoWidth="+videoWidth+",downProgress="+downProgress);
        mVideoWidth = videoWidth;
        mVideoProgress = 0;
        mDownProgress = downProgress;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.d(TAG, "onTouchEvent:event=" + event);
        if(mGestureDetector != null){
            mGestureDetector.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                removeCallbacks(mHideAction);
                break;
            /*case MotionEvent.ACTION_MOVE:
                break;*/
            case MotionEvent.ACTION_UP:
                postDelayed(mHideAction, 500);
                break;
        }
        return true;
    }

    private final Runnable mHideAction = new Runnable() {
        @Override
        public void run() {
            mProgressContainer.setVisibility(View.GONE);
        }
    };

    private void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showLog(String log){
        Logger.d(TAG, log);
    }

    /**
     * 格式化时间
     * @param second 秒钟
     * @return
     */
    private String formattedTime(long second) {
        String hs, ms, ss, formatTime;

        long h, m, s;
        h = second / 3600;
        m = (second % 3600) / 60;
        s = (second % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
        if (h > 0) {
            formatTime =  hs + ":" + ms + ":" + ss;
        } else {
            formatTime =  ms + ":" + ss;
        }
        return formatTime;
    }
}
