package com.example.duan1_pro1121.activity.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.user.LichSuNapAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.HistoryBuy;

import java.util.List;

public class HistoryNapTienActivity extends AppCompatActivity {
    private LichSuNapAdapter adapter;
    private RecyclerView recyclerHistoryNaptien;
    private List<HistoryBuy> historyList;
    private ImageView imgBackNaptienActivity;
    int historyNapTien = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_naptien);
        historyList = MyDatabase.getInstance(this).historyBuyDAO().getAll();

        imgBackNaptienActivity = (ImageView) findViewById(R.id.imgBackNaptienActivity);
        recyclerHistoryNaptien = (RecyclerView) findViewById(R.id.recycler_history_naptien);

        imgBackNaptienActivity.setOnClickListener(v -> {
        onBackPressed();
        });


        adapter = new LichSuNapAdapter(historyList,this);
        recyclerHistoryNaptien.setAdapter(adapter);
        recyclerHistoryNaptien.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onBackPressed() {
        if(historyNapTien == -1) {
            super.onBackPressed();
        }
    }
}