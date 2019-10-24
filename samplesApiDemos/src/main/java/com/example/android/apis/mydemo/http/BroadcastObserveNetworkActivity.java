package com.example.android.apis.mydemo.http;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.apis.R;

/**
 * 通过广播判断网络状态
 */
public class BroadcastObserveNetworkActivity extends Activity {
    private static final String TAG = BroadcastObserveNetworkActivity.class.getSimpleName();

    private MyNetBoradcast mNetworkReceiver;
    private TextView mTvNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_observe_network);
        initView();
        registerNetworkBroadcast();
    }

    private void initView() {
        mTvNetwork = (TextView) findViewById(R.id.tv_network);
    }

    private void registerNetworkBroadcast() {
        mNetworkReceiver = new MyNetBoradcast(mTvNetwork);
        IntentFilter intent = new IntentFilter();
        intent.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, intent);

        showNetworkState();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkBroadcast();
    }

    private void unregisterNetworkBroadcast() {
        unregisterReceiver(mNetworkReceiver);
    }


    private class MyNetBoradcast extends BroadcastReceiver {


        private final TextView mTvState;

        public MyNetBoradcast(TextView tvState) {
            this.mTvState = tvState;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            // 网络状态改变
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //判断网络类型、是否可用
                showNetworkState();

            }
        }

    }

    private void showNetworkState() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            mTvNetwork.setBackgroundColor(0x99FF5722);
            mTvNetwork.setText("网络不可用");
            return;
        }

        // 获取网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connMgr.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            mTvNetwork.setBackgroundColor(0x99FF5722);
            mTvNetwork.setText("网络不可用");
            return;
        }


        NetworkInfo wifiInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifiInfo != null && wifiInfo.isAvailable()) {
            mTvNetwork.setBackgroundColor(0xFF22FF73);
            mTvNetwork.setText("WIFI连接");
        }

        if (mobileInfo != null && mobileInfo.isAvailable()) {
            mTvNetwork.setBackgroundColor(0x992226FF);
            mTvNetwork.setText("手机移动网络");
        }
    }
}
