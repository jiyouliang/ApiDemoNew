package com.example.android.apis.mydemo.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author YouLiang.Ji
 * 带圆角图片
 */
public class ImageCircleDrawable extends Drawable {

    private final int mWidth;
    private final int w;
    private final int h;
    private Paint mPaint;

    /**
     *
     */
    public ImageCircleDrawable(int w, int h, Bitmap bitmap) {
        //大图压缩
        this.w = w;
        this.h = h;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);//着色器 水平和竖直都需要填充满
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        mPaint.setShader(bitmapShader);
        mWidth = Math.min(bitmap.getWidth(), bitmap.getHeight()); //宽高的最小值作为直径

    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.drawCircle(w / 2, h / 2, w / 2, mPaint);
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
     *
     * @return
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
