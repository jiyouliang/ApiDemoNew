package com.example.android.apis.mydemo.drawable;

import android.app.Activity;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.apis.R;

public class CustomerDrawableActivity extends Activity implements View.OnClickListener {

    private TextView mTv;
    private TextView mTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_drawable);
        initView();
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mTv2 = (TextView) findViewById(R.id.tv2);
        ViewGroup.LayoutParams lp = mTv.getLayoutParams();
        mTv.setBackground(new RoundRectDrawable(lp.width, lp.height));

        ViewGroup.LayoutParams lp2 = mTv2.getLayoutParams();
        //selector drawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new PressedRoundRectDrawable(lp2.width, lp2.height));
        stateListDrawable.addState(new int[0], new NormalRoundRectDrawable(lp2.width, lp2.height));
        stateListDrawable.setState(new int[0]);
//        stateListDrawable.setEnterFadeDuration(200);
//        stateListDrawable.setExitFadeDuration(200);
        mTv2.setBackground(stateListDrawable);

        mTv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
