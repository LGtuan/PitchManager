package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.DatSanChiTietActivity;
import com.example.duan1_pro1121.model.Pitch;

import java.util.List;

public class RecyclerDatSanAdapter extends RecyclerView.Adapter<RecyclerDatSanAdapter.ViewHolder> {
    private Context context;
    private List<Pitch> list;
    private MyOnClickDatSan onClickDatSan;

    public RecyclerDatSanAdapter(Context context, List<Pitch> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerDatSanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recy_datsan,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDatSanAdapter.ViewHolder holder, int position) {
        holder.tv1.setText(list.get(position).getName());
        holder.tv2.setText(list.get(position).getStatus()+"");
        holder.tv3.setText(list.get(position).getId()+"");
        holder.tv4.setText(list.get(position).getCategoryId()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3,tv4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_name_item_datsan);
            tv2 = itemView.findViewById(R.id.tv_status_item_datsan);
            tv3 = itemView.findViewById(R.id.tv_money_item_datsan);
            tv4 = itemView.findViewById(R.id.tv_type_item_datsan);
            itemView.setOnClickListener(v->{
                onClickDatSan.myOnClick();
            });
        }
    }

    public void setOnClickDatSan(MyOnClickDatSan onClickDatSan) {
        this.onClickDatSan = onClickDatSan;
    }

    public interface MyOnClickDatSan{
        void myOnClick();
    }

    public void setData(List<Pitch> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
