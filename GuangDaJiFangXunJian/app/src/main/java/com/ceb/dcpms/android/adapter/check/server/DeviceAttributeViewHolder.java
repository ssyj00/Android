package com.ceb.dcpms.android.adapter.check.server;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceAttributeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_attr_name)
    TextView tvAttrName;
    @BindView(R.id.tv_attr_status)
    TextView tvAttrStatus;
    @BindView(R.id.et_attr_desc)
    EditText etAttrDesc;
    @BindView(R.id.et_attr_fault)
    EditText etAttrFault;
    @BindView(R.id.rl_attr_status)
    RelativeLayout rlAttrStatus;

    public DeviceAttributeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
