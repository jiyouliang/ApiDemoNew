package com.example.android.apis.mydemo.views;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.android.apis.R;

/**
 * @author YouLiang.Ji
 * 仿高德地图输入手机号EditText效果:EditText获取焦点下划线向右侧移动，并且有透明度动画,EditText右侧有删除图标
 */
@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText implements View.OnClickListener {

    private static final int COLOR_LIGHT = 0xFFECECEC;
    private static final int COLOR_GRAY = 0xFF686868;
    private Paint mPaint;
    private Path mPath;
    private Path mPathTransform;
    private Paint mPaintTransform;
    private int mHeight;
    private int mWidth;
    private static final int STROKE_WIDTH = 4;
    private ValueAnimator animator;
    private float mAnimatedValue;
    private Bitmap mBtmClose;
    private float left;
    private int top;


    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.WHITE);
        setFocusable(false);
        setFocusableInTouchMode(false);
        //取消默认弹出软键盘
//        setInputType(InputType.TYPE_NULL);
        //文字垂直居中
        setGravity(Gravity.CENTER_VERTICAL);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(COLOR_LIGHT);
        mPaint.setStrokeWidth(STROKE_WIDTH);

        mPaintTransform = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTransform.setStyle(Paint.Style.STROKE);
        mPaintTransform.setColor(COLOR_GRAY);
        mPaintTransform.setStrokeWidth(STROKE_WIDTH);

        mPath = new Path();
        mPathTransform = new Path();

        mBtmClose = BitmapFactory.decodeResource(getResources(), R.drawable.commute_tips_close2);

        setOnClickListener(this);
    }

    //布局文件解析完成回调
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        System.out.println(size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mHeight = h;
        this.mWidth = w;


        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(800);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) animation.getAnimatedValue();
                //刷新UI
                invalidate();
            }
        });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //绘制bitmap
        left = mWidth - mBtmClose.getWidth() - dp2px(10);
        top = (mHeight - mBtmClose.getHeight()) / 2;
        canvas.drawBitmap(mBtmClose, left, top, mPaint);

        mPath.moveTo(STROKE_WIDTH, mHeight - STROKE_WIDTH / 2);
        mPath.lineTo(mWidth - STROKE_WIDTH, mHeight - STROKE_WIDTH / 2);
        canvas.drawPath(mPath, mPaint);

        //绘制渐变线条
        if (isFocusable()) {
            mPathTransform.moveTo(STROKE_WIDTH, mHeight - STROKE_WIDTH / 2);
            mPathTransform.lineTo(mWidth * mAnimatedValue, mHeight - STROKE_WIDTH / 2);
            canvas.drawPath(mPathTransform, mPaintTransform);
        }
    }

    @Override
    public void onClick(View v) {
        if(!isFocusable() || !isFocusableInTouchMode()){
            setFocusable(true);
            setFocusableInTouchMode(true);
            requestFocus();
            InputMethodManager inputManager =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        animator.start();
    }

    

    private float dp2px(float dpValue){
        return  TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, dpValue, getResources()
                        .getDisplayMetrics());
    }
}
