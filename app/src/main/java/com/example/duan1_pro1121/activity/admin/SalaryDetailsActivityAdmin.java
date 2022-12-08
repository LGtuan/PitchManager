package com.example.duan1_pro1121.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.statistical.StaffSalary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SalaryDetailsActivityAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvDate;
    private Button btnPre,btnNext;
    private List<StaffSalary> list;
    private int year;
    private int month;
    SalaryAdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details_admin);

        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH)+1;
        year = calendar.get(Calendar.YEAR);

        initView();
        setData();

        adapter = new SalaryAdminAdapter(this,list);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    public void initView(){
        recyclerView = findViewById(R.id.recy_salary_admin);
        tvDate = findViewById(R.id.tv_date_salary_admin);
        btnPre = findViewById(R.id.btn_pre_salary_admin);
        btnNext = findViewById(R.id.btn_next_salary_admin);

        btnPre.setOnClickListener(v->{
            if(--month<=0){
                year--;
                month =12;
            }
            setData();
            adapter.setData(list);
        });
        btnNext.setOnClickListener(v->{
            if(++month>=13){
                year++;
                month = 1;
            }
            setData();
            adapter.setData(list);
        });
    }

    public void setUp(){
        tvDate.setText(month+"-"+year);
    }

    public void setData(){
        list = new ArrayList<>();
        Cursor cursor = MyDatabase.getInstance(this).orderDAO().getDoanhThuStaff("%-"+month+"-"+year,MyApplication.ADMIN_CATEGORY,MyApplication.HUY_STATUS);
        while (cursor.moveToNext()){
            StaffSalary staffSalary = new StaffSalary(cursor.getInt(0),cursor.getInt(1));
            list.add(staffSalary);
        }
        setUp();
    }

    private class SalaryAdminAdapter extends RecyclerView.Adapter<SalaryAdminAdapter.ViewHolder> {
        private Context context;
        private List<StaffSalary> list;

        public SalaryAdminAdapter(Context context, List<StaffSalary> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public SalaryAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_salary_admin,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull SalaryAdminAdapter.ViewHolder holder, int position) {
            Manager manager = MyDatabase.getInstance(context).managerDAO().getManagerWithID(list.get(position).getId()).get(0);
            holder.tvName.setText(manager.getName());
            holder.tvMoney.setText("+"+ MyApplication.convertMoneyToString(list.get(position).getSalary()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setData(List<StaffSalary> list){
            this.list = list;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvName,tvMoney;
            private Button btn;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvNameNV_item_salary_admin);
                tvMoney = itemView.findViewById(R.id.tv_money_item_salary_admin);
                btn = itemView.findViewById(R.id.btn_details_item_salary_admin);

                btn.setOnClickListener(v->{
                    Intent intent = new Intent(context,SalaryDetailsActivityStaff.class);
                    intent.putExtra("ID_MANAGER",list.get(getAdapterPosition()).getId());
                    intent.putExtra("MONTH",month);
                    intent.putExtra("YEAR",year);
                    context.startActivity(intent);
                });
            }
        }
    }
}