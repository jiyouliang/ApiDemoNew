package com.example.android.apis.mydemo.vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author YouLiang.Ji
 * 二阶贝塞尔曲线
 */
public class SecondBezierView extends View {

    private static final String TAG = "SecondBezierView";
    private static final boolean DEBUG = true;

    //贝塞尔起点和终点坐标
    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;
    //控制点坐标
    private float mFlagPointX;
    private float mFlagPointY;

    private Path mPath;
    private Paint mPaintBezier;
    //绘制文字
    private Paint mPaintText;

    private Paint mPaintPoint;
    private Paint mPaintFlag;


    public SecondBezierView(Context context) {
        this(context, null);
    }

    public SecondBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecondBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaintBezier.setStrokeCap(Paint.Cap.ROUND);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE); //实线

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStrokeWidth(2);
        //文字大小
        mPaintText.setTextSize(26);
        mPaintText.setStyle(Paint.Style.STROKE);

        //圆点
        mPaintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPoint.setStrokeWidth(12);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintPoint.setColor(Color.RED);


        mPaintFlag = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlag.setStrokeWidth(2);
        mPaintFlag.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        log("onSizeChanged");
        //初始化起点、终点坐标
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;
        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;
        //初始化控制点坐标
        mFlagPointX = w / 2;
        mFlagPointY = h / 2 - 350;

        mPath = new Path();
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        log("onDraw");
        //还原path
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        //绘制二级贝塞尔曲线quadTo()方法使用绝对坐标,rQuadTo()方法使用相对坐标
        //参数：控制点、终点
        mPath.quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY);

        //绘制圆点
        canvas.drawPoint(mStartPointX, mStartPointY, mPaintPoint);
        canvas.drawPoint(mFlagPointX, mFlagPointY, mPaintPoint);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintPoint);

        //绘制文字
        canvas.drawText("起点", mStartPointX-60, mStartPointY, mPaintText);
        canvas.drawText("控制点", mFlagPointX, mFlagPointY, mPaintText);
        canvas.drawText("终点", mEndPointX+30, mEndPointY, mPaintText);

        //绘制辅助线
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointX, mFlagPointY, mPaintFlag);
        canvas.drawLine(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY, mPaintFlag);

        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                //控制台跟随手指移动
                mFlagPointX = event.getX();
                mFlagPointY = event.getY();
                //刷新重绘，会重新调用onDraw方法
                invalidate();
                break;
        }
        return true;
    }

    private void log(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }
}
