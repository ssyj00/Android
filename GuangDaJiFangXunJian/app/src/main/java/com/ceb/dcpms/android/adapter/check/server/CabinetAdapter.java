package com.ceb.dcpms.android.adapter.check.server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.entity.check.server.Cabinet;

import java.util.ArrayList;
import java.util.List;

public class CabinetAdapter extends BaseAdapter<CabinetViewHolder> {

    private List<Cabinet> cabinets = new ArrayList<>();

    public void add(Cabinet cabinet){
        cabinets.add(cabinet);
    }

    public void clear(){
        cabinets.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_checkserver_cabinet, parent, false);
        CabinetViewHolder viewHolder = new CabinetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CabinetViewHolder viewHolder = (CabinetViewHolder) holder;
        Cabinet cabinet = cabinets.get(position);

        viewHolder.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnItemClickListener() != null){
                    getOnItemClickListener().onItemClick(v, cabinet);
                }
            }
        });

        viewHolder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnItemClickListener() != null){
                    getOnItemClickListener().onItemClick(v, cabinet);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cabinets.size();
    }
}
