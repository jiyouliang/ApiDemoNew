package com.example.android.apis.mydemo.vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author YouLiang.Ji
 * 贝塞尔曲线实现路径平滑绘画板
 */
public class PainBoardView extends View {
    private Paint mPaint;
    private Path mPath;
    private float mDownX;
    private float mDownY;
    private float mFlagX;
    private float mFlagY;
    private float mMoveX;
    private float mMoveY;

    public PainBoardView(Context context) {
        this(context, null);
    }

    public PainBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PainBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.RED);
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //非常重要，否则只绘制一条直线
//                mPath.reset();
                mDownX = event.getX();
                mDownY = event.getY();
                //移动到
                mPath.moveTo(mDownX, mDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getX();
                mMoveY = event.getY();
                //控制在起始点到终点 的一半
                mFlagX = (mMoveX + mDownX) / 2;
                mFlagY = (mMoveY + mDownY) / 2;
                //绘制二阶贝塞尔曲线，取代drawLine,drawLine会处先路径不平滑现象
                mPath.quadTo(mDownX, mDownY, mFlagX, mFlagY);

                mDownX = mMoveX;
                mDownY = mMoveY;
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 重置线条：清空之前线条
     */
    public void resetPath(){
            mPath.reset();
            invalidate();
    }

    public void setRedPaint(){
        mPaint.setColor(Color.RED);
        invalidate();
    }

    public void setYellowPaint(){
        mPaint.setColor(Color.YELLOW);
        invalidate();
    }
}
