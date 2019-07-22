package com.example.android.apis.mydemo.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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

    /**
     * 调用者传入大小
     * @param w
     * @param h
     */
    public CircleRectangleDrawable(int w, int h) {
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
        RectF rectF = new RectF(0, 0, w, h);
//        canvas.drawRect(rect, mPaint);

        final float halfBorderWidth = mPaint.getStrokeWidth() / 2f;

        // We need to inset the oval bounds by half the border width. This is because stroke draws
        // the center of the border on the dimension. Whereas we want the stroke on the inside.
        /*rectF.left += halfBorderWidth;
        rectF.top += halfBorderWidth;
        rectF.right -= halfBorderWidth;
        rectF.bottom -= halfBorderWidth;*/
        canvas.save();
        canvas.rotate(90, rectF.centerX(), rectF.centerY());
        // Draw the oval
        canvas.drawRoundRect(rectF, 10, 10, mPaint);
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
