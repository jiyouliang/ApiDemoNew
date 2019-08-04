package com.example.android.apis.mydemo.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @author YouLiang.Ji
 * 阻尼/回弹控件
 */
public class ReboundView2 extends LinearLayout implements NestedScrollingParent2 {
    private static final String TAG = "ReboundView2";
    /**
     * 摩擦力,摩擦力越大view移动距离越小
     */
    private static final float FORCE = 3.0f;

    /**
     * 滑动值
     */
    private int scrolls = 0;

    private NestedScrollingParentHelper mNestedHelper;
    private int dy;

    public ReboundView2(@NonNull Context context) {
        this(context, null);
    }

    public ReboundView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mNestedHelper = new NestedScrollingParentHelper(this);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        log("onStartNestedScroll");
//        return target instanceof RecyclerView;
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        log("onNestedScrollAccepted");
        mNestedHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        log("onStopNestedScroll");
        mNestedHelper.onStopNestedScroll(target, type);
        scrollTo(0, 0);
        scrolls = 0;


    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        log(String.format("onNestedPreScroll, dy=%s, getScrollY=%s", dy, getScrollY()));
        this.dy = dy;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        log("onNestedScroll");
        // 手指触发滑动,即非Filing,并且只允向下滑动(只显示头部阻尼效果)
        if(type == ViewCompat.TYPE_TOUCH && dy <= 0){
            getParent().requestDisallowInterceptTouchEvent(true);
            scrolls += dyUnconsumed / FORCE;
            scrollTo(0, scrolls);
        }
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        log(String.format("onNestedPreFling, velocityX=%s, velocityY=%s, getBottom()=%s=, getMeasuredHeight()=%s", velocityX, velocityY, getBottom(), getHeight()));
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        log(String.format("onNestedFling, velocityX=%s, velocityY=%s", velocityX, velocityY));
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }


    private void log(String msg) {
        Log.d(TAG, msg);
    }
}
