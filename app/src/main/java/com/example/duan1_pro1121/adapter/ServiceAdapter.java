package com.example.duan1_pro1121.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.model.ServiceBall;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private Context context;
    private List<ServiceBall> list;
    private MyServiceOnClick myOnClick;

    public ServiceAdapter(Context context, List<ServiceBall> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_service,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        holder.tv1.setText(list.get(position).getName());
        holder.tv2.setText(list.get(position).getMoney()+"");
        if(list.get(position).isProduct()){
            holder.tv3.setText(" | 1Product");
        }else{
            holder.tv2.setText(" | 1hour");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_name_item_service);
            tv2 = itemView.findViewById(R.id.tv_money_item_service);
            tv3 = itemView.findViewById(R.id.tv_type_item_service);
            itemView.setOnClickListener(v->{
                myOnClick.myOnClick(list.get(getAdapterPosition()));
            });
        }
    }

    public void setData(List<ServiceBall> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public interface MyServiceOnClick{
        void myOnClick(ServiceBall service);
    }

    public void setMyOnClick(MyServiceOnClick myOnClick) {
        this.myOnClick = myOnClick;
    }
}
