package com.example.android.apis.mydemo.app.statusbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author YouLiang.Ji
 */
public class ObservableScrollView extends ScrollView {
    private OnObservableScrollListener mListener;

    public ObservableScrollView(Context context) {
        this(context, null);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mListener != null){
            mListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    /**
     * 自定义滑动监听,用于监听滑动位移变化
     */
    public interface OnObservableScrollListener{
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public void setOnObservableScrollListener(OnObservableScrollListener listener){
        this.mListener = listener;
    }
}
