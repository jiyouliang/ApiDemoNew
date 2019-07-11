package com.example.android.apis.mydemo.animation;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.apis.R;

public class PropertyAnimActivity extends Activity implements View.OnClickListener {

    private boolean toggle1;
    private Button mBtnTrans;
    private ImageView mImageView;
    private int[] mBtnLoc;
    private int[] mImageViewLoc;
    private float mImageViewLastPosY;
    private float mLastAnimTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_anim);
        initView();
    }

    private void initView() {
        mBtnTrans = (Button) findViewById(R.id.btn_trans);
        mImageView = (ImageView) findViewById(R.id.iv);

        mBtnTrans.setOnClickListener(this);
        mImageView.setOnClickListener(this);


        //ImageView上次位置
        mImageViewLastPosY = mImageView.getTop();
    }

    /**
     * 属性动画
     */
    private void translation() {
        mImageView.clearAnimation();

        float toY = 0;
        float fromY = 0;
        if (!toggle1) {
            //向上移动
            fromY = 0;
            //加上ImageView高度，避免遮挡
            toY = -(mImageView.getY() - mBtnTrans.getY()) - mImageView.getMeasuredHeight();
        } else {
            fromY = 0.0f;
            //向下移动
//            toY = mImageViewLastPosY;//回退到原来位置
            toY = 0;//回退到原来位置
        }
        //属性translationY,  mImageView.setTranslationY(translationY);
        ObjectAnimator anim = ObjectAnimator.ofFloat(mImageView, "translationY", 0, toY);
        anim.setDuration(100);
        anim.start();
        toggle1 = !toggle1;
        mBtnTrans.setText(!toggle1 ? "移动到Button上面":"移动到原来位置");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_trans:
                translation();
                break;
            case R.id.iv:
                Toast.makeText(this, "点击了ImageView", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
