package com.example.duan1_pro1121.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.ServiceAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.ServiceBall;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DichVuFrgment extends Fragment {

    private List<ServiceBall> serviceList;
    private FloatingActionButton btn;
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceList = MyDatabase.getInstance(getContext()).serviceDAO().getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dich_vu_frgment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_service);
        btn = view.findViewById(R.id.btn_create_dialog_add_service);
        btn.setOnClickListener(v->{
            createDialogAdd();
        });

        adapter = new ServiceAdapter(getContext(),serviceList);
        adapter.setMyOnClick(this::createDialogUpdate);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

    public void createDialogAdd(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_and_update_service);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtName = dialog.findViewById(R.id.edt_name_dialog_add_and_update_service);
        EditText edtMoney = dialog.findViewById(R.id.edt_money_dialog_add_and_update_service);
        RadioButton rdoProduct = dialog.findViewById(R.id.rdo_type_product_dialog_add_and_update_service);
        RadioButton rdoHour = dialog.findViewById(R.id.rdo_type_hour_dialog_add_and_update_service);

        TextView tvCheckName = dialog.findViewById(R.id.tv_check_name_dialog_add_and_update_khachhang);
        TextView tvCheckMoney = dialog.findViewById(R.id.tv_check_money_dialog_add_and_update_khachhang);

        Button btn = dialog.findViewById(R.id.btn_add_and_update_service);
        btn.setOnClickListener(v->{
            String name = edtName.getText().toString();
            String money = edtMoney.getText().toString();

            if(name.length() <= 0){
                invisible(tvCheckMoney,tvCheckName);
                tvCheckName.setVisibility(View.VISIBLE);
            }else{
                ServiceBall serviceBall = new ServiceBall();
                serviceBall.setName(name);
                try{
                    serviceBall.setMoney(Integer.parseInt(money));
                }catch(NumberFormatException e){
                    invisible(tvCheckMoney,tvCheckName);
                    tvCheckMoney.setVisibility(View.VISIBLE);
                    return;
                }
                if(rdoHour.isChecked()){
                    serviceBall.setProduct(false);
                }else{
                    serviceBall.setProduct(true);
                }

                MyDatabase.getInstance(getContext()).serviceDAO().insert(serviceBall);
                serviceList = MyDatabase.getInstance(getContext()).serviceDAO().getAll();
                adapter.setData(serviceList);

                Toast.makeText(getContext(), "Thêm dịch vụ thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void invisible(TextView...tvs){
        for(TextView tv : tvs){
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public void createDialogUpdate(ServiceBall s){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_and_update_service);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}