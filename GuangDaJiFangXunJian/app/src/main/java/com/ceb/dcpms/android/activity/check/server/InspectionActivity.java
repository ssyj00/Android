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
import com.ceb.dcpms.android.activity.photo.PreviewPhotoActivity;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.adapter.check.server.CabinetAdapter;
import com.ceb.dcpms.android.entity.check.server.Cabinet;
import com.ceb.dcpms.android.entity.check.server.MachineRoom;
import com.ceb.dcpms.android.loadmore.LoadMoreWrapper;
import com.ceb.dcpms.android.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InspectionActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_label_total)
    TextView tvLabelTotal;
    @BindView(R.id.tv_cabinet_total)
    TextView tvCabinetTotal;
    @BindView(R.id.tv_number)
    TextView tvNumber;
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
    private CabinetAdapter adapter;

    private MachineRoom room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkserver_inspection);
        unbinder = ButterKnife.bind(this);

        room = (MachineRoom) getIntent().getSerializableExtra(Constants.Tag.data);

        adapter = new CabinetAdapter();
        loadMoreWrapper = new LoadMoreWrapper(adapter);
        rvList.setAdapter(loadMoreWrapper);
        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Cabinet>() {
            @Override
            public void onItemClick(View view, Cabinet cabinet) {
                switch (view.getId()){
                    case R.id.btn_complete:{
                        // 判断当前机房是否还有未完成操作的设备
                        // 没有，给出提示，完成巡检操作
                        DialogUtils.showDialog(InspectionActivity.this, R.string.dialog_message_check_device_complete,
                                R.string.btn_ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                        break;
                    }
                    case R.id.rl_container:{
                        Intent intent = new Intent(getApplicationContext(), DeviceListActivity.class);
                        intent.putExtra(Constants.Tag.data, cabinet);
                        startActivity(intent);
                        break;
                    }
                }
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

    @OnClick({R.id.ll_camera, R.id.ll_map, R.id.ll_unknown, R.id.ll_next_label, R.id.iv_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_camera:{
                break;
            }
            case R.id.ll_map:{
                Intent intent = new Intent(getApplicationContext(), PreviewPhotoActivity.class);
                intent.putExtra(Constants.Tag.data, "https://tenfei04.cfp.cn/creative/vcg/800/version23/VCG41175510742.jpg");
                startActivity(intent);
                break;
            }
            case R.id.ll_unknown:{
                break;
            }
            case R.id.ll_next_label:{
                // 判断当前标签是否还有未完成的设备
                // 有，给出提示
                // 没有，获取下一个标签的数据，并刷新本页
                DialogUtils.showDialog(InspectionActivity.this, R.string.dialog_message_check_device_no_complete,
                        R.string.btn_ok,null);
                break;
            }
            case R.id.iv_back:{
                finish();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    private void doQuery(){
        for(int i=0; i<10; i++){
            adapter.add(new Cabinet());
        }
        hasMore = true;

        mySwipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }
}
