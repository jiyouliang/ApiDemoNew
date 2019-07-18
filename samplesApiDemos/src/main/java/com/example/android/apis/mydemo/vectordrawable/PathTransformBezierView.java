package com.example.android.apis.mydemo.vectordrawable;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * @author YouLiang.Ji
 * 贝塞尔曲线实线路径变换
 */
public class PathTransformBezierView extends View implements View.OnClickListener {

    private static final String TAG = "PathTransformBezierView";
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
    private ValueAnimator mAnimator;

    public PathTransformBezierView(Context context) {
        this(context, null);
    }

    public PathTransformBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathTransformBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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


        setOnClickListener(this);
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
        //初始化控制点坐标:刚开始坐标值和起始点、终点一样
        mFlagPointOneX = mStartPointX;
        mFlagPointOneY = mStartPointY;
        mFlagPointTwoX = mEndPointX;
        mFlagPointTwoY = mEndPointY;

        mPath = new Path();
        //属性动画
        mAnimator = ValueAnimator.ofFloat(mStartPointY, h);
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new BounceInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //两个参考点Y轴坐标一样
                mFlagPointOneY = (float) animation.getAnimatedValue();
                mFlagPointTwoY = (float) animation.getAnimatedValue();
                //刷新重绘
                invalidate();
            }
        });
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


    private void log(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    @Override
    public void onClick(View v) {
        //点击触发动画
        mAnimator.start();
    }
}
