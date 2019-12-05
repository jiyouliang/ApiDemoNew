package com.example.android.apis.mydemo.http;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.apis.R;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 获取网速
 */
public class GetNetSpeedInfoActivity extends Activity implements View.OnClickListener {

    private long rxtxTotal = 0;
    private TextView mTvInfo;
    private int delay;
    private static final String TAG = "GetNetSpeedInfoActivity";
    private Button mBtnDownload;
    private final String URL = "http://1300399567.vod2.myqcloud.com/8b45eaadvodcq1300399567/e21bb5e75285890796034754000/XBcaeURZFQkA.mp3";
    private ProgressBar mProgressBar;
    private DownloadContext mDownloadContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_net_info);
        initView();

        getNetSpeed();
    }


    private void initView() {
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mBtnDownload = (Button) findViewById(R.id.btnDownload);
        mBtnDownload.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setOnClickListener(this);
    }

    private void getNetSpeed() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateUi();
            }
        }, 1000, 1000);


    }

    private void updateUi() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String traffic = getNetSpeed(getApplicationInfo().uid);
                mTvInfo.setText("网速: " + traffic);
                Log.d(TAG, "网速: " + traffic);
            }
        });
    }


    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private String unit;

    public String getNetSpeed(int uid) {
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes());
        unit = "Byte/s";
        if(nowTotalRxBytes >= 1000){
            nowTotalRxBytes = nowTotalRxBytes / 1024;//转KB
            unit = "KB/s";
        }
        if(nowTotalRxBytes >= 1000){
            nowTotalRxBytes = nowTotalRxBytes / 1024;// 转M
            unit = "M/s";
        }
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return String.valueOf(speed) + unit;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnDownload:
                mProgressBar.setProgress(0);
                downloadFile();
                break;
        }
    }


    private void downloadFile() {
        String downloadDir = getCacheDir() + "/" + "download";
        File f = new File(downloadDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        DownloadContext.Builder mDownloadBuilder = new DownloadContext.QueueSet()
                .setParentPath(f.getAbsolutePath())
                .setMinIntervalMillisCallbackProcess(150)
                .commit();

        if (mDownloadBuilder == null) {
            Log.d(TAG, "mDownloadBuilder null");
            return;
        }
        mDownloadBuilder.bind(URL);
        /*mDownloadBuilder.setListener(new DownloadContextListener() {
            @Override
            public void taskEnd(@NonNull DownloadContext context, @NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, int remainCount) {

            }

            @Override
            public void queueEnd(@NonNull DownloadContext context) {

            }
        });*/
        mDownloadContext = mDownloadBuilder.build();
        mDownloadContext.startOnParallel(new DownloadListener1() {
            @Override
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
                showProgressBar();

            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {

            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                mProgressBar.setMax((int) totalLength);
                mProgressBar.setProgress((int) currentOffset);
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
                Toast.makeText(GetNetSpeedInfoActivity.this, "成功", Toast.LENGTH_SHORT).show();
            }
        });

        mDownloadBuilder.build().stop();
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDownloadContext != null){
            mDownloadContext.stop();
        }
    }
}
