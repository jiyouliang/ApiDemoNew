package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.apis.mydemo.drawable.NumberKeyboardDrawable;

/**
 * @author YouLiang.Ji
 * 研究自定义view继承ViewGroup
 */
public class MyViewGroup extends BaseViewGroup implements View.OnClickListener {

    private static final String TAG = "MyViewGroup";

    private StateListDrawable drawable;
    private TextView item;
    private int w;
    private int h;
    private NumberKeyboardDrawable pressedDrawable;
    private NumberKeyboardDrawable normalDrawable;

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
        drawable = new StateListDrawable();
        pressedDrawable = new NumberKeyboardDrawable(NumberKeyboardDrawable.STATE_PRESSED);
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        normalDrawable = new NumberKeyboardDrawable(NumberKeyboardDrawable.STATE_NORMAL);
        drawable.addState(new int[0], normalDrawable);
//        drawable.setState(new int[0]);
        item = getItemView();
        item.setBackground(drawable);
        addView(item);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        log("onSizeChanged:w="+w+",h="+h);
        this.w = w;
        this.h = h;
    }

    /**
     * 在该方法中测量子控件
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        log("onMeasure");
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);


        //测量子控件
        int itemWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth /3, MeasureSpec.EXACTLY);
        int itemHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int) dp2px(46), MeasureSpec.EXACTLY);
        item.measure(itemWidthMeasureSpec, itemHeightMeasureSpec);

        // 设置自己的宽度和高度
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * 在该方法中布局子控件
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        log("onLayout");
        item.layout(0, 0, w/3, (int) dp2px(46));
        normalDrawable.setWidthAndHeight(w/3, (int) dp2px(46));
        pressedDrawable.setWidthAndHeight(w/3, (int) dp2px(46));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private TextView getItemView() {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(0xFF000000);
        textView.setTextSize(20);

        textView.setText("A");
        return textView;
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public void onClick(View v) {

    }
}
