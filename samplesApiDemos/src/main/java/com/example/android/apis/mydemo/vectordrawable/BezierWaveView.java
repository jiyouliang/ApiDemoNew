package com.example.android.apis.mydemo.vectordrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author YouLiang.Ji
 * 贝塞尔曲线实现波浪效果
 */
public class BezierWaveView extends View {
    private Paint mPaint;
    private Path mPath;
    //起点
    private float mStartX;
    private float mStartY;
    //终点
    private float mEndX;
    private float mEndY;
    //控制点
    private float mFlagX;
    private float mFlagY;
    //每个控制点高度
    private int mFlagHeight;
    //波浪长度
    private int mWaveLength;
    //波浪个数
    private int mWaveCount;


    public BezierWaveView(Context context) {
        this(context, null);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //起点y轴在屏幕中线
        mStartX = 0;
        mStartY = h / 2;
        mEndY = mStartY;

        mWaveLength = 100;
        //四舍五入
        mWaveCount = Math.round((float) (w / mWaveLength) + 1.5f);
        mFlagHeight = mWaveLength / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制波浪线
        mPath.reset();
        mPath.moveTo(mStartX, mStartY);
        //绘制完整波浪线
        for (int i = 0; i < mWaveCount; i++) {

        }

        // i = 0
        mFlagX = mStartX + mWaveLength / 2;
        mFlagY = mStartY + mFlagHeight;
        mEndX = mWaveLength;
        mPath.quadTo(mFlagX, mFlagY, mEndX, mEndY);

        mFlagX = mStartX + mWaveLength / 2 + mWaveLength;
        mFlagY = mStartY - mFlagHeight;
        mEndX = mWaveLength + mWaveLength;
        mPath.quadTo(mFlagX, mFlagY, mEndX, mEndY);

        //----------------------
        // i = 1
        mFlagX = mStartX + mWaveLength / 2 + mWaveLength * 2;
        mFlagY = mStartY + mFlagHeight;
        mEndX = mWaveLength + mWaveLength * 2;
        mPath.quadTo(mFlagX, mFlagY, mEndX, mEndY);

        mFlagX = mStartX + mWaveLength / 2 + mWaveLength + mWaveLength * 2;
        mFlagY = mStartY - mFlagHeight;
        mEndX = mWaveLength + mWaveLength + mWaveLength * 2;
        mPath.quadTo(mFlagX, mFlagY, mEndX, mEndY);

        // i = 2
        mFlagX = mStartX + mWaveLength / 2 + mWaveLength * 4;
        mFlagY = mStartY + mFlagHeight;
        mEndX = mWaveLength + mWaveLength * 2 + mWaveLength * 2;
        mPath.quadTo(mFlagX, mFlagY, mEndX, mEndY);


        mFlagX = mStartX + mWaveLength / 2 + mWaveLength + mWaveLength * 2 + mWaveLength * 2;
        mFlagY = mStartY - mFlagHeight;
        mEndX = mWaveLength + mWaveLength + mWaveLength * 2 + mWaveLength * 2;
        mPath.quadTo(mFlagX, mFlagY, mEndX, mEndY);







        canvas.drawPath(mPath, mPaint);

    }
}
