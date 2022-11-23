package com.example.duan1_pro1121.adapter.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.model.MyNotification;

import java.util.List;

public class NotificationAdapterUser extends RecyclerView.Adapter<NotificationAdapterUser.ViewHolder> {

    private Context context;
    private List<MyNotification> list;

    public NotificationAdapterUser(Context context, List<MyNotification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notification_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvContent.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvContent,tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_item_thongbao_user);
            tvContent = itemView.findViewById(R.id.tv_content_item_thongbao_user);
            tvDate = itemView.findViewById(R.id.tv_date_item_thongbao_user);
        }
    }
}
