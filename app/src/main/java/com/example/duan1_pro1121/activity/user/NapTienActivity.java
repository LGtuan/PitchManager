package com.example.duan1_pro1121.activity.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.HistoryBuy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NapTienActivity extends AppCompatActivity {

    private List<Integer> list = new ArrayList<>(Arrays.asList(100000,200000,300000,500000,1000000,2000000));

    private Button btnNapTien;
    private ImageView imgBack;
    private TextView tvCheck;
    private RecyclerView recyclerView;
    private NapTienAdapter adapter;
    int naptienStatus = -1;
    int customer_id = -1;

    boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_tien);

        customer_id = getIntent().getIntExtra("CUSTOMER_ID",-1);
        isAdmin = getIntent().getBooleanExtra("IS_ADMIN",false);

        btnNapTien = findViewById(R.id.btn_nap_tien);
        tvCheck = findViewById(R.id.tvCheckNapTien);
        imgBack = findViewById(R.id.imgBackNaptienActivity);
        recyclerView = findViewById(R.id.recy_naptien);

        imgBack.setOnClickListener(v->{
            onBackPressed();
        });

        btnNapTien.setOnClickListener(v->{
            if(adapter.getCurrentSelect()==-1){
                tvCheck.setVisibility(View.VISIBLE);
            }else{
                if(customer_id!=-1) {
                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.dialog_loading);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    naptienStatus = 0;


                    new Handler().postDelayed(() -> {
                        HistoryBuy historyBuy = new HistoryBuy();
                        Calendar calendar = Calendar.getInstance();
                        historyBuy.setDate(calendar.get(Calendar.DATE)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR));
                        historyBuy.setIdCustomer(customer_id);
                        historyBuy.setMoney(list.get(adapter.getCurrentSelect()));
                        if(isAdmin){
                            Customer customer = MyDatabase.getInstance(this).customerDAO().getCustomerWithID(customer_id).get(0);
                            customer.setCoin(customer.getCoin() + list.get(adapter.getCurrentSelect()));
                            MyDatabase.getInstance(this).customerDAO().update(customer);
                            historyBuy.setStatus(MyApplication.NAPTIEN_THANHCONG);
                            Toast.makeText(NapTienActivity.this, "Nạp tiền thành công", Toast.LENGTH_SHORT).show();

                        }else{
                            historyBuy.setStatus(MyApplication.NAPTIEN_CHOXACNHAN);
                            Toast.makeText(NapTienActivity.this, "Đang chờ xác nhận", Toast.LENGTH_SHORT).show();
                        }
                        MyDatabase.getInstance(this).historyBuyDAO().insert(historyBuy);
                        progressDialog.dismiss();
                        adapter.setCurrentSelect(-1);
                        naptienStatus = -1;
                        setResult(RESULT_OK);
                        finish();
                    }, 1000);
                }
            }
        });

        adapter = new NapTienAdapter(this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false));
    }

    private class NapTienAdapter extends RecyclerView.Adapter<NapTienAdapter.ViewHolder>{

        private Context context;
        private List<Integer> list;
        int currentSelect = -1;

        public NapTienAdapter(Context context, List<Integer> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public NapTienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recy_naptien,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull NapTienAdapter.ViewHolder holder, int position) {

            holder.tv.setText(MyApplication.convertMoneyToString(list.get(position))+" VNĐ");

            if(currentSelect == position){
                holder.tv.setTextColor(context.getResources().getColor(R.color.my_blue));
                holder.layout.setBackground(AppCompatResources.getDrawable(context,R.drawable.border_item_naptien_2));
                holder.img.setVisibility(View.VISIBLE);
            }else{
                holder.tv.setTextColor(Color.BLACK);
                holder.layout.setBackground(AppCompatResources.getDrawable(context,R.drawable.border_item_naptien));
                holder.img.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setCurrentSelect(int currentSelect){
            this.currentSelect = currentSelect;
            notifyDataSetChanged();
        }

        public int getCurrentSelect() {
            return currentSelect;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView img;
            private TextView tv;
            private ConstraintLayout layout;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                img = itemView.findViewById(R.id.img_select_naptien);
                tv = itemView.findViewById(R.id.tv_mengia_naptien);
                layout = itemView.findViewById(R.id.layout_menhgia_naptien);

                itemView.setOnClickListener(v->{
                    if(currentSelect != getAdapterPosition()){
                        tvCheck.setVisibility(View.INVISIBLE);
                        setCurrentSelect(getAdapterPosition());
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(naptienStatus==-1) {
            super.onBackPressed();
        }
    }
}