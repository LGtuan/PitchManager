package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.ManagerCategory;
import com.example.duan1_pro1121.model.information;

import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private List<information> listInformation;
    private Context context;

    public InformationAdapter(List<information> listInformation, Context context) {
        this.listInformation = listInformation;
        this.context = context;
    }

    @NonNull
    @Override
    public InformationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_information,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull InformationAdapter.ViewHolder holder, int position) {


        holder.tv1.setText(listInformation.get(position).getCustomerId());
        holder.tv2.setText(listInformation.get(position).getPitchId());
        holder.tv3.setText(listInformation.get(position).getStartTime());
        holder.tv4.setText(listInformation.get(position).getEndTime());
        holder.tv5.setText(listInformation.get(position).getDate());
        holder.tv6.setText(listInformation.get(position).getTotal());
        holder.tv7.setText(listInformation.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return listInformation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_custom_item_manager);
            tv2 = itemView.findViewById(R.id.tv_pitch_item_pitch);
            tv3 = itemView.findViewById(R.id.tv_startime_item_manager);
            tv4 = itemView.findViewById(R.id.tv_endtime_item_manager);
            tv5 = itemView.findViewById(R.id.tv_date_item_manager);
            tv6 = itemView.findViewById(R.id.tv_total_item_manager);
            tv7 = itemView.findViewById(R.id.tv_status_item_manager);


        }
    }

}
