package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.Pitch;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<Order> list;
    private Context context;
    private OrderAdapter.MyOnClick onClick;

    public OrderAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        Customer customer = MyDatabase.getInstance(context).customerDAO().getCustomerWithID(list.get(position).getCustomerId()).get(0);
        Pitch pitch = MyDatabase.getInstance(context).pitchDao().getPitchId(list.get(position).getPitchId()).get(0);

        holder.tvTenKhachHang.setText(customer.getName());
        holder.tvTenSanBong.setText(pitch.getName());
        holder.tvTienSan.setText(MyApplication.convertMoneyToString(list.get(position).getTotalPitchMoney())+" VNĐ");
        holder.tvTienDichVu.setText(MyApplication.convertMoneyToString(list.get(position).getTotal()-list.get(position).getTotalPitchMoney())+" VNĐ");
        holder.tvMaPhieuThongTin.setText("Phiếu "+ list.get(position).getId());
        holder.tvTime.setText(((int)list.get(position).getStartTime())+"h đến "+((int)list.get(position).getEndTime())+"h");
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvTotal.setText(MyApplication.convertMoneyToString(list.get(position).getTotal()) +"VNĐ");
        holder.tvDatePlay.setText("- Ngày "+list.get(position).getDatePlay());

        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarNow = Calendar.getInstance();

        int[] arr = getArrayDate(list.get(position).getDatePlay());
        calendarStart.set(arr[2],arr[1]-1,arr[0], (int) list.get(position).getStartTime(),0);
        calendarEnd.set(arr[2],arr[1]-1,arr[0], (int) list.get(position).getEndTime(),0);


        if(calendarStart.before(calendarNow) && calendarEnd.after(calendarNow)){
            list.get(position).setStatus(MyApplication.DANG_STATUS);
            holder.tvStatus.setText("Đang đá");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.tim));
        }else if(calendarStart.after(calendarNow)){
            list.get(position).setStatus(MyApplication.CHUA_STATUS);
            holder.tvStatus.setText("Chưa đá");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }else if(calendarEnd.before(calendarNow)){
            list.get(position).setStatus(MyApplication.DA_STATUS);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.dark_gray));
            holder.tvStatus.setText("Đã đá xong");
        }

        if(list.get(position).getStatus() == MyApplication.CHUA_STATUS){
            holder.btnHuy.setBackground(AppCompatResources.getDrawable(context,R.drawable.btn_background));
        }else{
            holder.btnHuy.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));
        }
    }

    public int[] getArrayDate(String date){
        String[] str = date.split("-");
        int arr[] = new int[str.length];
        try{
            for(int i = 0;i<str.length;i++){
                arr[i] = Integer.parseInt(str[i]);
            }
        }catch (NumberFormatException e){
            return null;
        }
        return arr;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMaPhieuThongTin;
        private TextView tvTenKhachHang;
        private TextView tvTenSanBong;
        private TextView tvTime;
        private TextView tvDate;
        private TextView tvTotal,tvTienSan,tvTienDichVu,tvStatus;
        private Button btnHuy;
        private TextView tvDatePlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuThongTin = (TextView) itemView.findViewById(R.id.tv_id_item_order);
            tvTenKhachHang = (TextView) itemView.findViewById(R.id.tv_ten_khach_hang_item_order);
            tvTenSanBong = (TextView) itemView.findViewById(R.id.tv_ten_san_bong_item_order);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_item_order);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date_item_order);
            tvTotal = (TextView) itemView.findViewById(R.id.tv_total_item_order);
            tvTienSan = itemView.findViewById(R.id.tv_tiensan_item_order);
            tvTienDichVu = itemView.findViewById(R.id.tv_tiendichvu_item_order);
            btnHuy = itemView.findViewById(R.id.btnhuy_item_order);
            tvStatus = itemView.findViewById(R.id.tv_status_item_order);
            tvDatePlay = itemView.findViewById(R.id.tv_date_play_itemorder);

            btnHuy.setOnClickListener(v->{
                Order order = list.get(getAdapterPosition());
                if(order.getStatus()==MyApplication.CHUA_STATUS){
                    Customer customer = MyDatabase.getInstance(context).customerDAO().getCustomerWithID(order.getCustomerId()).get(0);
                    customer.setCoin(customer.getCoin() + order.getTotal());
                    MyDatabase.getInstance(context).customerDAO().update(customer);

                    MyDatabase.getInstance(context).orderDAO().delete(list.get(getAdapterPosition()));
                    Toast.makeText(context, "Hủy đơn thành công", Toast.LENGTH_SHORT).show();
                    setData(MyDatabase.getInstance(context).orderDAO().getAll());
                }
            });

            itemView.setOnClickListener( v -> {
                onClick.myOnClick(list.get(getAdapterPosition()));
            });
        }
    }

    public void setOnClick(OrderAdapter.MyOnClick onClick) {
        this.onClick = onClick;
    }

    public interface MyOnClick{
        void myOnClick(Order order);
    }

    public void setData(List<Order> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
