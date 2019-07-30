package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * @author YouLiang.Ji
 * 阻尼/回弹控件
 */
public class ReboundRecyclerView extends RecyclerView {
    //    private View inner;
    private float mDownY;
    /**
     * 存储阻尼view初始四个(左上右下)点坐标
     */
    private Rect mRect = new Rect();
    /**
     * 阻力,也可以说是摩擦力,阻力越大拖动距离越小
     */
    private static final float SIZE = 2.5f;

    public ReboundRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ReboundRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
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
                //这里移动布局
                layout(getLeft(), getTop() - deltaY, getRight(), getBottom() - deltaY);
                mDownY = moveY;
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);

    }

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

}
