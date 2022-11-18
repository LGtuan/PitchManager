package com.example.duan1_pro1121.adapter.admin;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.MyTime;

import java.util.List;

public class CaThiDauAdapter extends RecyclerView.Adapter<CaThiDauAdapter.ViewHolder> {

    private Context context;
    private List<MyTime> times;

    public CaThiDauAdapter(Context context, List<MyTime> times) {
        this.context = context;
        this.times = times;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cathidau,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(times.get(position).getName());
        holder.tvTime.setText(times.get(position).getStartTime() + "h - " + times.get(position).getEndTime()+"h");
        holder.tvMoney.setText(MyApplication.convertMoneyToString(times.get(position).getMoney())+"VNĐ");
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public void setData(List<MyTime> list){
        times = list;
        notifyDataSetChanged();
    }

    public void createDialogUpdate(MyTime time){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_loai_nv);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_title = dialog.findViewById(R.id.tv_title_dialog_add_loainv);
        tv_title.setText("Cập nhật chi phí");
        EditText edt = dialog.findViewById(R.id.edt_name_dialog_add_loainv);
        edt.setHint("Nhập số tiền");
        edt.setInputType(InputType.TYPE_CLASS_NUMBER);
        edt.setText(time.getMoney()+"");
        Button btn = dialog.findViewById(R.id.btn_add_loainv);
        btn.setText("Cập nhật");
        TextView tv = dialog.findViewById(R.id.tv_check_name_dialog_add_loainv);
        tv.setText("Số tiền không hợp lệ");

        btn.setOnClickListener(v->{
            try{
                int money = Integer.parseInt(edt.getText().toString());
                time.setMoney(money);
                MyDatabase.getInstance(context).timeDAO().update(time);
                setData(MyDatabase.getInstance(context).timeDAO().getAll());
                dialog.dismiss();
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException e){
                e.printStackTrace();
                tv.setVisibility(View.VISIBLE);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvTime,tvMoney;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_ca_item_cathidau);
            tvTime = itemView.findViewById(R.id.tv_time_item_cathidau);
            tvMoney = itemView.findViewById(R.id.tv_money_item_cathidau);

            itemView.setOnClickListener(v->{
                if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN) {
                    createDialogUpdate(times.get(getAdapterPosition()));
                }
            });
        }
    }
}
