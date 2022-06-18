package com.ceb.dcpms.android.adapter.check.server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.entity.check.server.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter<TaskViewHolder> {

    private List<Task> tasks = new ArrayList<>();

    public void add(Task task){
        tasks.add(task);
    }

    public void clear(){
        tasks.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_checkserver_task, parent, false);
        TaskViewHolder viewHolder = new TaskViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TaskViewHolder viewHolder = (TaskViewHolder) holder;

        Task task = tasks.get(position);
        viewHolder.tvTaskId.setText("abcdef");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnItemClickListener() != null){
                    getOnItemClickListener().onItemClick(v, task);
                }
            }
        });

        viewHolder.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnItemClickListener() != null){
                    getOnItemClickListener().onItemClick(v, null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
