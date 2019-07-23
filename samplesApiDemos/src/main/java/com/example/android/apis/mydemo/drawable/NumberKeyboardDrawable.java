package com.example.android.apis.mydemo.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author YouLiang.Ji
 * 自定义数字键盘圆角矩形
 */
public class NumberKeyboardDrawable extends Drawable {
    private final int w;
    private final int h;
    private Paint mPaint;
    //圆角
    private static final int RADIUS = 20;

    public NumberKeyboardDrawable(int w, int h) {
        this.w = w;
        this.h = h;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
