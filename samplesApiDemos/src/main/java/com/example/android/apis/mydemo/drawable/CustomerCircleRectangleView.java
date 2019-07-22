package com.example.android.apis.mydemo.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author YouLiang.Ji
 */
public class CustomerCircleRectangleView extends View {

    private CircleRectangleDrawable drawable;

    public CustomerCircleRectangleView(Context context) {
        this(context, null);
    }

    public CustomerCircleRectangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerCircleRectangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawable = new CircleRectangleDrawable(w, h);
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
