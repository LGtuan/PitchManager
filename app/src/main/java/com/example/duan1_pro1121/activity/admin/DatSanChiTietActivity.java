package com.example.duan1_pro1121.activity.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.ManagerCategory;
import com.example.duan1_pro1121.model.Pitch;
import com.example.duan1_pro1121.model.ServiceBall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatSanChiTietActivity extends AppCompatActivity {

    public static int REQUEST_CODE_CUSTOMER = 1;
    public static int REQUEST_CODE_SERVICE = 2;

    TextView tv_xemlich, tv_tensan, tv_startTime, tv_endTime, tvCustomer,
            tvDate, tvService, tvServiceMoney, tvSanBongMoney, tvAllMoney,
            tv_checkTime;
    Button btnServiceDetails, btnDatSan;
    ImageView imgBack;
    RadioButton rdoCoin, rdoTienMat, rdoTTSau;

    Pitch pitch;
    Customer customer;
    List<ServiceBall> listService = new ArrayList<>();
    List<Integer> numberOfService = new ArrayList<>();

    int totalMoneyService;
    int totalMoneyPitch = 0;

    float fromHour = 0;
    float toHour = 0;
    int FROM_CODE = 0;
    int TO_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datsan_chitiet_layout);
        //createDataCustommer();
        //createDataCategoriesStaff();
        //createDataStaff();

        pitch = (Pitch) getIntent().getSerializableExtra("PITCH");

        initView();
        tv_tensan.setText(pitch.getName());

        tv_xemlich.setOnClickListener(v -> {
            Intent intent = new Intent(DatSanChiTietActivity.this, LichHoatDongActivity.class);
            startActivity(intent);
        });
        tv_startTime.setOnClickListener(v -> {
            openTimeDialog(tv_startTime,FROM_CODE);
        });
        tv_endTime.setOnClickListener(v -> {
            openTimeDialog(tv_endTime,TO_CODE);
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
            if(toHour - fromHour < 1){
                tv_checkTime.setText("* Trận đấu phải có thời gian hơn 1 giờ");
                tv_checkTime.setVisibility(View.VISIBLE);
            }else if(toHour - fromHour > 3){
                tv_checkTime.setText("* Thời gian trận đấu quá 3 giờ");
                tv_checkTime.setVisibility(View.VISIBLE);
            }else{
                tv_checkTime.setText("* Thời gian trận đấu quá 3 giờ");
                tv_checkTime.setVisibility(View.INVISIBLE);
            }
        });
        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

        setUpService();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_CUSTOMER == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                customer = (Customer) data.getSerializableExtra("CUSTOMER");
                tvCustomer.setText(customer.getName());
            }
        } else if (REQUEST_CODE_SERVICE == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                listService = (List<ServiceBall>) data.getBundleExtra("bundle").getSerializable("LIST_SERVICE");
                numberOfService = (List<Integer>) data.getBundleExtra("bundle").getSerializable("LIST_NUMBER");
                setUpService();
            }
        }
    }

    public void initView() {
        tv_xemlich = findViewById(R.id.tv_xemlichdau);
        tv_tensan = findViewById(R.id.tv_name_datsanchitiet);
        tv_startTime = findViewById(R.id.tv_starttime_datsanchitiet);
        tv_endTime = findViewById(R.id.tv_endtime_datsanchitiet);
        tvCustomer = findViewById(R.id.tv_customer_datsanchitiet);
        tvDate = findViewById(R.id.tv_date_datsanchitiet);
        Calendar calendar = Calendar.getInstance();
        tvDate.setText(getThu(calendar.get(Calendar.DATE),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR)));
        tvService = findViewById(R.id.tv_service_datsanchitiet);
        tvServiceMoney = findViewById(R.id.tv_money_service_datsanchitiet);
        tvSanBongMoney = findViewById(R.id.tv_money_san_datsanchitiet);
        tvAllMoney = findViewById(R.id.tv_tongtien_datsanchitiet);
        tv_checkTime = findViewById(R.id.tv_check_time_datsan_chitiet);

        btnDatSan = findViewById(R.id.btn_datsan_datsanchitiet);
        btnServiceDetails = findViewById(R.id.btn_chitiet_datsanchitiet);
        imgBack = findViewById(R.id.btn_back_datsanchitiet);

        rdoCoin = findViewById(R.id.rdo_tt0_datsanchitiet);
        rdoTienMat = findViewById(R.id.rdo_tt1_datsanchitiet);
        rdoTTSau = findViewById(R.id.rdo_tt2_datsanchitiet);
    }

    public void openTimeDialog(TextView tv,int code) {
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog pickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (minute <= 15) {
                    tv.setText(hourOfDay + "h");
                    if(code == FROM_CODE){
                        fromHour = hourOfDay;
                    }else if(code == TO_CODE){
                        toHour = hourOfDay;
                    }
                } else {
                    tv.setText(hourOfDay + "h" + 30);
                    if(code == FROM_CODE){
                        fromHour = (float) (hourOfDay+0.5);
                    }else if(code == TO_CODE){
                        toHour = (float) (hourOfDay+0.5);
                    }
                }

            }
        }, mHour, mMinute, true);

        pickerDialog.setTitle("Select time");
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.setCancelable(false);
        pickerDialog.show();
    }

    public void openDateDialog(TextView tv) {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDate = calendar.get(Calendar.DATE);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(year < mYear){
                    Toast.makeText(DatSanChiTietActivity.this, "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
                }else if(year == mYear){
                    if(month < mMonth){
                        Toast.makeText(DatSanChiTietActivity.this, "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
                    }else if(month == mMonth){
                        if(dayOfMonth < mDate){
                            Toast.makeText(DatSanChiTietActivity.this, "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
                        }else{
                            tv.setText(getThu(dayOfMonth, month+1, year));
                        }
                    }else{
                        tv.setText(getThu(dayOfMonth, month+1, year));
                    }
                }else{
                    tv.setText(getThu(dayOfMonth, month+1, year));
                }
            }
        }, mYear, mMonth, mDate);

        pickerDialog.setCancelable(false);
        pickerDialog.setTitle("Chose date");
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    public String getThu(int d, int m, int y) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m-1, d);

        String s = d + "/" + m + "/" + y;
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case 1:
                return "Sun " + s;
            case 2:
                return "Mon " + s;
            case 3:
                return "Tue " + s;
            case 4:
                return "Wed " + s;
            case 5:
                return "Thur " + s;
            case 6:
                return "Fri " + s;
            case 7:
                return "Sat " + s;
            default:
                return "";
        }
    }

    public void setUpService() {
        tvService.setText(listService.size() + " trong tổng số " + MyDatabase.getInstance(this).serviceDAO().getAll().size());

        totalMoneyService = 0;

        for (int i = 0; i < listService.size(); i++) {
            totalMoneyService += listService.get(i).getMoney() * numberOfService.get(i);
        }

        tvServiceMoney.setText(changeToVND(totalMoneyService));
    }

    public String changeToVND(int money) {
        String s = "";
        while (true) {
            if (money / 100 >= 10) {
                s += ".000";
                money /= 1000;
            } else {
                s = money + s;
                break;
            }
        }
        return s;
    }

    public void createDataCustommer() {
        Customer customer = new Customer();
        customer.setName("Nguyễn Văn Nhân");
        customer.setCoin(200000);
        customer.setPassword("123456");
        customer.setCmnd("98329831092");
        customer.setAddress("Phúc thọ Hà Nội");
        customer.setPhone("0390292654");
        MyDatabase.getInstance(this).customerDAO().insert(customer);
        customer = new Customer();
        customer.setName("Nguyễn Văn Bình");
        customer.setCoin(200000);
        customer.setPassword("123456");
        customer.setCmnd("98329831092");
        customer.setAddress("Phúc thọ Hà Nội");
        customer.setPhone("0393692623");
        MyDatabase.getInstance(this).customerDAO().insert(customer);
        customer = new Customer();
        customer.setName("Lê Văn Đại");
        customer.setCoin(200000);
        customer.setPassword("123456");
        customer.setCmnd("98329831092");
        customer.setAddress("Phúc thọ Hà Nội");
        customer.setPhone("03936922233");
        MyDatabase.getInstance(this).customerDAO().insert(customer);
        customer = new Customer();
        customer.setName("Đoàn Văn Chiến");
        customer.setCoin(200000);
        customer.setPassword("123456");
        customer.setCmnd("98329831092");
        customer.setAddress("Phúc thọ Hà Nội");
        customer.setPhone("0394592623");
        MyDatabase.getInstance(this).customerDAO().insert(customer);
        customer = new Customer();
        customer.setName("Nguyễn Thị Cúc");
        customer.setCoin(200000);
        customer.setPassword("123456");
        customer.setCmnd("98329831092");
        customer.setAddress("Phúc thọ Hà Nội");
        customer.setPhone("0309292623");
        MyDatabase.getInstance(this).customerDAO().insert(customer);
        customer = new Customer();
        customer.setName("Đinh Công Sáng");
        customer.setCoin(200000);
        customer.setPassword("123456");
        customer.setCmnd("98329831092");
        customer.setAddress("Phúc thọ Hà Nội");
        customer.setPhone("0393002623");
        MyDatabase.getInstance(this).customerDAO().insert(customer);
    }

    public void createDataCategoriesStaff() {
        ManagerCategory category = new ManagerCategory();
        category.setName("Nhân viên");
        MyDatabase.getInstance(this).managerCategoryDAO().insert(category);
        category = new ManagerCategory();
        category.setName("Admin");
        MyDatabase.getInstance(this).managerCategoryDAO().insert(category);
    }

    public void createDataStaff() {
        List<ManagerCategory> list = MyDatabase.getInstance(this).managerCategoryDAO().getAll();
        if (list.size() > 0) {
            Toast.makeText(this, "list > 0", Toast.LENGTH_SHORT).show();

            Manager manager = new Manager();
            manager.setName("Bùi Thị Xuân");
            manager.setPassword("taolatuan");
            manager.setPhone("0393699054");
            manager.setBankName("Techcombank");
            manager.setPosition(list.get(0).getId());
            MyDatabase.getInstance(this).managerDAO().insert(manager);

            manager = new Manager();
            manager.setName("Bùi Thị Xuân");
            manager.setPassword("taolatuan");
            manager.setPhone("0393699054");
            manager.setBankName("Techcombank");
            manager.setPosition(list.get(0).getId());
            MyDatabase.getInstance(this).managerDAO().insert(manager);
        }
    }
}
