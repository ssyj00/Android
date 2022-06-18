package com.ceb.dcpms.android.adapter.check.server;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_taskid)
    TextView tvTaskId;
    @BindView(R.id.tv_room)
    TextView tvRoom;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
