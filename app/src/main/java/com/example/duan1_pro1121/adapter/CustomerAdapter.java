package com.example.duan1_pro1121.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.model.Customer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private List<Customer> list;
    private Context context;
    private MyCustomerOnClick onClick;

    public CustomerAdapter(Context context,List<Customer> list) {
        this.list = list;
        this.context = context;
    }

    public void setData(List<Customer> data){
        list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_khachhang,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv2.setText(list.get(position).getName());
        holder.tv1.setText(list.get(position).getPhone());
        holder.tv3.setText(list.get(position).getAddress());
        holder.tv4.setText(list.get(position).getCmnd());
        holder.tv5.setText(list.get(position).getCoin()+"");
        holder.tv6.setText(list.get(position).getPassword());

        if(list.get(position).getImg() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(position).getImg(), 0, list.get(position).getImg().length);
            holder.img.setImageBitmap(bitmap);
        }else holder.img.setImageResource(R.drawable.user_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1,tv2,tv3,tv4,tv5,tv6;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_phone_item_khachhang);
            tv2 = itemView.findViewById(R.id.tv_name_item_khachhang);
            tv3 = itemView.findViewById(R.id.tv_address_item_khachhang);
            tv4 = itemView.findViewById(R.id.tv_cmnd_item_khachhang);
            tv5 = itemView.findViewById(R.id.tv_coin_item_khachhang);
            tv6 = itemView.findViewById(R.id.tv_password_item_khachhang);
            img = itemView.findViewById(R.id.img_avatar_khachhang_fragment);
            itemView.setOnClickListener(v->{
                onClick.myCustomerOnClick(list.get(getAdapterPosition()));
            });
        }
    }

    public void setOnClick(MyCustomerOnClick onClick) {
        this.onClick = onClick;
    }

    public interface MyCustomerOnClick{
        void myCustomerOnClick(Customer customer);
    }

}
