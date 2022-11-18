package com.example.duan1_pro1121.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.ServiceBall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListServiceActivity extends AppCompatActivity {

    private List<ServiceBall> list1 = new ArrayList<>(), list2;
    private List<Integer> listNumber = new ArrayList<>();
    private RecyclerView recy1, recy2;
    private ServiceAdapter adapter;
    private ServiceAdapter2 adapter2;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_service);

        list1 = (List<ServiceBall>) getIntent().getBundleExtra("bundle").getSerializable("LIST_SERVICE");
        listNumber = (List<Integer>) getIntent().getBundleExtra("bundle").getSerializable("LIST_NUMBER");
        list2 = MyDatabase.getInstance(this).serviceDAO().getAll();
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).getId() == list2.get(j).getId()) {
                    list2.remove(j);
                    break;
                }
            }
        }
        initView();
        adapter = new ServiceAdapter(list1, false);
        adapter.setNumbers(listNumber);
        adapter2 = new ServiceAdapter2(list2);

        recy1.setAdapter(adapter);
        recy2.setAdapter(adapter2);

        recy1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recy2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    public void initView() {
        img = findViewById(R.id.img_complete_list_service);
        img.setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("LIST_SERVICE", (Serializable) list1);
            bundle.putSerializable("LIST_NUMBER", (Serializable) listNumber);
            intent.putExtra("bundle", bundle);
            setResult(RESULT_OK, intent);
            finish();
        });
        recy1 = findViewById(R.id.recycler_list_service1);
        recy2 = findViewById(R.id.recycler_list_service2);
    }


    private class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

        private List<ServiceBall> data;
        private List<Integer> numbers;
        private boolean isAdd;

        public ServiceAdapter(List<ServiceBall> data, boolean isAdd) {
            this.data = data;
            this.isAdd = isAdd;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(ListServiceActivity.this).inflate(R.layout.item_recyler_list_service, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.img.setImageResource(R.drawable.ic_cancel);
            holder.tv1.setText(data.get(position).getName());
            holder.tv2.setText(MyApplication.convertMoneyToString(data.get(position).getMoney()) + " VNĐ");
            holder.tv3.setText(numbers.get(position) + "");
            holder.tv4.setText(MyApplication.convertMoneyToString(numbers.get(position) * data.get(position).getMoney()) + " VNĐ");
            if(data.get(position).isProduct()){
                holder.tv5.setText("| 1Product");
            }else holder.tv5.setText("| 2Hour");
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setData(List<ServiceBall> list) {
            data = list;
            notifyDataSetChanged();
        }

        public void setNumbers(List<Integer> list) {
            numbers = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView img, imgPlus, imgMinus;
            private TextView tv1, tv2, tv3, tv4,tv5;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img_add_cancel_item_list_service);
                tv1 = itemView.findViewById(R.id.tv_name_item_list_service);
                tv2 = itemView.findViewById(R.id.tv_money_item_list_service);
                tv3 = itemView.findViewById(R.id.tv_soluong_item_list_service);
                tv4 = itemView.findViewById(R.id.tv_total_money_item_list_service);
                tv5 = itemView.findViewById(R.id.tv_type_item_listservice);
                imgPlus = itemView.findViewById(R.id.img_plus_item_list_service);
                imgMinus = itemView.findViewById(R.id.img_minus_item_list_service);

                imgPlus.setOnClickListener(v -> {
                    listNumber.set(getAdapterPosition(), listNumber.get(getAdapterPosition())+1);
                    adapter.setNumbers(listNumber);
                    adapter.notifyDataSetChanged();
                });
                imgMinus.setOnClickListener(v -> {
                    if (!(listNumber.get(getAdapterPosition()) <= 1)) {
                        listNumber.set(getAdapterPosition(), listNumber.get(getAdapterPosition())-1);
                        adapter.setNumbers(listNumber);
                        adapter.notifyDataSetChanged();
                    }
                });

                img.setOnClickListener(v -> {
                    list2.add(list1.get(getAdapterPosition()));
                    list1.remove(getAdapterPosition());
                    listNumber.remove(getAdapterPosition());

                    adapter.setData(list1);
                    adapter2.setData(list2);
                });
            }
        }
    }

    private class ServiceAdapter2 extends RecyclerView.Adapter<ServiceAdapter2.ViewHolder> {

        private List<ServiceBall> data;

        public ServiceAdapter2(List<ServiceBall> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ServiceAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(ListServiceActivity.this).inflate(R.layout.item_recyler_list_service, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ServiceAdapter2.ViewHolder holder, int position) {
            holder.img.setImageResource(R.drawable.ic_add);
            holder.tv1.setText(data.get(position).getName());
            holder.tv2.setText(MyApplication.convertMoneyToString(data.get(position).getMoney()) + " VNĐ");

            if(data.get(position).isProduct()){
                holder.tv5.setText("| 1Product");
            }else holder.tv5.setText("| 2Hour");
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setData(List<ServiceBall> list) {
            this.data = list;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView img;
            private TextView tv1, tv2,tv5;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img_add_cancel_item_list_service);
                tv1 = itemView.findViewById(R.id.tv_name_item_list_service);
                tv2 = itemView.findViewById(R.id.tv_money_item_list_service);
                tv5 = itemView.findViewById(R.id.tv_type_item_listservice);

                img.setOnClickListener(v -> {
                    list1.add(list2.get(getAdapterPosition()));
                    listNumber.add(1);
                    list2.remove(getAdapterPosition());

                    adapter.setData(list1);
                    adapter2.setData(list2);
                });
            }
        }

    }
}