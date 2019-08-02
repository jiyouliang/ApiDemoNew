package com.example.android.apis.mydemo.nestedscrolling;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author YouLiang.Ji
 *
 * 研究NestScrolling机制中的 NestedScrollingParent
 */
public class MyNestScrollingParentView extends LinearLayout implements NestedScrollingParent2 {

    private NestedScrollingParentHelper mParentHelper;

    public MyNestScrollingParentView(Context context) {
        this(context, null);
    }

    public MyNestScrollingParentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNestScrollingParentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mParentHelper = new NestedScrollingParentHelper(this);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

    }
}
