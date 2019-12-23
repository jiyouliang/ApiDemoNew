package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.apis.R;
import com.jiyouliang.log.Logger;

/**
 * 圆环ProgressBar
 */
public class CircleProgressBar extends View {
    private static final float DEFAULT_RADIUS = 30;
    //输出路径
    private Paint paint;
    //路径测量
    //半径
    private float radius;
    private int max;
    private int progress;
    private int backgroundColor;
    private int progressColor;
    private int mCenterX;
    private int mCenterY;
    private float strokeWidth;
    private static final String TAG = "CircleProgressBar";
    private boolean initialed;
    private RectF oval;


    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        // 进度圆弧
        oval = new RectF(mCenterX - radius + strokeWidth, mCenterY - radius + strokeWidth, mCenterX + radius - strokeWidth, mCenterY + radius - strokeWidth);
    }

    private void initAttributes(Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        radius = ta.getDimension(R.styleable.CircleProgressBar_cpbRadius, DEFAULT_RADIUS);
        max = ta.getInt(R.styleable.CircleProgressBar_cpbMax, 100);
        progress = ta.getInt(R.styleable.CircleProgressBar_cpbProgress, 0);
        backgroundColor = ta.getColor(R.styleable.CircleProgressBar_cpbBackground, Color.BLACK);
        progressColor = ta.getColor(R.styleable.CircleProgressBar_cpbProgressColor, Color.WHITE);
        strokeWidth = ta.getDimension(R.styleable.CircleProgressBar_cpbStrokeWidth, 8);
        ta.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(backgroundColor);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initialed) {
            paint.setColor(backgroundColor);
            canvas.drawCircle(mCenterX, mCenterY, radius - strokeWidth, paint);
            initialed = false;
        }
        drawProgress(canvas, progress);

    }

    /**
     * 绘制进度
     *
     * @param canvas
     * @param progress
     */
    public void drawProgress(@NonNull Canvas canvas, int progress) {

        float percentage = progress * 1.0f / max * 1.0F;
        if(percentage > 1){
            percentage = 1;
        }
        if(percentage < 0){
            percentage = 0;
        }
        int current = Math.round(percentage * 360);

        Logger.d(TAG, "drawProgress:percentage="+percentage+",current=" + current);
        paint.setColor(progressColor);
        // 绘制圆弧
        canvas.drawArc(oval, 0, current, false, paint);
    }

    /**
     * 设置最大进度
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
        postInvalidate();
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public int getMax() {
        return max;
    }

    public int getProgress() {
        return progress;
    }
}
