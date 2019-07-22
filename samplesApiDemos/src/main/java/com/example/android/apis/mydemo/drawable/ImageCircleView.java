package com.example.android.apis.mydemo.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.apis.R;

/**
 * @author YouLiang.Ji
 * 圆形图片控件,将矩形图片处理成圆角
 */
public class ImageCircleView extends View {

    private ImageCircleDrawable drawable;
    private int w;
    private int h;

    public ImageCircleView(Context context) {
        this(context, null);
    }

    public ImageCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = calculateInSampleSize();
        options.inJustDecodeBounds = false;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_universe, options);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_universe);
        drawable = new ImageCircleDrawable(w, h, bitmap);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawable.draw(canvas);
        canvas.restore();
    }

    /**
     * 计算图片采样率,用于压缩图片显示
     * @return
     */
    private int calculateInSampleSize(){
        //这里简单取最小值
        return Math.min(w, h);
    }
}
