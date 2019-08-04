package com.example.android.apis.mydemo.nestedscrolling;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.android.apis.R;

/**
 * @author YouLiang.Ji
 *
 */
public class DampLayout extends LinearLayout implements NestedScrollingParent2 {


    private static final int MAX_HEIGHT = 400;
    private View headerView;
    private View footerView;
    private View childView;
    private ReboundAnimator animator = null;
    private boolean isFirstRunAnim;//针对冗余fling期间，保证回弹动画只执行一次
    private RecyclerView mRecycleView;

    public DampLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        headerView = new View(context);
        footerView = new View(context);
    }

    @Override
    protected void onFinishInflate() {//在setContentView之后、onMeasure之前调用的方法
        super.onFinishInflate();
        childView = getChildAt(0);  //这里的childView将是你xml中嵌套进的子view，我这里是RecyclerView
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_HEIGHT);
        addView(headerView, 0, layoutParams);
        addView(footerView, getChildCount(), layoutParams);
        mRecycleView = findViewById(R.id.recycle_view);
        // 上移，即隐藏header
        scrollBy(0, MAX_HEIGHT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = childView.getLayoutParams();
        params.height = getMeasuredHeight();

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            // 计算RecyclerView高度,避免底部处先空白
            if(child instanceof RecyclerView){
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                int childMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
                child.measure(widthMeasureSpec, childMeasureSpec);
            }
        }
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        if (animator == null) {//初始化动画对象
            animator = new ReboundAnimator();
        }
    }

    /**
     * 返回true代表处理本次事件
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes, int type) {
        return target instanceof RecyclerView;
    }

    /**
     * 复位初始位置
     */
    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        isFirstRunAnim = false;
        if (getScrollY() != MAX_HEIGHT) {//优化代码执行效率
            animator.startOfFloat(target, getScrollY());
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        // 如果在自定义ViewGroup之上还有父View交给我来处理
        getParent().requestDisallowInterceptTouchEvent(true);
        if (type == ViewCompat.TYPE_TOUCH) {//手指触发的滑动
            // dy>0向下scroll dy<0向上scroll
            boolean hiddenTop = dy > 0 && getScrollY() < MAX_HEIGHT && !target.canScrollVertically(-1);
            boolean showTop = dy < 0 && !target.canScrollVertically(-1);
            boolean hiddenBottom = dy < 0 && getScrollY() > MAX_HEIGHT && !target.canScrollVertically(1);
            boolean showBottom = dy > 0 && !target.canScrollVertically(1);
            if (hiddenTop || showTop || hiddenBottom || showBottom) {
                if (animator.isStarted()) {
                    animator.pause();
                }
                scrollBy(0, damping(dy));
                if (animator.isPaused()) {//手动cancel 避免内存泄漏
                    animator.cancel();
                }
                consumed[1] = dy;
            }
            adjust(dy, target);//调整错位
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        getParent().requestDisallowInterceptTouchEvent(true);
        if (type == ViewCompat.TYPE_NON_TOUCH) {//非手指触发的滑动，即Filing
            //解决冗余fling问题
            if (((Math.floor(getScrollY()) == 0) || ((Math.ceil(getScrollY()) == 2 * MAX_HEIGHT))) && !isFirstRunAnim) {
                int startY = 0;
                if (dyUnconsumed > 0) {
                    startY = 2 * MAX_HEIGHT;
                }
                animator.startOfFloat(target, startY);
                isFirstRunAnim = true;
            }
            if (isFirstRunAnim)
                return;

            // dy>0向下fling dy<0向上fling
            boolean showTop = dyUnconsumed < 0 && !target.canScrollVertically(-1);
            boolean showBottom = dyUnconsumed > 0 && !target.canScrollVertically(1);
            if (showTop || showBottom) {
                if (animator.isStarted()) {
                    animator.pause();
                }
                scrollBy(0, damping(dyUnconsumed));
                if (animator.isPaused()) {//手动cancel 避免内存泄漏
                    animator.cancel();
                }
            }
            adjust(dyUnconsumed, target);//调整错位
        }

    }

    /**
     * 衰减可继续scroll或fling的距离
     */
    private int damping(int dy) {
        //计算衰减系数,越大可继续scroll或fling的距离越短
        int i = (int) (Math.abs(MAX_HEIGHT - getScrollY()) * 0.01);
        return i < 2 ? dy : dy / i;
    }

    /**
     * 调整错位问题(强转精度损失造成的错位)
     */
    private void adjust(int condition1, View condition2) {
        if (condition1 > 0 && getScrollY() > MAX_HEIGHT && !condition2.canScrollVertically(-1)) {
            scrollTo(0, MAX_HEIGHT);
        }
        if (condition1 < 0 && getScrollY() < MAX_HEIGHT && !condition2.canScrollVertically(1)) {
            scrollTo(0, MAX_HEIGHT);
        }
    }

    /**
     * 限制滑动 移动y轴不能超出最大范围
     */
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        } else if (y > MAX_HEIGHT * 2) {
            y = MAX_HEIGHT * 2;
        }
        super.scrollTo(x, y);
    }

    /**
     * 回弹动画
     */
    private class ReboundAnimator extends ValueAnimator {
        private View target;

        private ReboundAnimator() {
            init();
        }

        private void init() {
            this.setInterpolator(new DecelerateInterpolator());//添加减速插值器
            this.setDuration(260);
            //添加值更新监听器
            this.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float currValue = (float) getAnimatedValue();
                    scrollTo(0, (int) currValue);
                    // 调整错位问题(强转精度损失造成的错位)
                    if (getScrollY() > MAX_HEIGHT && !target.canScrollVertically(-1)) {
                        scrollTo(0, MAX_HEIGHT);
                    }
                    if (getScrollY() < MAX_HEIGHT && !target.canScrollVertically(1)) {
                        scrollTo(0, MAX_HEIGHT);
                    }
                }
            });
        }

        private void startOfFloat(View target, float startY) {
            this.setFloatValues(startY, MAX_HEIGHT);
            this.target = target;
            this.start();
        }
    }
}
