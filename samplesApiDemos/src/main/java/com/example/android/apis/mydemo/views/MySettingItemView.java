package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.apis.R;

/**
 * @author YouLiang.Ji
 */
public class MySettingItemView extends RelativeLayout {
    private static final String TAG = "MySettingItemView";
    private int h;
    private int height;
    private TextView mTvTitle;

    public MySettingItemView(Context context) {
        this(context, null);
    }

    public MySettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
        initFromAttributes(context, attrs);
    }

    private void initView(Context context) {
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.setting_item_vew_layout, this, true);
        mTvTitle = (TextView)findViewById(R.id.tv_title);
    }

    private void initFromAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MySettingItemView);
        // 通过自定义属性控制控件高度,为什么不通过系统属性控制?
        // 1.当我们在布局写死父控件高度,比如wrap_content或者80dp,子控件能够垂直自适应居中，但是外部使用自定义控件通过
        // android:layout_height设置固定高度诸如100dp时,会出现问题
        // 2.自定义控件中无法通过android.R.styleable读取系统属性
        height = ta.getDimensionPixelSize(R.styleable.MySettingItemView_sivHeight, 0);

        String title = ta.getString(R.styleable.MySettingItemView_sivTitle);
        if(!TextUtils.isEmpty(title) && mTvTitle != null){
            mTvTitle.setText(title);
        }
        ta.recycle();
    }

    // view绘制流程：onFinishInflate -> onMeasure -> onSizeChanged -> onLayout
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        log("onFinishInflate");
        // 通过自定义属性控制控件高度,为什么不通过系统属性控制?
        // 1.当我们在布局写死父控件高度,比如wrap_content或者80dp,子控件能够垂直自适应居中，但是外部使用自定义控件通过
        // android:layout_height设置固定高度诸如100dp时,会出现问题
        // 2.自定义控件中无法通过android.R.styleable读取系统属性
        if(getChildCount() == 1 && getChildAt(0) instanceof ViewGroup){
            ViewGroup.LayoutParams lp = getChildAt(0).getLayoutParams();
            if (height > 0 && lp != null) {
                lp.height = height;
                setLayoutParams(lp);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        log(String.format("onMeasure, widthMeasureSpec=%s, heightMeasureSpec=%s", widthMeasureSpec, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.h = h;
        log(String.format("onSizeChanged, w=%s, h=%s, oldw=%s, oldh=%s", w, h, oldw, oldh));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        log(String.format("onLayout, changed=%s, l=%s, t=%s, r=%s, b=%s", changed, l, t, r, b));
    }

    /*
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
        if(getChildCount() == 1 && getChildAt(0) instanceof RelativeLayout){
            RelativeLayout rl = (RelativeLayout) getChildAt(0);
            if(rl.getChildCount() == 1){
                View child = rl.getChildAt(0);
                RelativeLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int top = (h - child.getMeasuredHeight())/2;
                int bottom = (h + child.getMeasuredHeight())/2;
                *//*lp.topMargin = top;
                lp.bottomMargin = bottom;

                child.setLayoutParams(lp);*//*
                child.layout(l, top, getMeasuredWidth(), bottom);
            }
        }
        super.onLayout(changed, l, t, r, b);
    }*/

    private void log(String msg) {
        Log.d(TAG, msg);
    }
}
