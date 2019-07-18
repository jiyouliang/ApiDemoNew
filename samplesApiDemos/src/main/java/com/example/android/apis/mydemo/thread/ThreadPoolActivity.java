package com.example.android.apis.mydemo.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.apis.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ThreadPoolActivity extends Activity implements View.OnClickListener {

    private Button mBtn1;
    private Handler mHandler;
    private static final int SUCCESS = 0;
    private static final int ERROR = -1;
    private TextView mTvContent;
    private TextView mTvTitle;
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).build();

    private static final Request request = new Request.Builder()
            .url("http://47.106.182.74:8000/fmap/test/testInterface")//请求的url,10.0.3.2为genymotion访问本地服务器ip
            .get()
            .build();

    private boolean running;
    private static final String TAG = "ThreadPoolActivity";
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        initView();
        mHandler = new MyHandler(mTvContent);
    }

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);

        mTvContent = (TextView) findViewById(R.id.tv);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContent.setText("");
        mTvTitle.setText("任务数:30");

        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn4.setOnClickListener(this);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                threadPoolExecutorDemo();
                break;

            case R.id.btn2:
                newFixedThreadPoolDemo();
                break;

            case R.id.btn3:
                newCachedThreadPoolDemo();
                break;
            case R.id.btn4:
                newSingleThreadPoolDemo();
                break;
            case R.id.btn5:
                newScheduledThreadPoolDemo();
                break;
        }
    }



    /**
     * 通过ThreadPoolExecutor创建基本线程池
     */
    private void threadPoolExecutorDemo() {
        int corePoolSize = 5; //核心线程数量
        int maximumPoolSize = 10;
        long keepAliveTime = 30;//30秒钟，参考自AsyncTask,是非核心线程空闲时要等待下一个任务到来的时间，当任务很多，每个任务执行时间很短的情况下调大该值有助于提高线程利用率。
        TimeUnit unit = TimeUnit.SECONDS;//时间单位：秒
        //阻塞队列
        LinkedBlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<Runnable>(30);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        mTvContent.setText("通过ThreadPoolExecutor创建线程池\n");
        for (int i = 0; i < 30; i++) {
            threadPoolExecutor.execute(runOnThread(i));
        }
    }

    private void newFixedThreadPoolDemo() {
        //可重用固定线程数,newFixedThreadPool方法内部：参数为核心线程数，只有核心线程，无非核心线程，并且阻塞队列无界
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        mTvContent.setText("通过Executors.newFixedThreadPool创建线程池\n");
        for (int i = 0; i < 30; i++) {
            executorService.execute(runOnThread(i));
        }
    }
    private void newCachedThreadPoolDemo() {
        //没有核心线程，只有非核心线程，并且每个非核心线程空闲等待的时间为60s，采用SynchronousQueue队列
        ExecutorService executorService = Executors.newCachedThreadPool();
        mTvContent.setText("通过Executors.newCachedThreadPool创建线程池\n");
        for (int i = 0; i < 30; i++) {//会创建30个线程
            executorService.execute(runOnThread(i));
        }
    }


    private void newSingleThreadPoolDemo() {
        //有一个核心线程，当被占用时，其他的任务需要进入队列等待。
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        mTvContent.setText("通过Executors.newSingleThreadPool创建线程池\n");
        for (int i = 0; i < 30; i++) {
            executorService.execute(runOnThread(i));
        }
    }

    private void newScheduledThreadPoolDemo() {
        ExecutorService executorService = Executors.newScheduledThreadPool(5);
        mTvContent.setText("通过Executors.newScheduledThreadPool创建线程池，定时延时执行\n");
        for (int i = 0; i < 30; i++) {//会创建30个线程
            //延时5秒钟执行
            ((ScheduledExecutorService) executorService).schedule(runOnThread(i), 5, TimeUnit.SECONDS);
        }
    }

    private Runnable runOnThread(final int index) {
        return new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();//线程名
                String threadMsg = "线程" + name + " 正在执行第  " + index + "个任务\n";
                requestHttp(threadMsg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private static class MyHandler extends Handler {

        private TextView mTv;

        public MyHandler(TextView tv) {
            this.mTv = tv;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    String content = (String) msg.obj;
                    mTv.setText(mTv.getText().toString() + content);
                    break;
                case ERROR:
                    Log.e(TAG, String.valueOf(msg.what));
                    break;
            }
        }
    }


    /**
     * 通过OkHttp发送网络请求
     *
     * @param threadMsg
     */
    private void requestHttp(final String threadMsg) {
        Call call = getCall();
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = mHandler.obtainMessage();
                msg.what = ERROR;
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = SUCCESS;
                    msg.obj = threadMsg;
                    mHandler.sendMessage(msg);

                    Log.i(TAG, "服务器返回=" + body.string());
                }
            }
        });
    }

    @NotNull
    private Call getCall() {
        return okHttpClient.newCall(request);
    }
}
