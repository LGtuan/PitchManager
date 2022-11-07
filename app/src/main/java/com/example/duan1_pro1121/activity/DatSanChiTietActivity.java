package com.example.duan1_pro1121.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_pro1121.R;

import java.util.Calendar;
import java.util.Date;

public class DatSanChiTietActivity extends AppCompatActivity {

    TextView tv_xemlich,tv_tensan,tv_startTime,tv_endTime,tvCustomer,tvDate,tvService,tvServiceMoney,tvSanBongMoney,tvAllMoney;
    Button btnServiceDetails,btnDatSan;
    RadioButton rdoCoin,rdoTienMat,rdoTTSau;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datsan_chitiet_layout);

        initView();

        tv_xemlich.setOnClickListener(v->{
            Intent intent = new Intent(DatSanChiTietActivity.this, LichHoatDongActivity.class);
            startActivity(intent);
        });
        tv_startTime.setOnClickListener(v->{
            openTimeDialog(tv_startTime);
        });
        tv_endTime.setOnClickListener(v->{
            openTimeDialog(tv_endTime);
        });
        tvCustomer.setOnClickListener(v->{
            Intent intent = new Intent(DatSanChiTietActivity.this,ListCustomerActivity.class);
            startActivity(intent);
        });
        tvDate.setOnClickListener(v->{
            openDateDialog(tvDate);
        });
        btnServiceDetails.setOnClickListener(v->{
            Intent intent = new Intent(DatSanChiTietActivity.this,ListServiceActivity.class);
            startActivity(intent);
        });
    }

    public void initView(){
        tv_xemlich = findViewById(R.id.tv_xemlichdau);
        tv_tensan = findViewById(R.id.tv_name_datsanchitiet);
        tv_startTime = findViewById(R.id.tv_starttime_datsanchitiet);
        tv_endTime = findViewById(R.id.tv_endtime_datsanchitiet);
        tvCustomer = findViewById(R.id.tv_customer_datsanchitiet);
        tvDate = findViewById(R.id.tv_date_datsanchitiet);
        tvService = findViewById(R.id.tv_service_datsanchitiet);
        tvServiceMoney = findViewById(R.id.tv_money_service_datsanchitiet);
        tvSanBongMoney = findViewById(R.id.tv_money_san_datsanchitiet);
        tvAllMoney = findViewById(R.id.tv_tongtien_datsanchitiet);

        btnDatSan = findViewById(R.id.btn_datsan_datsanchitiet);
        btnServiceDetails = findViewById(R.id.btn_chitiet_datsanchitiet);

        rdoCoin = findViewById(R.id.rdo_tt0_datsanchitiet);
        rdoTienMat = findViewById(R.id.rdo_tt1_datsanchitiet);
        rdoTTSau = findViewById(R.id.rdo_tt2_datsanchitiet);
    }

    public void openTimeDialog(TextView tv){
        TimePickerDialog pickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(minute <= 15){
                    tv.setText(hourOfDay+"h");
                }else tv.setText(hourOfDay+"h"+30);
            }
        },0,0,true);

        pickerDialog.setTitle("Select time");
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.setCancelable(false);
        pickerDialog.show();

    }

    public void openDateDialog(TextView tv){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv.setText(getThu(dayOfMonth,month,year));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));

        pickerDialog.setCancelable(false);
        pickerDialog.setTitle("Chose date");
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    public String getThu(int d,int m,int y){
        Calendar calendar = Calendar.getInstance();
        calendar.set(y,m,d);

        String s = d+"/"+m+"/"+y;
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek){
            case 1:
                return "Sun "+s;
            case 2:
                return "Mon "+s;
            case 3:
                return "Tue "+s;
            case 4:
                return "Wed "+s;
            case 5:
                return "Thur "+s;
            case 6:
                return "Fri "+s;
            case 7:
                return "Sat "+s;
            default:
                return "";
        }
    }
}
