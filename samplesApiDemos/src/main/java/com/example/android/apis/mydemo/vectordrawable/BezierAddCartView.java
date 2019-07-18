package com.example.android.apis.mydemo.vectordrawable;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.example.android.apis.mydemo.util.BezierUtil;

/**
 * @author YouLiang.Ji
 * 贝塞尔曲线实现添加到购物车效果、带抛物线
 */
public class BezierAddCartView extends View implements View.OnClickListener {
    private Path mPath;
    private Paint paintLine;//这里变量名没加前缀，为了不和mPath混淆
    private Paint paintCircle;//绘制原形

    //起始点
    private float mStartX;
    private float mStartY;

    //参考点
    private float mFlagX;
    private float mFlagY;

    //终点
    private float mEndX;
    private float mEndY;

    //移动坐标
    private float mMoveX;
    private float mMoveY;

    public BezierAddCartView(Context context) {
        this(context, null);
    }

    public BezierAddCartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierAddCartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
        mPath = new Path();
        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(8);

        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        paintCircle.setStrokeWidth(8);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mStartX = 50;
        mStartY = 100;

        mMoveX = mStartX;
        mMoveY = mStartY;

        mFlagX = 300;
        mFlagY = 120;

        mEndX = 300;
        mEndY = h - 30;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartX, mStartY);
        mPath.quadTo(mFlagX, mFlagY, mEndX, mEndY);

        canvas.drawCircle(mStartX, mStartY, 20, paintCircle);
        canvas.drawCircle(mEndX, mEndY, 20, paintCircle);
        //绘制移动物体
        canvas.drawCircle(mMoveX, mMoveY, 20, paintCircle);
        canvas.drawPath(mPath, paintLine);


    }

    @Override
    public void onClick(View v) {
        BezierEvaluator evaluator = new BezierEvaluator(new PointF(mFlagX, mFlagY));

        //根据上面参考的计算：从起点到终点变化，贝塞尔曲线上的坐标值
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(mStartX, mFlagY), new PointF(mEndX, mEndY));
        animator.setDuration(600);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取贝塞尔曲线上坐标
                PointF pointF = (PointF) animation.getAnimatedValue();
                mMoveX = pointF.x;
                mMoveY = pointF.y;

                invalidate();
            }
        });

        //加速效果
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    /**
     * 通过TypeEvaluator构造曲线变换区间，传入：起始点、参考点、终点，通过evaluate计算贝塞尔曲线任意一个点坐标
     */
    private static class BezierEvaluator implements TypeEvaluator<PointF> {

        private PointF mFlagPoint;

        public BezierEvaluator(PointF flagPoint) {
            mFlagPoint = flagPoint;
        }

        @Override
        public PointF evaluate(float v, PointF pointF, PointF t1) {
            return BezierUtil.calculateBezierPointForQuadratic(v, pointF, mFlagPoint, t1);
        }
    }
}
