package com.example.android.apis.mydemo.nestedscrolling;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.example.android.apis.R;


public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "StickyNavLayout";
    private final int mScreenWidth;
    private final NestedScrollingParentHelper mParentHelper;

    private View mRlTopView;
    private View mTextView;
    private RecyclerView mRecycleView;

    private int mTopViewHeight;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private ValueAnimator mOffsetAnimator;
    private Interpolator mInterpolator;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;
    private int mTopViewWidth;
    private int mTextViewWidth;
    private int mTextViewHeight;
    private int mRecycleViewWidth;
    private int mRecycleViewHeight;

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e(TAG, "onStartNestedScroll");
        // return  true;
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.e(TAG, "onNestedScrollAccepted");
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.e(TAG, "onStopNestedScroll");
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.e(TAG, String.format("onNestedPreScroll, dx=%s, dy=%s, consumed=[%s, %s]", dx, dy, consumed[0], consumed[1]));
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    private int TOP_CHILD_FLING_THRESHOLD = 3;

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, String.format("onNestedFling, velocityX=%s, velocityY=%s", velocityX, velocityY));
        Log.e(TAG, String.format("consumed=%s", consumed));
        //如果是recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        //这里判断如果第一个元素的位置是大于TOP_CHILD_FLING_THRESHOLD的
        //认为已经被消耗，在animateScroll里不会对velocityY<0时做处理
        if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
        }
        if (!consumed) {
            animateScroll(velocityY, computeDuration(0), consumed);
        } else {
            animateScroll(velocityY, computeDuration(velocityY), consumed);
        }
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //不做拦截 可以传递给子View
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.e(TAG, "getNestedScrollAxes");
        return 0;
    }

    /**
     * 根据速度计算滚动动画持续时间
     *
     * @param velocityY
     * @return
     */
    private int computeDuration(float velocityY) {
        final int distance;
        if (velocityY > 0) {
            distance = Math.abs(mRlTopView.getHeight() - getScrollY());
        } else {
            distance = Math.abs(mRlTopView.getHeight() - (mRlTopView.getHeight() - getScrollY()));
        }


        final int duration;
        velocityY = Math.abs(velocityY);
        if (velocityY > 0) {
            duration = 3 * Math.round(1000 * (distance / velocityY));
        } else {
            final float distanceRatio = (float) distance / getHeight();
            duration = (int) ((distanceRatio + 1) * 150);
        }

        return duration;

    }

    private void animateScroll(float velocityY, final int duration, boolean consumed) {
        final int currentOffset = getScrollY();
        final int topHeight = mRlTopView.getHeight();
        if (mOffsetAnimator == null) {
            mOffsetAnimator = new ValueAnimator();
            mOffsetAnimator.setInterpolator(mInterpolator);
            mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Integer) {
                        scrollTo(0, (Integer) animation.getAnimatedValue());
                    }
                }
            });
        } else {
            mOffsetAnimator.cancel();
        }
        mOffsetAnimator.setDuration(Math.min(duration, 600));

        if (velocityY >= 0) {
            mOffsetAnimator.setIntValues(currentOffset, topHeight);
            mOffsetAnimator.start();
        } else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if (!consumed) {
                mOffsetAnimator.setIntValues(currentOffset, 0);
                mOffsetAnimator.start();
            }

        }
    }


    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mParentHelper = new NestedScrollingParentHelper(this);
        setOrientation(LinearLayout.VERTICAL);

        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;

        setVerticalFadingEdgeEnabled(false);
        setHorizontalFadingEdgeEnabled(false);

    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRlTopView = findViewById(R.id.rl_top_view);
        mTextView = findViewById(R.id.tv_title);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            //宽度和parent一样,可以不需要重新计算
            //int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, params.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, params.height);
            childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, params.height);
            child.measure(widthMeasureSpec, childHeightMeasureSpec);
        }

        int width;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);

        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = measureWidth;
        } else {
            width = mScreenWidth;
        }
        setMeasuredDimension(width, measureHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mRlTopView.layout(0, 0, mTopViewWidth, mTopViewHeight);
        mTextView.layout(0, mTopViewHeight, mTextViewWidth, mTopViewHeight + mTextViewHeight);
        //RecycleView布局button参数没有加上悬浮TextView是因为TextView最终悬浮显示
        mRecycleView.layout(0, mTopViewHeight + mTextViewHeight, mRecycleViewWidth, mTopViewHeight + mRecycleViewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewWidth = mRlTopView.getMeasuredWidth();
        mTopViewHeight = mRlTopView.getMeasuredHeight();

        mTextViewWidth = mTextView.getMeasuredWidth();
        mTextViewHeight = mTextView.getMeasuredHeight();

        mRecycleViewWidth = mRecycleView.getMeasuredWidth();
        mRecycleViewHeight = mRecycleView.getMeasuredHeight();
    }


    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }


}
