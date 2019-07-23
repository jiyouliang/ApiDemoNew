package com.example.android.apis.mydemo.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.drawable.NumberKeyboardDrawable;

/**
 * @author YouLiang.Ji
 * 数字键盘控件
 */
public class NumberKeyboardView extends BaseViewGroup implements View.OnClickListener {

    private static final String TAG = "NumberKeyboardView";
    private static final int COLOR_BG = 0xFFDBDEE9;
    private static final String[] NUMBERS = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    private static final int MARGIN = 10;
    //item高度
    private static final int ITEM_HEIGHT = 46;
    private int mItemWidth;
    private int mItemMargin;
    private int mItemHeight;
    private int w;
    private Bitmap mBitmapDelete;
    private OnKeyboardClickListener mListener;

    public NumberKeyboardView(Context context) {
        this(context, null);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(COLOR_BG);

        mItemHeight = (int) dp2px(ITEM_HEIGHT);
        mItemMargin = (int) dp2px(MARGIN);
        initChildrenView();
        setListener();

    }

    /**
     * 初始化子控件
     */
    private void initChildrenView() {
        removeAllViews();
        //数字控件
        for (int i = 0; i < NUMBERS.length; i++) {
            ItemTextView item = (ItemTextView) getItemView();
            item.setText(NUMBERS[i]);
            addView(item);
        }
        //图片控件
        ImageView imageView = new ImageView(getContext());
       /* ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams((int) dp2px(mItemHeight), (int) dp2px(mItemHeight));
        imageView.setLayoutParams(lp);*/
        mBitmapDelete = BitmapFactory.decodeResource(getResources(), R.drawable.delete_keyboard);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        imageView.setImageBitmap(mBitmapDelete);
        imageView.setImageResource(R.drawable.delete_keyboard);
        addView(imageView);


    }

    /**
     * 使用matrix缩放图片
     * @param bitmap
     * @param scale
     * @return
     */
    private Bitmap bitMapScale(Bitmap bitmap,float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 计算图片采样率,用于压缩Bitmap
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
            return inSampleSize;
        }
        return inSampleSize;
    }

    /**
     * 设置监听
     */
    private void setListener() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView != null) {
                childView.setOnClickListener(this);
            }
        }
    }

    public void setOnKeyboardClickListener(OnKeyboardClickListener listener) {
        this.mListener = listener;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        log("onSizeChanged:w=" + w + ",h=" + h);
        this.w = w;
    }

    /**
     * 在该方法中测量子控件
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        log("onMeasure");
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = 4 * (mItemHeight + mItemMargin) + mItemMargin;
        int count = getChildCount();

        mItemWidth = (int) ((measuredWidth - 4 * mItemMargin) / 3);
        if (count > 0) {
            for (int i = 0; i < count - 1; i++) {
                TextView item = (TextView) getChildAt(i);
                //测量子控件
                int itemWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mItemWidth, MeasureSpec.EXACTLY);

                int itemHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mItemHeight, MeasureSpec.EXACTLY);
                item.measure(itemWidthMeasureSpec, itemHeightMeasureSpec);
            }
        }

        //测量图片控件大小
        ImageView imageView = (ImageView) getChildAt(count - 1);
        imageView.measure(MeasureSpec.makeMeasureSpec(mItemWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mItemHeight, MeasureSpec.EXACTLY));

        // 设置自己的宽度和高度
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * 在该方法中布局子控件
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        log("onLayout");
        int count = getChildCount();
        int left = 0;
        int top = 0;
        if (count > 0) {
            for (int i = 0; i < count - 2; i++) {
                TextView item = (TextView) getChildAt(i);
                //计算x轴方向
                left = mItemMargin + i % 3 * (mItemMargin + mItemWidth);
                //计算y轴方向
                top = mItemMargin + i / 3 * (mItemHeight + mItemMargin);
                //布局子控件
                item.layout(left, top, left + mItemWidth, top + mItemHeight);
            }
        }
        //布局最后一个数字0
        TextView item = (TextView) getChildAt(count - 2);
        left = mItemMargin + (count - 1) % 3 * (mItemMargin + mItemWidth);
        top = mItemMargin + (count - 1) / 3 * (mItemHeight + mItemMargin);
        item.layout(left, top, left + mItemWidth, top + mItemHeight);
        //布局删除图标
        ImageView imageView = (ImageView) getChildAt(count - 1);
        left = mItemMargin + count % 3 * (mItemMargin + mItemWidth);
        top = mItemMargin + count / 3 * (mItemHeight + mItemMargin);
        imageView.layout(left, top, left + mItemWidth, top + mItemHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private TextView getItemView() {
        TextView textView = new ItemTextView(getContext());
        textView.setText("A");
        return textView;
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }
        if (v instanceof ItemTextView) {
            mListener.onNumberKeyClick(((ItemTextView) v).getText().toString());
        } else if (v instanceof ImageView) {
            mListener.onDeleteClick();
        }
    }


    /**
     * 数字键盘item view
     */
    @SuppressLint("AppCompatCustomView")
    private static class ItemTextView extends TextView {
        private static final int COLOR_TEXT = 0xFF252525;
        private static final int TEXT_SIZE = 20;//20sp

        public ItemTextView(Context context) {
            this(context, null);
        }

        public ItemTextView(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ItemTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setGravity(Gravity.CENTER);
            setTextColor(COLOR_TEXT);
            setTextSize(TEXT_SIZE);

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            StateListDrawable drawableList = new StateListDrawable();
            drawableList.addState(new int[]{android.R.attr.state_pressed},
                    new NumberKeyboardDrawable(NumberKeyboardDrawable.STATE_PRESSED, w, h));
            drawableList.addState(new int[0], new NumberKeyboardDrawable(NumberKeyboardDrawable.STATE_NORMAL, w, h));
            setBackground(drawableList);
        }


        /**
         * 点击键盘回调
         */


       /* private float sp2px(float spValue) {
            return TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_PX, spValue, getResources()
                            .getDisplayMetrics());
        }*/
    }

    public interface OnKeyboardClickListener {

        /**
         * 选中数字
         *
         * @param num
         */
        void onNumberKeyClick(String num);

        /**
         * 删除
         */
        void onDeleteClick();
    }


}
