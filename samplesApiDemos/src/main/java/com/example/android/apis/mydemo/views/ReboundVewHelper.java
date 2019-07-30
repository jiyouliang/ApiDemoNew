package com.example.android.apis.mydemo.views;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

/**
 * @author YouLiang.Ji
 * <p>
 * 阻尼效果View控制器
 */
public class ReboundVewHelper implements View.OnTouchListener {

    private static final String TAG = "ReboundVewHelper";

    /**
     * 存储目标View四个点(左上右下)初始坐标值
     */
    private final Rect mRect = new Rect();

    /**
     * 摩擦力,摩擦力越大view移动距离越小
     */
    private static final float FORCE = 2.5f;
    private float mDownY;

    /**
     * 对指定view设置阻尼效果
     *
     * @param target 目标view
     */
    public void setReboundView(ViewGroup target) {
        if (target == null) {
            return;
        }
        target.setOnTouchListener(this);
//        target.onInterceptTouchEvent()
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(final View v, MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.clearAnimation();
                mDownY = ev.getY();
                //存储target view四个点初始坐标
                if (mRect.isEmpty()) {
                    mRect.set(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                //差值
                int deltaY = (int) ((mDownY - moveY) / FORCE);
                //y轴方向layout改变
                v.layout(v.getLeft(), v.getTop() - deltaY, v.getRight(), v.getBottom() - deltaY);
                //没有这行,将无法实现阻尼,改行代码类似绘制连线点
                mDownY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                // 开启移动动画
                TranslateAnimation ta = new TranslateAnimation(0, 0, v.getTop(), mRect.top);
                ta.setDuration(200);
                v.startAnimation(ta);
                // 设置回到正常的布局位置
                v.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
                mRect.setEmpty();
                break;
            default:
                break;
        }
        return true;
    }
}
