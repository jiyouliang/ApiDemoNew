package com.example.android.apis.mydemo.drawable;

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author YouLiang.Ji
 * 矩形
 */
public class RectangleDrawable extends Drawable {

    private final int w;
    private final int h;
    private Paint mPaint;

    /**
     * 调用者传入大小
     * @param w
     * @param h
     */
    public RectangleDrawable(int w, int h) {
        this.w = w;
        this.h = h;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        mPaint.setColor(0xFFDBDEE9);
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        //绘制矩形
        Rect rect = new Rect(0, 0, w, h);
        canvas.drawRect(rect, mPaint);
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
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

    }

    /**
     * 不透明度
     * @return
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
