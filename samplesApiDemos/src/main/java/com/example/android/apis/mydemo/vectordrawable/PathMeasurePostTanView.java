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
 * 通过PathMeasure.getPostTan绘制切线
 */
public class PathMeasurePostTanView extends View {
    private Paint paint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private float[] mPos;
    private float[] mTan;
    private ValueAnimator mAnimator;
    private float mAnimValue;

    public PathMeasurePostTanView(Context context) {
        this(context, null);
    }

    public PathMeasurePostTanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasurePostTanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        mPos = new float[2];
        mTan = new float[2];

        mPath = new Path();
        mPath.addCircle(0, 0, 100, Path.Direction.CW);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);

        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //锁定画布
        canvas.save();
        //移动画布中心
        canvas.translate(200, 200);
        canvas.drawPath(mPath, paint);
        //getPosTan方法获取摸个点上的坐标点和切线坐标,参数1:输入参数;参数2:输出参数,某点对应的坐标;参数3:输出参数,切线坐标
        mPathMeasure.getPosTan(mAnimValue * mPathMeasure.getLength(), mPos, mTan);
        canvas.drawCircle(mPos[0], mPos[1], 10, paint);
        //计算切线角度
        float degree = (float) (Math.atan2(mTan[1], mTan[0]) * 180 / Math.PI);
        //旋转画布,不旋转切线永远固定不动
        canvas.rotate(degree);
        //绘制切线
        canvas.drawLine(0, -100, 100, -100, paint);
//        canvas.drawLine(0, -100, -100, -100, paint);

        //释放画布
        canvas.restore();
    }
}
