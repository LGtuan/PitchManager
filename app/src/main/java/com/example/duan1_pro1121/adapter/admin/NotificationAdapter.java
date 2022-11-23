package com.example.duan1_pro1121.adapter.admin;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.MyNotification;
import com.example.duan1_pro1121.model.NotificationDetails;

import java.util.Calendar;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private List<MyNotification> list;

    public NotificationAdapter(Context context, List<MyNotification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_thongbao_admin,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvContent.setText(list.get(position).getContent());
        if(list.get(position).getStatus() == MyApplication.HOATDONG_STATUS){
            holder.tvStatus.setText("Đang hoạt động");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }else{
            holder.tvStatus.setText("Đã kết thúc");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<MyNotification> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvContent,tvStatus,tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_item_thongbao_admin);
            tvContent = itemView.findViewById(R.id.tv_content_item_thongbao_admin);
            tvStatus = itemView.findViewById(R.id.tv_status_item_thongbao_admin);
            tvDate = itemView.findViewById(R.id.tv_date_item_thongbao_admin);

            itemView.setOnClickListener(v->{
                createDialogUpdate(list.get(getAdapterPosition()));
            });
        }
    }

    public void createDialogUpdate(MyNotification notification){

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_thongbao);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = dialog.findViewById(R.id.tv_title_dialog_add_notification);
        tvTitle.setText("Cập nhật thông báo");

        EditText edtTitle = dialog.findViewById(R.id.edt_title_dialog_add_thongbao);
        edtTitle.setText(notification.getTitle());
        EditText edtContent = dialog.findViewById(R.id.edt_content_dialog_thongbao);
        edtContent.setText(notification.getContent());
        RadioButton rdoHoatDong = dialog.findViewById(R.id.rdo_hoatdong_dialog_add_thongbao);
        RadioButton rdoKetthuc = dialog.findViewById(R.id.rdo_end_dialog_add_thongbao);
        if(notification.getStatus() == MyApplication.HOATDONG_STATUS){
            rdoHoatDong.setChecked(true);
        }else{
            rdoKetthuc.setChecked(true);
        }
        Button btn = dialog.findViewById(R.id.btn_add_thongbao);
        btn.setText("Cập nhật");

        btn.setOnClickListener(v->{
            if(!edtTitle.getText().toString().equals("") && !edtContent.getText().toString().equals("")){
                if(rdoHoatDong.isChecked())notification.setStatus(MyApplication.HOATDONG_STATUS);
                else notification.setStatus(MyApplication.BAOTRI_STATUS);
                notification.setContent(edtContent.getText().toString());
                notification.setTitle(edtTitle.getText().toString());

                MyDatabase.getInstance(context).notificationDAO().update(notification);
                list = MyDatabase.getInstance(context).notificationDAO().getALl();
                setData(list);

                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }
}
