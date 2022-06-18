package com.ceb.dcpms.android.adapter.check.server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.entity.check.server.Attribute;

import java.util.ArrayList;
import java.util.List;

public class DeviceAttributeAdapter extends BaseAdapter<DeviceAttributeViewHolder> {

    private List<Attribute> attributes = new ArrayList<>();

    public void add(Attribute attribute){
        attributes.add(attribute);
    }

    public void clear(){
        attributes.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_checkserver_device_attr, parent, false);
        DeviceAttributeViewHolder viewHolder = new DeviceAttributeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceAttributeViewHolder viewHolder = (DeviceAttributeViewHolder) holder;
        Attribute attribute = attributes.get(position);

        viewHolder.rlAttrStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnItemClickListener() != null){
                    getOnItemClickListener().onItemClick(v, attribute);
                }
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
