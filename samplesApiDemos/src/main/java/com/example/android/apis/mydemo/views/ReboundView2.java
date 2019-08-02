package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
     * 存储阻尼view初始四个(左上右下)点坐标
     */
    private Rect mRect = new Rect();
    /**
     * 摩擦力,摩擦力越大view移动距离越小
     */
    private static final float FORCE = 3.5f;
    /**
     * 拉伸张力
     */
    private float PULLRATE = -1;

    /**
     * 滑动值
     */
    private int scrolls = 0;

    private NestedScrollingParentHelper mNestedHelper;

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

    /*@Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (mRect.isEmpty()) {
                    // 保存正常的布局位置
                    mRect.set(getLeft(), getTop(),
                            getRight(), getBottom());
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    animation();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                int deltaY = (int) ((mDownY - moveY) / SIZE);
                mDownY = moveY;
                //这里移动布局
                layout(getLeft(), getTop() - deltaY, getRight(), getBottom() - deltaY);
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);

    }*/

    // 开启动画移动
    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, getTop(), mRect.top);
        ta.setDuration(200);
        startAnimation(ta);
        // 设置回到正常的布局位置
        layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !mRect.isEmpty();
    }

    // 是否需要移动布局
    public boolean isNeedMove() {
        int offset = getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
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
        scrolls = 0;
        scrollTo(0, 0);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        log(String.format("onNestedPreScroll, dy=%s, getScrollY=%s", dy, getScrollY()));
        // 如果在自定义ViewGroup之上还有父View交给我来处理
       /* int dscroll = dy - consumed[1];
        scrolls += dscroll / PULLRATE;
        scrollBy(0, dscroll);
        consumed[1] = dscroll;*/
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        log("onNestedScroll");
        getParent().requestDisallowInterceptTouchEvent(true);
        scrolls += dyUnconsumed / FORCE;
//        super.scrollTo(0, scrolls);
        scrollTo(0, scrolls);
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        log("onNestedPreFling");
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        log("onNestedFling");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }
}
