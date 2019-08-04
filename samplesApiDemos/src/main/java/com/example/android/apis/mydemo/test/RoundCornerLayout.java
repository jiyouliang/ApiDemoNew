package com.example.android.apis.mydemo.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * 圆角布局
 */
public class RoundCornerLayout extends RelativeLayout {

    private RoundViewDelegate mRoundViewDelegate;

    public RoundCornerLayout(Context context) {
        this(context, null);
    }

    public RoundCornerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (mRoundViewDelegate == null) {
            mRoundViewDelegate = new RoundViewDelegate(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        mRoundViewDelegate.roundRectSet(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        mRoundViewDelegate.canvasSetLayer(canvas);
        super.draw(canvas);
        canvas.restore();
    }



    private static class RoundViewDelegate {
        private final RectF mRect = new RectF();
        // 圆角半径
        private  float mRectRadius = 30;
        private final Paint maskPaint = new Paint();
        private final Paint zonePaint = new Paint();
        private View mView;

        public RoundViewDelegate(View view) {
            this.mView = view;
            init();
        }

        private void init() {
            maskPaint.setAntiAlias(true);
            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            zonePaint.setAntiAlias(true);
            zonePaint.setColor(Color.WHITE);
        }

        /**
         * 从新设置圆角
         *
         * @param adius
         */
        public void setRectAdius(float adius) {
            mRectRadius = adius;
            if (mView != null) {
                mView.invalidate();
            }
        }

        /**
         * 圆角区域设置
         *
         * @param width
         * @param height
         */
        public void roundRectSet(int width, int height) {
            mRect.set(0, 0, width, height);
        }

        /**
         * 画布区域裁剪
         *
         * @param canvas
         */
        public void canvasSetLayer(Canvas canvas) {
            canvas.saveLayer(mRect, zonePaint, Canvas.ALL_SAVE_FLAG);
            canvas.drawRoundRect(mRect, mRectRadius, mRectRadius, zonePaint);
            canvas.saveLayer(mRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        }
    }

}