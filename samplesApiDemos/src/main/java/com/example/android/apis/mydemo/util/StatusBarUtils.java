package com.example.android.apis.mydemo.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author YouLiang.Ji
 * 状态栏工具类
 */
public class StatusBarUtils {

    private StatusBarUtils(){}
    private static StatusBarUtils instance;

    public static synchronized StatusBarUtils getInstance(){
        if(instance == null){
            instance = new StatusBarUtils();
        }
        return instance;
    }

    /**
     * 设置5.0以上状态栏
     * @param activity
     * @param titleView
     * @param statusBarColor
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View setStatusBarAboveLOLLIPOP(Activity activity, View titleView, int statusBarColor){
        // 5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //注意要清除 FLAG_TRANSLUCENT_STATUS flag
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(statusBarColor);
        }

        /*
         *
         *6.0以上:系统状态栏的字色和图标颜色为白色，当我的主题色或者图片接近白色或者为浅色的时候，
         * 状态栏上的内容就看不清了,通过如下设置解决该问题,不过设置后后可能出现标题栏和状态栏重合,此时需要参考上面4.4添加view
         */
        View statusView = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            //获取windowphone下的decorView
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            int count = decorView.getChildCount();
            //判断是否已经添加了statusBarView
            if (count > 0 && decorView.getChildAt(count - 1) instanceof TextView) {
                decorView.getChildAt(count - 1).setBackgroundColor(statusBarColor);
            } else {
                //新建一个和状态栏高宽的view
                statusView = createStatusBarView(activity, titleView, statusBarColor);
                decorView.addView(statusView);
            }
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            //rootview不会为状态栏留出状态栏空间
            ViewCompat.setFitsSystemWindows(rootView, true);
            rootView.setClipToPadding(true);
        }
        return statusView;
    }

    /**
     * 绘制一个和状态栏一样高的矩形
     * @param context
     * @param titleView
     * @param statusBarColor
     * @return
     */
    private View createStatusBarView(Context context, View titleView, int statusBarColor) {
        View statusBarView = new View(context);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleView.getMeasuredHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(statusBarColor);
        return statusBarView;
    }

}
