package com.example.duan1_pro1121.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.CustomerAdapter;
import com.example.duan1_pro1121.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class ListCustomerActivity extends AppCompatActivity {

    public List<Customer> list = new ArrayList<>();
    RecyclerView recyclerView;
    ImageView img_search;
    EditText edt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_khach_hang);

        initView();
        CustomerAdapter adapter = new CustomerAdapter(this,list);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    public void initView(){
        recyclerView = findViewById(R.id.recycler_customer);
        img_search = findViewById(R.id.img_search_activity_customer);
        edt_search = findViewById(R.id.edt_searchNameCustomer_activity_customer);
    }
}