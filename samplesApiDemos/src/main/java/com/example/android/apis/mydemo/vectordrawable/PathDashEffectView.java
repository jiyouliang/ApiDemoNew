package com.example.android.apis.mydemo.vectordrawable;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author YouLiang.Ji
 * DashEffect实现轨迹动画
 */
public class PathDashEffectView extends View {
    private Path mPath;
    private Paint paint;
    //路径测量
    private PathMeasure mPathMeasure;
    //路径长度
    private float mLength;
    private ValueAnimator animator;
    private float mAnimValue;
    private DashPathEffect mPathEffect;


    public PathDashEffectView(Context context) {
        this(context, null);
    }

    public PathDashEffectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathDashEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        mPath = new Path();

        mPath.moveTo(6, 6);
        //绘制三角形
        mPath.lineTo(0+6, 0+6);
        mPath.lineTo(0+6, 400+6);
        mPath.lineTo(300+6, 200+6);
        //关闭
        mPath.close();

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);
        //获取路径长度
        mLength = mPathMeasure.getLength();

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimValue = (float) animation.getAnimatedValue();
                mPathEffect = new DashPathEffect(new float[]{mLength, mLength}, mLength * mAnimValue);
                //设置路径风格
                paint.setPathEffect(mPathEffect);
                invalidate();
            }
        });
        animator.start();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, paint);
    }
}
