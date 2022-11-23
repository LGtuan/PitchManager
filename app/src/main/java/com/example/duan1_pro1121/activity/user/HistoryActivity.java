package com.example.duan1_pro1121.activity.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.user.HistoryBuyAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.HistoryBuy;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvCount;
    private List<HistoryBuy> list;
    HistoryBuyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = MyDatabase.getInstance(this).historyBuyDAO().getALlWithId(UserMainActivity.customer.getId());
        tvCount = findViewById(R.id.tv_count_history_buy);
        tvCount.setText(list.size()+"");

        recyclerView = findViewById(R.id.recy_history_buy);
        adapter = new HistoryBuyAdapter(this,list);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
    }
}