package com.ceb.dcpms.android.activity.check.server.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ceb.dcpms.android.Constants;
import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.activity.check.server.TaskDetailActivity;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.adapter.check.server.TaskAdapter;
import com.ceb.dcpms.android.entity.check.server.Task;
import com.ceb.dcpms.android.loadmore.LoadMoreWrapper;
import com.lzy.okgo.utils.OkLogger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TemporaryTaskFragment extends Fragment {

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;

    private Unbinder unbinder;
    private int pageNumber = 0;
    private boolean hasMore = false;
    private TaskAdapter adapter;
    private LoadMoreWrapper loadMoreWrapper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkserver_temporarytask, null);

        unbinder = ButterKnife.bind(this, view);

        adapter = new TaskAdapter();
        loadMoreWrapper = new LoadMoreWrapper(adapter);
        rvList.setAdapter(loadMoreWrapper);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Task>() {
            @Override
            public void onItemClick(View view, Task task) {
                if(task != null){
                    // 进入巡检页面
                    OkLogger.i("11");
                    Intent intent = new Intent(getContext(), TaskDetailActivity.class);
                    intent.putExtra(Constants.Tag.data, task);
                    intent.putExtra(Constants.Tag.type, Constants.Type.Task.temporary);
                    startActivity(intent);
                }else{
                    // 为空，发起任务数据下载操作
                    OkLogger.i("dd");
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

        return view;
    }

    private void doQuery(){
        for(int i=0; i<10; i++){
            adapter.add(new Task());
        }
        hasMore = true;

        mySwipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }
}
