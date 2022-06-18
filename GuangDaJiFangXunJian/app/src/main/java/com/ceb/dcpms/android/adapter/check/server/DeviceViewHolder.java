package com.ceb.dcpms.android.adapter.check.server;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_check_type)
    TextView tvCheckType;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_maintenance)
    TextView tvMaintenance;
    @BindView(R.id.tv_check_status)
    TextView tvCheckStatus;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
