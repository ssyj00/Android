package com.ceb.dcpms.android.activity.check.server;

import android.app.AlertDialog;
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
import com.ceb.dcpms.android.adapter.check.server.MachineRoomAdapter;
import com.ceb.dcpms.android.entity.check.server.MachineRoom;
import com.ceb.dcpms.android.entity.check.server.Task;
import com.ceb.dcpms.android.loadmore.LoadMoreWrapper;
import com.ceb.dcpms.android.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TaskDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
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
    private MachineRoomAdapter adapter;
    private DialogButtonClickListener dialogButtonClickListener;
    private AlertDialog identificationDialog;

    private Task task;
    private MachineRoom currentMachineRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkserver_taskdetail);
        unbinder = ButterKnife.bind(this);

        task = (Task) getIntent().getSerializableExtra(Constants.Tag.data);
        int type = getIntent().getIntExtra(Constants.Tag.type, Constants.Type.Task.scheduled);
        switch (type){
            case Constants.Type.Task.scheduled:{
                tvType.setText(R.string.text_menu_service_task_1);
                break;
            }
            case Constants.Type.Task.temporary:{
                tvType.setText(R.string.text_menu_service_task_2);
                break;
            }
        }

        dialogButtonClickListener = new DialogButtonClickListener();
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_label_identification_type, null);
        dialogView.findViewById(R.id.btn_nfc).setOnClickListener(dialogButtonClickListener);
        dialogView.findViewById(R.id.btn_qrcode).setOnClickListener(dialogButtonClickListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetailActivity.this);
        builder.setView(dialogView);
        identificationDialog= builder.create();

        adapter = new MachineRoomAdapter();
        loadMoreWrapper = new LoadMoreWrapper(adapter);
        rvList.setAdapter(loadMoreWrapper);
        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<MachineRoom>() {
            @Override
            public void onItemClick(View view, MachineRoom room) {
                currentMachineRoom = room;
                identificationDialog.show();
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

    private void doQuery(){
        for(int i=0; i<5; i++){
            adapter.add(new MachineRoom());
        }
        hasMore = true;

        mySwipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    @OnClick({R.id.iv_back, R.id.ll_complete})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:{
                finish();
                break;
            }
            case R.id.ll_complete:{
                DialogUtils.showDialog(TaskDetailActivity.this,
                        R.string.dialog_message_check_no_complete,
                        R.string.btn_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
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

    private void doInspection(String label){
        Intent intent = new Intent(getApplicationContext(), InspectionActivity.class);
        intent.putExtra(Constants.Tag.data, currentMachineRoom);
        startActivity(intent);
    }

    private class DialogButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_nfc:{
                    identificationDialog.dismiss();
                    doInspection("");
                    break;
                }
                case R.id.btn_qrcode:{
                    identificationDialog.dismiss();
                    doInspection("");
                    break;
                }
            }
        }
    }
}
