package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.HistoryBuy;

import java.util.List;

public class AcceptAdapter extends RecyclerView.Adapter<AcceptAdapter.ViewHolder>{
    private Context context;
    private List<HistoryBuy> list;

    public AcceptAdapter(Context context, List<HistoryBuy> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AcceptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_accept,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptAdapter.ViewHolder holder, int position) {
        holder.tvNameCus.setText(MyDatabase.getInstance(context).customerDAO()
                .getCustomerWithID(list.get(position).getIdCustomer()).get(0).getName());
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvMoney.setText(MyApplication.convertMoneyToString(list.get(position).getMoney())+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameCus,tvDate,tvMoney;
        private Button btnAccept;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCus = itemView.findViewById(R.id.tvNameCustomer_item_accept);
            tvDate = itemView.findViewById(R.id.tv_date_item_accept);
            tvMoney = itemView.findViewById(R.id.tv_money_item_accept);
            btnAccept = itemView.findViewById(R.id.btn_accept_item_accept);

            btnAccept.setOnClickListener(v->{
                HistoryBuy historyBuy = list.get(getAdapterPosition());
                historyBuy.setStatus(MyApplication.NAPTIEN_THANHCONG);
                MyDatabase.getInstance(context).historyBuyDAO().update(historyBuy);
                Customer customer = MyDatabase.getInstance(context).customerDAO().getCustomerWithID(list.get(getAdapterPosition()).getIdCustomer()).get(0);
                customer.setCoin(customer.getCoin()+historyBuy.getMoney());
                MyDatabase.getInstance(context).customerDAO().update(customer);
                setData(MyDatabase.getInstance(context).historyBuyDAO().getAllWithStatus(MyApplication.NAPTIEN_CHOXACNHAN));
            });
        }
    }

    public void setData(List<HistoryBuy> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
