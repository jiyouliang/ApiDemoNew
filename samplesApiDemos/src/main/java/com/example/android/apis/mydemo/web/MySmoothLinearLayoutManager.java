package com.example.android.apis.mydemo.web;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class MySmoothLinearLayoutManager extends LinearLayoutManager {
    private boolean isInit = true;
    public final static int TYPE_DEFAULT = 0;
    public final static int TYPE_SMOOTH = 1;

    private int mType = TYPE_DEFAULT;

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
                        float speedPerPixel = 0;
                        switch (mType){
                            case TYPE_DEFAULT:
                                speedPerPixel = super.calculateSpeedPerPixel(displayMetrics);;
                                break;
                            case TYPE_SMOOTH:
                                speedPerPixel = 520f / displayMetrics.densityDpi;
                                break;
                        }
                        return speedPerPixel;
                    }
                };

        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    /**
     * {@link #TYPE_DEFAULT} or
     * {@link #TYPE_SMOOTH}
     * @param type
     */
    public void setSmoothType(int type){
        this.mType = type;
    }


}
