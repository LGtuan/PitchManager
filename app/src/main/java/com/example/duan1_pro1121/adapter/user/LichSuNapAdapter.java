package com.example.duan1_pro1121.adapter.user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.dao.HistoryBuyDAO;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.HistoryBuy;
import com.example.duan1_pro1121.model.Order;

import java.util.List;

public class LichSuNapAdapter extends RecyclerView.Adapter<LichSuNapAdapter.ViewHolder> {
    private List<HistoryBuy> list;
    private Context context;

    public LichSuNapAdapter(List<HistoryBuy> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LichSuNapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_naptien,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull LichSuNapAdapter.ViewHolder holder, int position) {
        Customer customer = MyDatabase.getInstance(context).customerDAO().getCustomerWithID(list.get(position).getIdCustomer()).get(0);

        holder.tvNameHistoryNaptien.setText(customer.getName());
        holder.tvDateHistoryNaptien.setText(list.get(position).getDate());
        holder.tvMoneyHistoryNaptien.setText(MyApplication.convertMoneyToString(list.get(position).getMoney())+" VND");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDateHistoryNaptien;
        private TextView tvNameHistoryNaptien;
        private TextView tvMoneyHistoryNaptien;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateHistoryNaptien = (TextView) itemView.findViewById(R.id.tv_date_history_naptien);
            tvNameHistoryNaptien = (TextView) itemView.findViewById(R.id.tv_name_history_naptien);
            tvMoneyHistoryNaptien = (TextView) itemView.findViewById(R.id.tv_money_history_naptien);
        }
    }
}
