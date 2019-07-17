package com.example.android.apis.mydemo.vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.apis.R;

/**
 * 绘画板：通过贝塞尔曲线平滑路径
 */
public class PathPaintBoardActivity extends Activity {

    private PainBoardView mPaintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_paint_board);
        initView();
    }

    private void initView() {
        mPaintView = (PainBoardView) findViewById(R.id.paintView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.paint_board_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                mPaintView.resetPath();
                break;
            case R.id.action_red:
                mPaintView.setRedPaint();
                break;
            case R.id.action_yellow:
                mPaintView.setYellowPaint();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
