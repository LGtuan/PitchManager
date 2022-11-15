package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.Pitch;

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
       // Order order = MyDatabase.getInstance(context).orderDAO().getOrderWithID(list.get(position).getId()).get(0);

        Manager manager = MyDatabase.getInstance(context).managerDAO().getManagerWithID(list.get(position).getManagerId()).get(0);
        Customer customer = MyDatabase.getInstance(context).customerDAO().getCustomerWithID(list.get(position).getCustomerId()).get(0);
        Pitch pitch = MyDatabase.getInstance(context).pitchDao().getPitchId(list.get(position).getPitchId()).get(0);

        holder.tvTenNhanVien.setText(manager.getName());
        holder.tvTenKhachHang.setText(customer.getName());
        holder.tvTenSanBong.setText(pitch.getName());
        holder.tvMaPhieuThongTin.setText("Phiếu "+ list.get(position).getId());
        holder.tvStartTime.setText(list.get(position).getStartTime());
        holder.tvEndTime.setText(list.get(position).getEndTime());
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvTotal.setText(MyApplication.convertMoneyToString(list.get(position).getTotal()) +"VNĐ");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMaPhieuThongTin;
        private TextView tvTenNhanVien;
        private TextView tvTenKhachHang;
        private TextView tvTenSanBong;
        private TextView tvStartTime;
        private TextView tvEndTime;
        private TextView tvDate;
        private TextView tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuThongTin = (TextView) itemView.findViewById(R.id.tv_ma_phieu_thong_tin);
            tvTenNhanVien = (TextView) itemView.findViewById(R.id.tv_ten_nhan_vien);
            tvTenKhachHang = (TextView) itemView.findViewById(R.id.tv_ten_khach_hang);
            tvTenSanBong = (TextView) itemView.findViewById(R.id.tv_ten_san_bong);
            tvStartTime = (TextView) itemView.findViewById(R.id.tv_start_time);
            tvEndTime = (TextView) itemView.findViewById(R.id.tv_end_time);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvTotal = (TextView) itemView.findViewById(R.id.tv_total);


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
