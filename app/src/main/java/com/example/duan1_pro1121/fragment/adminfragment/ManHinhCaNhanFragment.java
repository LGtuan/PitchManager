package com.example.duan1_pro1121.fragment.adminfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.MainActivity;
import com.example.duan1_pro1121.activity.user.ChangePassActivity;
import com.example.duan1_pro1121.activity.user.ChangeProfileActivity;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Manager;

public class ManHinhCaNhanFragment extends Fragment {

    private TextView tvName,tvPhone,tvBankName,tvBankNumber,tvLuong,tvShowInfo,tvChangePass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_man_hinh_ca_nhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tv_nameAdmin);
        tvPhone = view.findViewById(R.id.phone_admin);
        tvShowInfo = view.findViewById(R.id.tv_show_info_profile_admin);
        tvBankName = view.findViewById(R.id.bankNameAdmin);
        tvBankNumber = view.findViewById(R.id.bankNumberAdmin);
        tvLuong = view.findViewById(R.id.tv_salary_admin);
        tvChangePass = view.findViewById(R.id.tv_change_pass_admin);
        tvChangePass.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), ChangePassActivity.class);
            startActivity(intent);
        });
        tvShowInfo.setOnClickListener(v->{

        });
    }

    public void setUp(){
        Manager manager = MyDatabase.getInstance(getContext()).managerDAO().getManagerWithPhone(MainActivity.ACCOUNT,-1).get(0);
        tvName.setText(manager.getName());
        tvPhone.setText(manager.getPhone());
        tvBankNumber.setText(manager.getBankNumber());
        tvBankName.setText(manager.getBankName());
        tvLuong.setText(MyApplication.convertMoneyToString(manager.getSalary()));
    }

    @Override
    public void onResume() {
        super.onResume();
        setUp();
    }
}
