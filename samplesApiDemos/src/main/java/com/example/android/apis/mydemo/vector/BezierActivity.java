package com.example.android.apis.mydemo.vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.android.apis.R;

/**
 * 贝塞尔曲线
 */
public class BezierActivity extends Activity {


    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bezier_activity);
        initView();
    }

    private void initView() {
        mContainer = (LinearLayout) findViewById(R.id.container);

        showSecondBezier();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bezier_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.second_bezier:
                showSecondBezier();
                break;
            case R.id.third_bezier:
                showThirdBezier();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * 显示二级贝塞尔曲线
     */
    private void showSecondBezier() {
        mContainer.removeAllViews();
        SecondBezierView bezierView = new SecondBezierView(this);
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bezierView.setLayoutParams(lp);
        mContainer.addView(bezierView);
    }

    /**
     * 三阶贝塞尔曲线
     */
    private void showThirdBezier() {
        mContainer.removeAllViews();
        ThindBezierView bezierView = new ThindBezierView(this);
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bezierView.setLayoutParams(lp);
        mContainer.addView(bezierView);
    }
}
