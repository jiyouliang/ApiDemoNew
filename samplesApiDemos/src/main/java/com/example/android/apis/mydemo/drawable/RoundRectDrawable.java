package com.example.android.apis.mydemo.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author YouLiang.Ji
 * 圆角矩形
 */
public class RoundRectDrawable extends Drawable {
    private final int w;
    private final int h;
    private Paint mPaint;
    //圆角
    private static final int RADIUS = 20;

    public RoundRectDrawable(int w, int h) {
        this.w = w;
        this.h = h;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        invalidateSelf();
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.drawRoundRect(0, 0, w, h, RADIUS, RADIUS, mPaint);
        canvas.restore();
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
