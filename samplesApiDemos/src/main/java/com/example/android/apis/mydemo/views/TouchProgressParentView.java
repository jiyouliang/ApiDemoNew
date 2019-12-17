package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.apis.R;
import com.jiyouliang.log.Logger;

public class TouchProgressParentView extends LinearLayout {
    private static final String TAG = "TouchProgressView";

    private TextView mTvTime;
    private ProgressBar mProgressBar;
    private LinearLayout mProgressContainer;
    private GestureDetector mGestureDetector;
    private int mVideoWidth;
    private int mVideoProgress;
    private int mDownProgress;
    private ImageView mIvBrightness;
    private int mScrollMode = ScrollMode.NONE;
    private float brightness = -1;
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
    private AudioManager mAudioManager;
    private int mMaxVolume;
    private int mOldVolume = 0;
    private int mVideoHeight;
    private ImageView mIvVolume;

    public TouchProgressParentView(Context context) {
        this(context, null, 0);
    }

    public TouchProgressParentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchProgressParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mVideoWidth = getWidth();
        mVideoHeight = getHeight();
    }

    private void init(Context context) {
        mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        LayoutInflater.from(context).inflate(R.layout.view_touch_progress_parent_layout, this, true);
        mProgressContainer = findViewById(R.id.progress_container);
        mProgressBar = findViewById(R.id.progressBar);
        mTvTime = findViewById(R.id.tv_time);
        mIvVolume = findViewById(R.id.iv_volume);
        // 亮度图标
        mIvBrightness = findViewById(R.id.iv_brightness);
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
                if (downEvent == null || moveEvent == null) {
                    return false;
                }
                Logger.d(TAG, "onScroll:mScrollMode=" + mScrollMode);
                switch (mScrollMode) {
                    case ScrollMode.NONE:
                        // 估计滑动位置、距离判断滑动模式
                        if (Math.abs(downEvent.getX() - moveEvent.getX()) <= offsetX) {
                            // 水平滑动距离小于阻尼值
                            int halfVideoWidth = mVideoWidth / 2;
                            if (downEvent.getX() < halfVideoWidth) {
                                mScrollMode = ScrollMode.BRIGHTNESS;
                            } else {
                                mScrollMode = ScrollMode.VOLUME;
                            }
                        } else {
                            mScrollMode = ScrollMode.VIDEO_PROGRESS;
                        }
                        break;
                    case ScrollMode.VIDEO_PROGRESS:
                        setVideoProgress(downEvent, moveEvent);
                        break;
                    case ScrollMode.VOLUME:
                        int value = TouchProgressParentView.this.getHeight() / mMaxVolume;
                        int newVolume = (int) ((downEvent.getY() - moveEvent.getY()) / value / 2 + mOldVolume);
                        if (newVolume < 0 || newVolume > mMaxVolume) {
                            break;
                        }
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, AudioManager.FLAG_PLAY_SOUND);
                        float volumeProgress = newVolume / Float.valueOf(mMaxVolume) * 100;
                        if (mProgressContainer.getVisibility() != View.VISIBLE) {
                            mProgressContainer.setVisibility(View.VISIBLE);
                        }
                        if (mIvBrightness.getVisibility() != View.GONE) {
                            mIvBrightness.setVisibility(View.GONE);
                        }
                        if (mProgressBar.getVisibility() != View.GONE) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        if (mIvVolume.getVisibility() != View.VISIBLE) {
                            mIvVolume.setVisibility(View.VISIBLE);
                        }

                        mTvTime.setText(String.format("%s%s", (int) volumeProgress, "%"));
                        break;
                    case ScrollMode.BRIGHTNESS:
                        float deltaY = downEvent.getY() - moveEvent.getY();
                        float percent = deltaY / mVideoHeight;

                        Activity activity = getActivity();
                        if (activity == null)
                            break;
                        if (brightness < 0) {
                            brightness = activity.getWindow().getAttributes().screenBrightness;
                            if (brightness <= 0.00f) {
                                brightness = 0.50f;
                            } else if (brightness < 0.01f) {
                                brightness = 0.01f;
                            }
                        }
                        if (mProgressContainer.getVisibility() != View.VISIBLE) {
                            mProgressContainer.setVisibility(View.VISIBLE);
                        }
                        if (mIvBrightness.getVisibility() != View.VISIBLE) {
                            mIvBrightness.setVisibility(View.VISIBLE);
                        }
                        if (mProgressBar.getVisibility() != View.GONE) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        if (mIvVolume.getVisibility() != View.GONE) {
                            mIvVolume.setVisibility(View.GONE);
                        }
                        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
                        lpa.screenBrightness = brightness + percent;
                        if (lpa.screenBrightness > 1.0f) {
                            lpa.screenBrightness = 1.0f;
                        } else if (lpa.screenBrightness < 0.01f) {
                            lpa.screenBrightness = 0.01f;
                        }
                        mTvTime.setText(String.format("%s%s", (int) (lpa.screenBrightness * 100), "%"));
                        activity.getWindow().setAttributes(lpa);
                        break;
                }

                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // 重置状态
                resetState(TouchProgressParentView.this.getWidth(), mProgressBar.getProgress());
                return true;
            }
        });
        mGestureDetector.setIsLongpressEnabled(false);
    }

    /**
     * 设置视频进度
     *
     * @param downEvent
     * @param moveEvent
     */
    private void setVideoProgress(MotionEvent downEvent, MotionEvent moveEvent) {
        if (mProgressContainer.getVisibility() != View.VISIBLE) {
            mProgressContainer.setVisibility(View.VISIBLE);
        }
        if (mIvBrightness.getVisibility() != View.GONE) {
            mIvBrightness.setVisibility(View.GONE);
        }
        if (mProgressBar.getVisibility() != View.VISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        if (mIvVolume.getVisibility() != View.GONE) {
            mIvVolume.setVisibility(View.GONE);
        }
        //修改进度
        float dis = moveEvent.getX() - downEvent.getX();
        float percent = dis / mVideoWidth;
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
        mTvTime.setText(String.format("%s/%s", formattedTime((long) currentTime), formattedTime(TOTAL_TIME)));
    }

    private void resetState(int videoWidth, int downProgress) {
        showLog("resetState:videoWidth=" + videoWidth + ",downProgress=" + downProgress);
//        mVideoWidth = videoWidth;
        mVideoProgress = 0;
        mScrollMode = ScrollMode.NONE;
        mDownProgress = downProgress;
        mOldVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.d(TAG, "onTouchEvent:event=" + event);
        if (mGestureDetector != null) {
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

    private Activity getActivity() {
        if (getContext() instanceof Activity) {
            return (Activity) getContext();
        }
        return null;
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showLog(String log) {
        Logger.d(TAG, log);
    }

    /**
     * 格式化时间
     *
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
            formatTime = hs + ":" + ms + ":" + ss;
        } else {
            formatTime = ms + ":" + ss;
        }
        return formatTime;
    }

    /**
     * 滚动模式
     */
    static class ScrollMode {
        static final int NONE = 0;
        /**
         * 视频进度
         */
        static final int VIDEO_PROGRESS = 1;
        /**
         * 亮度
         */
        static final int BRIGHTNESS = 2;
        /**
         * 音量
         */
        static final int VOLUME = 3;

    }
}
