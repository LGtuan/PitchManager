package com.example.duan1_pro1121.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.admin.CustomerAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListCustomerActivity extends AppCompatActivity {

    public List<Customer> list;
    RecyclerView recyclerView;
    ImageView img_search;
    EditText edt_search;
    FloatingActionButton btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_khach_hang);

        list = MyDatabase.getInstance(this).customerDAO().getAll();

        initView();
        CustomerAdapter adapter = new CustomerAdapter(this,list);
        adapter.setOnClick(customer -> {
            Intent intent = new Intent();
            intent.putExtra("CUSTOMER",customer);
            setResult(RESULT_OK,intent);
            finish();
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    public void initView(){
        recyclerView = findViewById(R.id.recycler_customer);
        img_search = findViewById(R.id.img_search_customerFragment);
        edt_search = findViewById(R.id.edt_searchName_customerFragment);
        btn_add = findViewById(R.id.btn_create_dialog_add_khachhang);
        btn_add.setVisibility(View.INVISIBLE);
    }
}