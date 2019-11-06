package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.apis.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.impl.ScrollBoundaryDeciderAdapter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 集成SmartRefreshLayout实现下拉刷新
 */
public class SmartPullRefreshActivity extends Activity {

    private RecyclerView listView;
    private ClassicsFooter footer;
    private SmartRefreshLayout refreshLayout;
    private SmartPullRefreshMessageAdapter adapter;
    private int mPrePosition;
    private int mLastPosition;
    private final int PAGE_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_pull_refresh);
        initView();
        initData();
    }

    private void initView() {
        listView = (RecyclerView) findViewById(R.id.recycleView);
        footer = (ClassicsFooter) findViewById(R.id.footer);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);

        View arrow = footer.findViewById(ClassicsFooter.ID_IMAGE_ARROW);
        arrow.setScaleY(-1);//必须设置



    }

    private void initData() {
        adapter = new SmartPullRefreshMessageAdapter();
        adapter.setDataSource(getTempData());
        listView.setAdapter(adapter);
        listView.setScaleY(-1);//必须设置

        refreshLayout.setEnableRefresh(false);//必须关闭
        refreshLayout.setEnableAutoLoadMore(true);//必须关闭
        refreshLayout.setEnableNestedScroll(false);//必须关闭
        refreshLayout.setEnableScrollContentWhenLoaded(true);//必须关闭
        refreshLayout.getLayout().setScaleY(-1);//必须设置
        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter() {
            @Override
            public boolean canLoadMore(View content) {
                return super.canRefresh(content);//必须替换
            }
        });

        //监听加载，而不是监听 刷新
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.insertToHeader(getTempData());
                        refreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }
        });

    }

    /**
     * 随机生成20条数据
     * @return
     */
    private List<String> getTempData(){
        List<String> data = new ArrayList<>();
        mPrePosition = mLastPosition;
        mLastPosition = mPrePosition + PAGE_SIZE;
        for (int i = mLastPosition - 1; i >= mPrePosition; i--) {
            data.add(String.valueOf(i));
        }
        return data;
    }

}
