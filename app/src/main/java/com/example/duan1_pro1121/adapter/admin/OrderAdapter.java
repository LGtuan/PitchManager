package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.MyTime;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.Pitch;

import java.util.Calendar;
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
        holder.tvTienDichVu.setText(MyApplication.convertMoneyToString(list.get(position).getTotalServiceMoney())+" VNĐ");
        holder.tvMaPhieuThongTin.setText("Phiếu "+ list.get(position).getId());
        holder.tvDate.setText(list.get(position).getDateCreate());
        holder.tvTotal.setText(MyApplication.convertMoneyToString(list.get(position).getTotal()) +"VNĐ");
        holder.tvDatePlay.setText("Ngày "+list.get(position).getDatePlay());
        holder.tvSoCa.setText(list.get(position).getSoCa()+"");
        holder.tvChiPhi.setText(MyApplication.convertMoneyToString(list.get(position).getChiPhiKhac()) + " VNĐ");

        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarNow = Calendar.getInstance();

        List<MyTime> myTimeList = MyDatabase.getInstance(context)
                .timeDAO().getTimeWithOrderId(list.get(position).getId());

        int beginStatus = list.get(position).getStatus();

        for(int i = 0;i<myTimeList.size();i++){
            int[] arr = getArrayDate(list.get(position).getDatePlay());
            calendarStart.set(arr[2],arr[1]-1,arr[0], myTimeList.get(i).getStartTime(),0);
            calendarEnd.set(arr[2],arr[1]-1,arr[0], myTimeList.get(i).getEndTime(),0);

            if(i == 0 && calendarStart.after(calendarNow)){
                list.get(position).setStatus(MyApplication.CHUA_STATUS);
                holder.tvStatus.setText("Chưa đá");
                break;
            }else if(i == myTimeList.size()-1 && calendarEnd.before(calendarNow)){
                list.get(position).setStatus(MyApplication.DA_STATUS);
                holder.tvStatus.setText("Đã đá");
                break;
            }else{
                if(calendarStart.before(calendarNow) && calendarEnd.after(calendarNow)){
                    list.get(position).setStatus(MyApplication.DANG_STATUS);
                    holder.tvStatus.setText("Đang đá");
                    break;
                }else{
                    list.get(position).setStatus(MyApplication.NGHI_STATUS);
                    holder.tvStatus.setText("Đang nghỉ");
                }
            }
        }

        if(beginStatus != list.get(position).getStatus()){
            MyDatabase.getInstance(context).orderDAO().update(list.get(position));
            list.set(position,MyDatabase.getInstance(context).orderDAO().getOrderWithID(list.get(position).getId()).get(0));
            notifyItemChanged(position);
        }

//        if(list.get(position).getStatus() == MyApplication.CHUA_STATUS){
//            holder.tvStatus.setText("Chưa đá");
//        }else if(list.get(position).getStatus() == MyApplication.DANG_STATUS){
//            holder.tvStatus.setText("Đang đá");
//        }else if(list.get(position).getStatus() == MyApplication.DA_STATUS){
//            holder.tvStatus.setText("Đã đá");
//        }else if(list.get(position).getStatus() == MyApplication.NGHI_STATUS){
//            holder.tvStatus.setText("Đang nghỉ");
//        }

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
        private TextView tvDate;
        private TextView tvSoCa;
        private TextView tvTotal,tvTienSan,tvTienDichVu,tvStatus,tvChiPhi;
        private Button btnHuy;
        private TextView tvDatePlay;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuThongTin =itemView.findViewById(R.id.tv_id_item_order);
            tvTenKhachHang =  itemView.findViewById(R.id.tv_ten_khach_hang_item_order);
            tvTenSanBong = itemView.findViewById(R.id.tv_ten_san_bong_item_order);
            tvDate = itemView.findViewById(R.id.tv_date_item_order);
            tvTotal = itemView.findViewById(R.id.tv_total_item_order);
            tvTienSan = itemView.findViewById(R.id.tv_tiensan_item_order);
            tvTienDichVu = itemView.findViewById(R.id.tv_tiendichvu_item_order);
            btnHuy = itemView.findViewById(R.id.btnhuy_item_order);
            tvStatus = itemView.findViewById(R.id.tv_status_item_order);
            tvDatePlay = itemView.findViewById(R.id.tv_date_play_itemorder);
            tvSoCa = itemView.findViewById(R.id.tv_soca_itemorder);
            tvChiPhi = itemView.findViewById(R.id.tv_chiphi_khac_item_order);
            cardView = itemView.findViewById(R.id.cardView_phieuThongTin);

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
