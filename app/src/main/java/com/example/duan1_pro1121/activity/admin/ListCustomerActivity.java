package com.example.duan1_pro1121.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.user.NapTienActivity;
import com.example.duan1_pro1121.adapter.admin.CustomerAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.fragment.adminfragment.KhachHangFragment;
import com.example.duan1_pro1121.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListCustomerActivity extends AppCompatActivity {

    public List<Customer> list;
    RecyclerView recyclerView;
    EditText edtFind;
    ImageView imgFind;
    FloatingActionButton btn_add;
    CustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_khach_hang);

        list = MyDatabase.getInstance(this).customerDAO().getAll();

        initView();
        adapter = new CustomerAdapter(this, list);
        adapter.setOnClick(customer -> {
            Intent intent = new Intent();
            intent.putExtra("CUSTOMER", customer);
            setResult(RESULT_OK, intent);
            finish();
        });
        adapter.setNapTienOnClick(id->{
            Intent intent = new Intent(this, NapTienActivity.class);
            intent.putExtra("CUSTOMER_ID",id);
            startActivityForResult(intent, KhachHangFragment.NAPTIEN_CODE);
        });
        recyclerView.setAdapter(adapter);

        imgFind.setOnClickListener(v -> {
            String name = edtFind.getText().toString();
            if (name.equals("")) {
                adapter.setData(list);
            } else {
                adapter.setData(MyDatabase.getInstance(this).customerDAO().getCustomerWithName("%" + name + "%"));
            }
        });

        btn_add.setOnClickListener(v -> {
            createDialogAdd();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    public void initView() {
        recyclerView = findViewById(R.id.recycler_customer);
        btn_add = findViewById(R.id.btn_create_dialog_add_khachhang);
        edtFind = findViewById(R.id.edt_searchName_customerFragment);
        imgFind = findViewById(R.id.img_search_customerFragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == KhachHangFragment.NAPTIEN_CODE && resultCode == AppCompatActivity.RESULT_OK){
            if(!edtFind.getText().toString().equals("")) {
                adapter.setData(MyDatabase.getInstance(this).customerDAO().getCustomerWithName("%"+edtFind.getText().toString()+"%"));
            }else{
                adapter.setData(MyDatabase.getInstance(this).customerDAO().getAll());
            }
        }
    }

    public void createDialogAdd() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_khachhang);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn = dialog.findViewById(R.id.btn_add_khachhang);
        EditText edtPhone = dialog.findViewById(R.id.edt_phone_dialog_add_khachhang);
        EditText edtName = dialog.findViewById(R.id.edt_name_dialog_add_khachhang);
        EditText edtPass = dialog.findViewById(R.id.edt_pass_dialog_add_khachhang);
        EditText edtPass2 = dialog.findViewById(R.id.edt_pass2_dialog_add_khachhang);
        EditText edtCmnd = dialog.findViewById(R.id.edt_cmnd_dialog_add_khachhang);
        EditText edtAddress = dialog.findViewById(R.id.edt_address_dialog_add_khachhang);

        TextView tvCheckPhone = dialog.findViewById(R.id.tv_check_phone_dialog_add_khachhang);
        TextView tvCheckName = dialog.findViewById(R.id.tv_check_name_dialog_add_khachhang);
        TextView tvCheckAddress = dialog.findViewById(R.id.tv_check_address_dialog_add_khachhang);
        TextView tvCheckPass = dialog.findViewById(R.id.tv_check_pass_dialog_add_khachhang);
        TextView tvCheckPass2 = dialog.findViewById(R.id.tv_check_pass2_dialog_add_khachhang);

        btn.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString();
            String name = edtName.getText().toString();
            String pass = edtPass.getText().toString();
            String pass2 = edtPass2.getText().toString();
            String cmnd = edtCmnd.getText().toString();
            String address = edtAddress.getText().toString();

            if (!phone.matches(MyApplication.PHONE_REGEX)) {
                tvCheckPhone.setText("* Số điện thoại không hợp lệ");
                invisible(tvCheckAddress, tvCheckName, tvCheckPass, tvCheckPhone, tvCheckPass2);
                tvCheckPhone.setVisibility(View.VISIBLE);
            } else if (!name.matches(MyApplication.NAME_REGEX)) {
                invisible(tvCheckAddress, tvCheckName, tvCheckPass, tvCheckPhone, tvCheckPass2);
                tvCheckName.setVisibility(View.VISIBLE);
            } else if (!address.matches(MyApplication.ADDRESS_REGEX)) {
                invisible(tvCheckAddress, tvCheckName, tvCheckPass, tvCheckPhone, tvCheckPass2);
                tvCheckAddress.setVisibility(View.VISIBLE);
            } else if (!pass.matches(MyApplication.PASS_REGEX)) {
                invisible(tvCheckAddress, tvCheckName, tvCheckPass, tvCheckPhone, tvCheckPass2);
                tvCheckPass.setVisibility(View.VISIBLE);
            } else if (!pass.equals(pass2)) {
                invisible(tvCheckAddress, tvCheckName, tvCheckPass, tvCheckPhone, tvCheckPass2);
                tvCheckPass2.setVisibility(View.VISIBLE);
            } else {
                Customer customer = new Customer();
                if (MyDatabase.getInstance(this).customerDAO().getCustomerWithPhone(phone, -1).size() == 0) {
                    customer.setPhone(phone);
                    customer.setName(name);
                    customer.setPassword(pass);
                    customer.setCmnd(cmnd);
                    customer.setAddress(address);

                    MyDatabase.getInstance(this).customerDAO().insert(customer);
                    Toast.makeText(this, "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                    list = MyDatabase.getInstance(this).customerDAO().getAll();
                    adapter.setData(list);

                    dialog.dismiss();
                } else {
                    tvCheckPhone.setText("* Số điện thoại đã tồn tại");
                    invisible(tvCheckAddress, tvCheckName, tvCheckPhone, tvCheckPass, tvCheckPass2);
                    tvCheckPhone.setVisibility(View.VISIBLE);
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void invisible(TextView... tvs) {
        for (TextView tv : tvs) {
            tv.setVisibility(View.INVISIBLE);
        }
    }
}