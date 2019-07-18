package com.example.android.apis.mydemo.vectordrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author YouLiang.Ji
 * 三阶贝塞尔曲线
 */
public class ThindBezierView extends View {

    private static final String TAG = "ThindBezierView";
    private static final boolean DEBUG = true;

    //贝塞尔起点和终点坐标
    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;
    //两个控制点坐标
    private float mFlagPointOneX;
    private float mFlagPointOneY;
    private float mFlagPointTwoX;
    private float mFlagPointTwoY;

    private Path mPath;
    private Paint mPaintBezier;
    //绘制文字
    private Paint mPaintText;

    private Paint mPaintPoint;
    private Paint mPaintFlag;

    //标记多点触控
    private boolean isSecondPoint;

    public ThindBezierView(Context context) {
        this(context, null);
    }

    public ThindBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThindBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mFlagPointOneX = w / 2 - 100;
        mFlagPointOneY = h / 2 - 350;

        mFlagPointTwoX = w / 2 + 100;
        mFlagPointTwoY = h / 2 - 350;

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
        //绘制三阶贝塞尔曲线cubicTo()方法使用绝对坐标,rCubicTo()方法使用相对坐标
        //参数：控制点1、控制点2、终点
        mPath.cubicTo(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mEndPointX, mEndPointY);

        //绘制圆点
        canvas.drawPoint(mStartPointX, mStartPointY, mPaintPoint);
        canvas.drawPoint(mFlagPointOneX, mFlagPointOneY, mPaintPoint);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintPoint);
        canvas.drawPoint(mFlagPointTwoX, mFlagPointTwoY, mPaintPoint);

        //绘制文字
        canvas.drawText("起点", mStartPointX - 60, mStartPointY, mPaintText);
        canvas.drawText("控制点1", mFlagPointOneX, mFlagPointOneY, mPaintText);
        canvas.drawText("控制点2", mFlagPointTwoX, mFlagPointTwoY, mPaintText);
        canvas.drawText("终点", mEndPointX + 30, mEndPointY, mPaintText);

        //绘制辅助线
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointOneX, mFlagPointOneY, mPaintFlag);
        canvas.drawLine(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mPaintFlag);
        canvas.drawLine(mFlagPointTwoX, mFlagPointTwoY, mEndPointX, mEndPointY, mPaintFlag);

        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //MotionEvent.ACTION_MASK让能够检测到多点触控
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                //多点触控
                isSecondPoint = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //两个手指抬起，重置开关状态
                isSecondPoint = false;
                bringToFront();
            case MotionEvent.ACTION_MOVE:
                //获取第一个手指坐标
                mFlagPointOneX = event.getX(0);
                mFlagPointOneY = event.getY(0);
                if (isSecondPoint) {
                    //获取第二个手指坐标
                    mFlagPointTwoX = event.getX(1);
                    mFlagPointTwoY = event.getY(1);
                }
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
