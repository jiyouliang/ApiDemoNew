package com.example.android.apis.mydemo.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author YouLiang.Ji
 */
public class CustomerRectangleView extends View {

    private RectangleDrawable drawable;

    public CustomerRectangleView(Context context) {
        this(context, null);
    }

    public CustomerRectangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerRectangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawable = new RectangleDrawable(w, h);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawable.draw(canvas);
        canvas.restore();
    }
}
