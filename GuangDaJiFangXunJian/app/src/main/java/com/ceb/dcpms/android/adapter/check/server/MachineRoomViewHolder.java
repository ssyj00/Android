package com.ceb.dcpms.android.adapter.check.server;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MachineRoomViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.tv_check_position)
    TextView tvCheckPosition;
    @BindView(R.id.tv_check_device)
    TextView tvCheckDevice;
    @BindView(R.id.tv_check_status)
    TextView tvCheckStatus;

    public MachineRoomViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
