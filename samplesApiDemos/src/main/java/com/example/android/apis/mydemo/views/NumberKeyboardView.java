package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;

/**
 * @author YouLiang.Ji
 * 数字键盘控件
 */
public class NumberKeyboardView extends BaseViewGroup {

    private static final String TAG = "NumberKeyboardView";
    private static final boolean DEBUGGING = true;
    private static final int[] NUMBERS = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    //背景色
    private static final int COLOR_BG = 0xFFDBDEE9;
    private static final int COLOR_TEXT = 0xFF262626;
    private static final int PADDING = 10;//10 dp
    private int mWidth;
    private int mHeight;

    public NumberKeyboardView(Context context) {
        this(context, null);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        log("onSizeChanged");
        this.mWidth = w;
        this.mHeight = h;
    }

    /**
     * 布局子控件
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        log("onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        log("onDraw");
    }

    private void log(String msg){
        if(DEBUGGING){
            Log.d(TAG, msg);
        }
    }


}
