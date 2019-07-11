package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.apis.R;

public class BottomSheetActivity extends Activity {
    private static final String TAG = "BottomSheetActivity";

    private RecyclerView mListView;
    private View mBottomSheet;
    private BottomSheetBehavior<View> mBehavior;
    private View mTvMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        initView();
        initData();
    }


    private void initView() {
        mBottomSheet = findViewById(R.id.bottom_sheet);
        mTvMore = findViewById(R.id.tv_more);
        mListView = (RecyclerView) findViewById(R.id.listView);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);

//        listBehaviorHeight = DensityUtil.dpToPx(345);
        final int behaviorHeight = point.y * 4 / 7;//屏幕高度4/7
        mBehavior = BottomSheetBehavior.from(mBottomSheet);
//        mBehavior.setPeekHeight(behaviorHeight);
//        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                 /*   STATE_DRAGGING = 1;
                STATE_SETTLING = 2;
                STATE_EXPANDED = 3;
                STATE_COLLAPSED = 4;
                STATE_HIDDEN = 5;*/
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        log("STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //拖拽
                        log("STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //结束：释放
                        log("STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        log("STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        log("STATE_HIDDEN");
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                int top = bottomSheet.getTop();
                int bottom = bottomSheet.getBottom();
                int[] inWin = new int[2];
                int[] inScreen = new int[2];
                bottomSheet.getLocationInWindow(inWin);
                bottomSheet.getLocationOnScreen(inScreen);
                Log.d(TAG, "LocationInWindow["+inWin[0]+","+inWin[1]+"]"+",  LocationOnScreen["+inScreen[0]+","+inScreen[1]+"]");
            }
        });
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }

    private void initData() {
        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(mManager);

        mListView.setAdapter(new MyAdapter());
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View tv = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_text_layout, parent, false);
            return new MyViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tv.setText("Item " + position);
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    public int dip2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv = (TextView) itemView;
        }
    }

}
