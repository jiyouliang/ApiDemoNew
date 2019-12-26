package com.example.android.apis.mydemo.web;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class MySmoothLinearLayoutManager extends LinearLayoutManager {
    private boolean isInit = true;

    public MySmoothLinearLayoutManager(Context context) {
        super(context);
    }

    public MySmoothLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MySmoothLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        //super.smoothScrollToPosition(recyclerView, state, position);
        LinearSmoothScroller smoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {


                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        if (isInit) {
                            isInit = false;
                            float speedPerPixel = super.calculateSpeedPerPixel(displayMetrics);
                            //return 150f / displayMetrics.densityDpi;
                            return speedPerPixel;
                        } else {
                            // 滑过1px时经历的时间(ms)
                            return 520f / displayMetrics.densityDpi;
                        }
                    }
                };

        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }
}
