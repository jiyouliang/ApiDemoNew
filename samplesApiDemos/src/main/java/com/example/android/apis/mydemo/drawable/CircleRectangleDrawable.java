package com.example.android.apis.mydemo.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author YouLiang.Ji
 * 圆角矩形
 */
public class CircleRectangleDrawable extends Drawable {

    private final int w;
    private final int h;
    private Paint mPaint;
    private static final int STROKE_WIDTH = 6;
    //x轴和y轴圆角半径
    private static final int RADIUS_X = 30;
    private static final int RADIUS_Y = 30;


    /**
     * 调用者传入大小
     *
     * @param w
     * @param h
     */
    public CircleRectangleDrawable(int w, int h) {
        this.w = w;
        this.h = h;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(0xFFDBDEE9);
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    public void draw(@NonNull Canvas canvas) {

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        //起点从strokeWidth一半开始,否则出现线条变粗问题
        canvas.drawRoundRect(STROKE_WIDTH / 2F, STROKE_WIDTH / 2F, w - STROKE_WIDTH / 2F, h - STROKE_WIDTH / 2F,
                RADIUS_X, RADIUS_Y, mPaint);


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
     *
     * @return
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
