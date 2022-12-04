package com.example.duan1_pro1121.fragment.adminfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.MainActivity;
import com.example.duan1_pro1121.activity.admin.SalaryDetailsActivityAdmin;
import com.example.duan1_pro1121.activity.admin.SalaryDetailsActivityStaff;
import com.example.duan1_pro1121.activity.user.ChangePassActivity;
import com.example.duan1_pro1121.activity.user.ChangeProfileActivity;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.Order;

import java.util.Calendar;
import java.util.List;

public class ManHinhCaNhanFragment extends Fragment {

    private TextView tvName,tvPhone,tvBankName,tvBankNumber,tvLuong,tvShowInfo,tvChangePass,tvLuongNhanVien;
    private CardView cardView,cardView2;
    private Button btnDetails;

    int totalRecei = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        String date = (calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
        int id = MyDatabase.getInstance(getContext()).managerDAO().getManagerWithPhone(MainActivity.ACCOUNT,-1).get(0).getId();
        List<Order> list = MyDatabase.getInstance(getContext()).orderDAO().getOrderWithManagerId(id,"%-"+date,MyApplication.HUY_STATUS);

        for(int i =0;i<list.size();i++){
            totalRecei += (list.get(i).getTotal()/100)*3;
        }

    }

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
        tvLuongNhanVien = view.findViewById(R.id.tv_show_salary_staff);
        btnDetails = view.findViewById(R.id.btn_details_fragment_canhan);
        cardView = view.findViewById(R.id.cardView_salary_staff);
        cardView2 = view.findViewById(R.id.cardview_luongnv);
        if(MainActivity.ACCOUNT.equals(MyApplication.ADMIN_CATEGORY)){
            cardView.setVisibility(View.GONE);
        }else{
            cardView2.setVisibility(View.GONE);
        }
        tvChangePass.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), ChangePassActivity.class);
            startActivity(intent);
        });
        tvShowInfo.setOnClickListener(v->{

        });
        btnDetails.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), SalaryDetailsActivityStaff.class);
            startActivity(intent);
        });
        tvLuongNhanVien.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), SalaryDetailsActivityAdmin.class);
            startActivity(intent);
        });
    }

    public void setUp(){
        Manager manager = MyDatabase.getInstance(getContext()).managerDAO().getManagerWithPhone(MainActivity.ACCOUNT,-1).get(0);
        tvName.setText(manager.getName());
        tvPhone.setText(manager.getPhone());
        tvBankNumber.setText(manager.getBankNumber());
        tvBankName.setText(manager.getBankName());
        tvLuong.setText(MyApplication.convertMoneyToString(manager.getSalary()) +" + "+ MyApplication.convertMoneyToString(totalRecei));
    }

    @Override
    public void onResume() {
        super.onResume();
        setUp();
    }
}
