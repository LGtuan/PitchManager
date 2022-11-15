package com.example.duan1_pro1121.activity.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.OrderDetails;
import com.example.duan1_pro1121.model.Pitch;
import com.example.duan1_pro1121.model.PithCategory;
import com.example.duan1_pro1121.model.ServiceBall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatSanChiTietActivity extends AppCompatActivity {

    public static int REQUEST_CODE_CUSTOMER = 1;
    public static int REQUEST_CODE_SERVICE = 2;

    TextView tv_tensan, tv_startTime, tv_endTime, tvCustomer,
            tvDate, tvService, tvServiceMoney, tvSanBongMoney, tvAllMoney, tvMoneyCustomer;
    LinearLayout layoutMoneyCustomer;
    Button btnServiceDetails, btnDatSan;
    ImageView imgBack;
    List<ImageView> listSelect = new ArrayList<>();
    int[] typeSelect = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
    ImageView imgSelect1, imgSelect2, imgSelect3, imgSelect4, imgSelect5, imgSelect6;
    ImageView imgSelect7, imgSelect8, imgSelect9, imgSelect10, imgSelect11, imgSelect12;
    List<MyTime> listTime = new ArrayList<>();

    public class MyTime {
        public MyTime(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
        int startTime;
        int endTime;
    }

    RadioButton rdoCoin, rdoTienMat;

    Pitch pitch;
    PithCategory categoryPitch;
    Customer customer;
    List<ServiceBall> listService = new ArrayList<>();
    List<Integer> numberOfService = new ArrayList<>();

    int currentImgSelect = -1;
    int totalMoneyService;
    int totalMoneyPitch;
    String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datsan_chitiet_layout);

        pitch = (Pitch) getIntent().getSerializableExtra("PITCH");
        categoryPitch = MyDatabase.getInstance(this).pitchCategoryDAO().getCategoryPitchWithId(pitch.getCategoryId()).get(0);

        addData();
        initView();

        setUpService();

        tv_tensan.setText(pitch.getName());

        rdoCoin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (customer == null) {
                    Toast.makeText(this, "Chọn khách hàng để sử dụng tính năng", Toast.LENGTH_SHORT).show();
                    rdoCoin.setChecked(false);
                    new Handler().postDelayed(() -> rdoTienMat.setChecked(true), 500);
                }
            }
        });

        tvCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(DatSanChiTietActivity.this, ListCustomerActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
        });

        tvDate.setOnClickListener(v -> {
            openDateDialog(tvDate);
        });

        btnServiceDetails.setOnClickListener(v -> {
            Intent intent = new Intent(DatSanChiTietActivity.this, ListServiceActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("LIST_SERVICE", (Serializable) listService);
            bundle.putSerializable("LIST_NUMBER", (Serializable) numberOfService);
            intent.putExtra("bundle", bundle);
            startActivityForResult(intent, REQUEST_CODE_SERVICE);
        });

        btnDatSan.setOnClickListener(v -> {
            if(currentImgSelect!=-1) {
                datSan();
            }else{
                Toast.makeText(this, "Bạn chưa chọn thời gian thi đấu", Toast.LENGTH_SHORT).show();
            }
        });

        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

        setUpService();
    }

    public void datSan() {
        Order order = new Order();
        order.setId(++MainActivity.ID_MAX_ORDER);
        if (customer != null) {
            order.setCustomerId(customer.getId());
        }else{
            Toast.makeText(this, "Vui lòng chọn khách hàng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rdoCoin.isChecked()) {
            int total = totalMoneyPitch + totalMoneyService;
            if (customer.getCoin() < total) {
                Toast.makeText(this, "Tiền tài khoản khách hàng không đủ", Toast.LENGTH_SHORT).show();
                return;
            } else {
                customer.setCoin(customer.getCoin() - total);
                MyDatabase.getInstance(this).customerDAO().update(customer);
                order.setStatus(0);
            }
        } else if (rdoTienMat.isChecked()) {
            order.setStatus(1);
        }
        order.setDate(date);
        order.setPitchId(pitch.getId());
        order.setManagerId(MyDatabase.getInstance(this).managerDAO().getManagerWithPhone(MainActivity.ACCOUNT, -1).get(0).getId());
        order.setEndTime(listTime.get(currentImgSelect).endTime);
        order.setStartTime(listTime.get(currentImgSelect).startTime);
        order.setTotal(totalMoneyPitch + totalMoneyService);
        order.setTotalPitchMoney(totalMoneyPitch);

        MyDatabase.getInstance(this).orderDAO().insert(order);

        for(int i = 0;i<listService.size();i++){
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setServiceId(listService.get(i).getId());
            Log.e("123","ID_Service" + listService.get(i).getId());
            orderDetails.setOrderId(order.getId());
            orderDetails.setSoLuong(numberOfService.get(i));
            Log.e("123","Id_order" + order.getId());
            orderDetails.setTongTien(listService.get(i).getMoney() * numberOfService.get(i));

            MyDatabase.getInstance(this).orderDetailsDAO().insert(orderDetails);
        }

        Toast.makeText(this, "Đặt sân thành công", Toast.LENGTH_SHORT).show();
        resetData();
        showLichHoatDong();
    }

    public void resetData(){
        currentImgSelect = -1;
        tv_startTime.setText("0h");
        tv_endTime.setText("0h");
        listService = new ArrayList<>();
        numberOfService = new ArrayList<>();
        setUpService();
        tvMoneyCustomer.setText(MyApplication.convertMoneyToString(customer.getCoin())+" VNĐ");
        rdoTienMat.setChecked(true);
    }

    public void showLichHoatDong() {
        resetTypeSelect();
        List<Order> orders = MyDatabase.getInstance(this).orderDAO().getOrderWithPitchAndDate(pitch.getId(), date);
        for (int i = 0; i < orders.size(); i++) {
            float orderStart = orders.get(i).getStartTime();
            int pos = (int) (orderStart/2);
            typeSelect[pos] = 1;
        }

        Calendar calendar = Calendar.getInstance();
        String s = getStringDate(calendar.get(Calendar.DATE),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR));
        if(s.equals(date)) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            for (int i = 0; i < 12; i++) {
                if (hour > i * 2) {
                    typeSelect[i] = 2;
                }
            }
        }
        setUpImageSelect();
    }

    public void resetTypeSelect(){
        Arrays.fill(typeSelect, 0);
    }

    public void setUpImageSelect(){
        for(int i = 0;i<listSelect.size();i++){
            if(typeSelect[i] == 0){
                listSelect.get(i).setImageResource(R.drawable.ic_add);
            }else if(typeSelect[i] == 2){
                listSelect.get(i).setImageResource(R.drawable.ic_add_gray);
            }else{
                listSelect.get(i).setImageResource(R.drawable.soldout_png);
            }
        }
    }

    public void setUpTvDate() {
        Calendar calendar = Calendar.getInstance();
        tvDate.setText(getThu(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
    }

    public String getStringDate(int d, int m, int y) {
        return d + "-" + m + "-" + y;
    }

    public void changeColorTVMoneyCustomer() {
        if (customer != null) {
            layoutMoneyCustomer.setVisibility(View.VISIBLE);
            if (customer.getCoin() >= (totalMoneyService + totalMoneyPitch))
                tvMoneyCustomer.setTextColor(Color.parseColor("#23C82A"));
            else tvMoneyCustomer.setTextColor(Color.parseColor("#FF1100"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_CUSTOMER == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                customer = (Customer) data.getSerializableExtra("CUSTOMER");
                tvCustomer.setText(customer.getName());
                changeColorTVMoneyCustomer();
                tvMoneyCustomer.setText(MyApplication.convertMoneyToString(customer.getCoin()) + " VNĐ");
            }
        } else if (REQUEST_CODE_SERVICE == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                listService = (List<ServiceBall>) data.getBundleExtra("bundle").getSerializable("LIST_SERVICE");
                numberOfService = (List<Integer>) data.getBundleExtra("bundle").getSerializable("LIST_NUMBER");
                setUpService();
            }
        }
    }

    public String getThu(int d, int m, int y) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, d);

        String s = "";
        date = getStringDate(d, m, y);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        showLichHoatDong();

        switch (dayOfWeek) {
            case 1:
                s = "Sun " + date;
                break;
            case 2:
                s = "Mon " + date;
                break;
            case 3:
                s = "Tue " + date;
                break;
            case 4:
                s = "Wed " + date;
                break;
            case 5:
                s = "Thur " + date;
                break;
            case 6:
                s = "Fri " + date;
                break;
            case 7:
                s = "Sat " + date;
        }
        return s;
    }

    public void setUpService() {
        tvService.setText(listService.size() + " trong tổng số " + MyDatabase.getInstance(this).serviceDAO().getAll().size());

        totalMoneyService = 0;
        for (int i = 0; i < listService.size(); i++) {
            totalMoneyService += listService.get(i).getMoney() * numberOfService.get(i);
        }

        tvServiceMoney.setText(MyApplication.convertMoneyToString(totalMoneyService) + " VNĐ");
        setUpTotalMoney();
    }

    public void setUpTotalMoney() {
        tvAllMoney.setText(MyApplication.convertMoneyToString(totalMoneyPitch + totalMoneyService) + " VNĐ");
        changeColorTVMoneyCustomer();
    }

    public void initView() {
        tv_tensan = findViewById(R.id.tv_name_datsanchitiet);
        tv_startTime = findViewById(R.id.tv_starttime_datsanchitiet);
        tv_endTime = findViewById(R.id.tv_endtime_datsanchitiet);
        tvCustomer = findViewById(R.id.tv_customer_datsanchitiet);

        tvDate = findViewById(R.id.tv_date_datsanchitiet);
        setUpTvDate();

        tvService = findViewById(R.id.tv_service_datsanchitiet);
        tvServiceMoney = findViewById(R.id.tv_money_service_datsanchitiet);
        tvSanBongMoney = findViewById(R.id.tv_money_san_datsanchitiet);
        tvAllMoney = findViewById(R.id.tv_tongtien_datsanchitiet);
        totalMoneyPitch = categoryPitch.getMoney()*2;
        setUpTotalMoney();
        tvSanBongMoney.setText(MyApplication.convertMoneyToString(totalMoneyPitch)+" VNĐ");

        btnDatSan = findViewById(R.id.btn_datsan_datsanchitiet);
        btnServiceDetails = findViewById(R.id.btn_chitiet_datsanchitiet);
        imgBack = findViewById(R.id.btn_back_datsanchitiet);

        rdoCoin = findViewById(R.id.rdo_tt0_datsanchitiet);
        rdoTienMat = findViewById(R.id.rdo_tt1_datsanchitiet);

        layoutMoneyCustomer = findViewById(R.id.layoutMoneyCustomer);
        tvMoneyCustomer = findViewById(R.id.tv_money_customer_datsanchitiet);

    }

    public void addData(){
        imgSelect1 = findViewById(R.id.img_select_san1);
        listSelect.add(imgSelect1);
        imgSelect2 = findViewById(R.id.img_select_san2);
        listSelect.add(imgSelect2);
        imgSelect3 = findViewById(R.id.img_select_san3);
        listSelect.add(imgSelect3);
        imgSelect4 = findViewById(R.id.img_select_san4);
        listSelect.add(imgSelect4);
        imgSelect5 = findViewById(R.id.img_select_san5);
        listSelect.add(imgSelect5);
        imgSelect6 = findViewById(R.id.img_select_san6);
        listSelect.add(imgSelect6);
        imgSelect7 = findViewById(R.id.img_select_san7);
        listSelect.add(imgSelect7);
        imgSelect8 = findViewById(R.id.img_select_san8);
        listSelect.add(imgSelect8);
        imgSelect9 = findViewById(R.id.img_select_san9);
        listSelect.add(imgSelect9);
        imgSelect10 = findViewById(R.id.img_select_san10);
        listSelect.add(imgSelect10);
        imgSelect11 = findViewById(R.id.img_select_san11);
        listSelect.add(imgSelect11);
        imgSelect12 = findViewById(R.id.img_select_san12);
        listSelect.add(imgSelect12);

        for (int i = 0; i < 12; i++) {
            listTime.add(new MyTime(i * 2, (i + 1) * 2));
        }

        for (int i = 0; i < listSelect.size(); i++) {
            int finalI = i;
            listSelect.get(i).setOnClickListener(v -> {
                if(typeSelect[finalI] == 0) {
                    if (currentImgSelect != finalI) {
                        if (currentImgSelect != -1) notifyImgSelectAtPos(currentImgSelect);
                        tv_startTime.setText(listTime.get(finalI).startTime + "h");
                        tv_endTime.setText(listTime.get(finalI).endTime + "h");
                        listSelect.get(finalI).setImageResource(R.drawable.ic_cancel);
                        currentImgSelect = finalI;
                    } else {
                        listSelect.get(finalI).setImageResource(R.drawable.ic_add);
                        currentImgSelect = -1;
                        tv_startTime.setText("0h");
                        tv_endTime.setText("0h");
                    }
                }else if (typeSelect[finalI] == 1){
                    Toast.makeText(this, "Lịch đã được khách hàng khác đặt", Toast.LENGTH_SHORT).show();
                }else if(typeSelect[finalI] == 2){
                    Toast.makeText(this, "Đã quá thời gian", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void notifyImgSelectAtPos(int pos) {
        listSelect.get(pos).setImageResource(R.drawable.ic_add);
    }

//    public void openTimeDialog(TextView tv, int code) {
//        Calendar calendar = Calendar.getInstance();
//        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
//        int mMinute = calendar.get(Calendar.MINUTE);
//        TimePickerDialog pickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                if (minute <= 15) {
//                    tv.setText(hourOfDay + "h");
//                    if (code == FROM_CODE) {
//                        fromHour = hourOfDay;
//                    } else if (code == TO_CODE) {
//                        toHour = hourOfDay;
//                    }
//                } else {
//                    tv.setText(hourOfDay + "h" + 30);
//                    if (code == FROM_CODE) {
//                        fromHour = (float) (hourOfDay + 0.5);
//                    } else if (code == TO_CODE) {
//                        toHour = (float) (hourOfDay + 0.5);
//                    }
//                }
//                setUpMoneyPitch();
//            }
//        }, mHour, mMinute, true);
//
//        pickerDialog.setTitle("Select time");
//        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        pickerDialog.setCancelable(false);
//        pickerDialog.show();
//    }

    public void openDateDialog(TextView tv) {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDate = calendar.get(Calendar.DATE);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (year < mYear) {
                    Toast.makeText(DatSanChiTietActivity.this, "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
                } else if (year == mYear) {
                    if (month < mMonth) {
                        Toast.makeText(DatSanChiTietActivity.this, "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
                    } else if (month == mMonth) {
                        if (dayOfMonth < mDate) {
                            Toast.makeText(DatSanChiTietActivity.this, "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
                        } else {
                            tv.setText(getThu(dayOfMonth, month + 1, year));
                        }
                    } else {
                        tv.setText(getThu(dayOfMonth, month + 1, year));
                    }
                } else {
                    tv.setText(getThu(dayOfMonth, month + 1, year));
                }
            }
        }, mYear, mMonth, mDate);

        pickerDialog.setCancelable(false);
        pickerDialog.setTitle("Chose date");
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }
}
