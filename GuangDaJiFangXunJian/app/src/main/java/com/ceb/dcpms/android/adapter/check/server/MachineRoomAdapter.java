package com.ceb.dcpms.android.adapter.check.server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.adapter.BaseAdapter;
import com.ceb.dcpms.android.entity.check.server.MachineRoom;

import java.util.ArrayList;
import java.util.List;

public class MachineRoomAdapter extends BaseAdapter<MachineRoomViewHolder> {

    private List<MachineRoom> rooms = new ArrayList<>();

    public void clear(){
        rooms.clear();
    }

    public void add(MachineRoom room){
        this.rooms.add(room);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_checkserver_machineroom, parent, false);
        MachineRoomViewHolder viewHolder = new MachineRoomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MachineRoomViewHolder viewHolder = (MachineRoomViewHolder) holder;
        MachineRoom room = rooms.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnItemClickListener() != null){
                    getOnItemClickListener().onItemClick(v, room);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
