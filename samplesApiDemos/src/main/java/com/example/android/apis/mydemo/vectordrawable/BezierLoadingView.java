package com.example.android.apis.mydemo.vectordrawable;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author YouLiang.Ji
 * 贝塞尔曲线实现loading效果
 */
public class BezierLoadingView extends View {
    private Path mPath;
    //输出路径
    private Path mDst;
    private Paint paint;
    //路径测量
    private PathMeasure mPathMeasure;
    //路径长度
    private float mLength;
    private ValueAnimator animator;
    private float mAnimValue;


    public BezierLoadingView(Context context) {
        this(context, null);
    }

    public BezierLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        mPath = new Path();
        mDst = new Path();
        //添加圆形路径
        mPath.addCircle(400, 400, 100, Path.Direction.CW);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);
        //获取路径长度
        mLength = mPathMeasure.getLength();

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(800);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mDst.reset();
        mDst.lineTo(0, 0);
        //float start = 0;
        // 0 ~ 整个圆长度变化,以此形成动画效果
        float stop  = mLength * mAnimValue;
        //当路径绘制到半个圆时,终点快速向起点移动
        float start = (float) (stop - ((0.5 - Math.abs(mAnimValue - 0.5)) * mLength));
        //获取路径片段:true代表从起点开始截取,将截取片段输出到mDst路径中
        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, paint);
    }
}
