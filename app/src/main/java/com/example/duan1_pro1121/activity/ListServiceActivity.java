package com.example.duan1_pro1121.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.model.ServiceBall;

import java.util.ArrayList;
import java.util.List;

public class ListServiceActivity extends AppCompatActivity {

    private List<ServiceBall> list1 = new ArrayList<>(),list2 = new ArrayList<>();
    private RecyclerView recy1,recy2;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_service);
        initView();
        setup();
    }


    public void initView(){
        img = findViewById(R.id.img_complete_list_service);
        img.setOnClickListener(v->{
            onBackPressed();
        });
        recy1 = findViewById(R.id.recycler_list_service1);
        recy2 = findViewById(R.id.recycler_list_service2);
    }

    public void setup(){
        ServiceAdapter adapter1 = new ServiceAdapter(list1,false);
        ServiceAdapter adapter2 = new ServiceAdapter(list2,true);

        recy1.setAdapter(adapter1);
        recy2.setAdapter(adapter2);
        recy1.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recy2.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    private class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{

        private List<ServiceBall> data;
        private boolean isAdd;

        public ServiceAdapter(List<ServiceBall> data, boolean isAdd) {
            this.data = data;
            this.isAdd = isAdd;
        }

        @NonNull
        @Override
        public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(ListServiceActivity.this).inflate(R.layout.item_recyler_list_service,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
            if(isAdd){
                holder.img.setImageResource(R.drawable.ic_add);
            }else{
                holder.img.setImageResource(R.drawable.ic_cancel);
            }
            holder.tv1.setText(data.get(position).getName());
            holder.tv2.setText(data.get(position).getMoney()+"");
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView img;
            private TextView tv1,tv2;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img_add_cancel_item_list_service);
                tv1 = itemView.findViewById(R.id.tv_name_item_list_service);
                tv2 = itemView.findViewById(R.id.tv_money_item_list_service);
                if(isAdd){
                    img.setOnClickListener(v->{
                        list1.add(list2.get(getAdapterPosition()));
                        list2.remove(getAdapterPosition());
                        setup();
                    });
                }else {
                    img.setOnClickListener(v->{
                        list2.add(list1.get(getAdapterPosition()));
                        list1.remove(getAdapterPosition());
                        setup();
                    });
                }
            }
        }
    }
}