package com.ceb.dcpms.android.adapter.check.server;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CabinetViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_check_status)
    TextView tvCheckStatus;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;

    public CabinetViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
