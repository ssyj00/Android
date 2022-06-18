package com.ceb.dcpms.android.adapter.check.server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.entity.check.server.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends BaseAdapter<DeviceViewHolder> {

    private List<Device> devices = new ArrayList<>();

    public void add(Device device){
        devices.add(device);
    }

    public void clear(){
        devices.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_checkserver_device, parent, false);
        DeviceViewHolder viewHolder = new DeviceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceViewHolder viewHolder = (DeviceViewHolder) holder;
        Device device = devices.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnItemClickListener() != null){
                    getOnItemClickListener().onItemClick(v, device);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}
