package com.ceb.dcpms.android.activity.check.server;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ceb.dcpms.android.Constants;
import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.activity.BaseActivity;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.adapter.check.server.DeviceAdapter;
import com.ceb.dcpms.android.entity.check.server.Device;
import com.ceb.dcpms.android.entity.check.server.MachineRoom;
import com.ceb.dcpms.android.loadmore.LoadMoreWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeviceListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private Unbinder unbinder;
    private int pageNumber = 0;
    private boolean hasMore = false;
    private LoadMoreWrapper loadMoreWrapper;
    private DeviceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkserver_devicelist);
        unbinder = ButterKnife.bind(this);

        adapter = new DeviceAdapter();
        loadMoreWrapper = new LoadMoreWrapper(adapter);
        rvList.setAdapter(loadMoreWrapper);
        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Device>() {
            @Override
            public void onItemClick(View view, Device device) {
                Intent intent = new Intent(getApplicationContext(), DeviceDetailActivity.class);
                intent.putExtra(Constants.Tag.data, device);
                startActivity(intent);
            }
        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if(hasMore){
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                        doQuery();
                    }else{
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                }
            }
        });

        mySwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorSwipeRefreshLayout1),
                getResources().getColor(R.color.colorSwipeRefreshLayout2),
                getResources().getColor(R.color.colorSwipeRefreshLayout3)
        );
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 0;
                hasMore = true;
                doQuery();
            }
        });

        mySwipeRefreshLayout.post(()->{
            mySwipeRefreshLayout.setRefreshing(true);
            doQuery();
        });
    }

    @OnClick({R.id.iv_back, R.id.ll_complete})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:{
                finish();
                break;
            }
            case R.id.ll_complete:{
                break;
            }
        }
    }

    private void doQuery(){
        for(int i=0; i<10; i++){
            adapter.add(new Device());
        }
        hasMore = true;

        mySwipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
