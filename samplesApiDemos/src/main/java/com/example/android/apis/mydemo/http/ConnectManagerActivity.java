package com.example.android.apis.mydemo.http;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.util.NetworkUtils;

/**
 * 判断网络状态
 */
public class ConnectManagerActivity extends Activity {

    private TextView mTvNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_manager);
        initView();
    }

    private void initView() {
        mTvNetwork = (TextView) findViewById(R.id.tv_network);
    }

    public void getNet(View v) {
        boolean netConnected = NetworkUtils.isNetConnected(this);
        boolean wifiConnected = NetworkUtils.isWifiConnected(this);
        int networkState = NetworkUtils.getNetworkState(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("网络是否连接:").append(netConnected)
                .append("\nwifi是否连接:").append(wifiConnected)
                .append("\n网络状态:").append(networkState);

        mTvNetwork.setText(stringBuilder.toString());
    }
}
