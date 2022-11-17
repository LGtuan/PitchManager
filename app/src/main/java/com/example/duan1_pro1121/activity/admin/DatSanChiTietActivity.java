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
    int[] typeSelect = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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

    Order order;
    Pitch pitch;
    PithCategory categoryPitch;
    Customer customer;
    List<ServiceBall> listService = new ArrayList<>();
    List<Integer> numberOfService = new ArrayList<>();

    int currentImgSelect = -1;
    int totalMoneyService;
    int totalMoneyPitch;
    String datePlay;
    String dateCreate;
    boolean isUpdate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datsan_chitiet_layout);

        initView();
        setUpImageSelect();

        order = (Order) getIntent().getSerializableExtra("ORDER");
        if(order==null){
            setOnClickForView();
            setOnClickForImageView();
        }else{
            if(order.getStatus() == MyApplication.CHUA_STATUS){
                setOnClickForView();
                setOnClickForImageView();
            }
        }
        if (order != null) {
            isUpdate = true;
            pitch = MyDatabase.getInstance(this).pitchDao().getPitchId(order.getPitchId()).get(0);
            categoryPitch = MyDatabase.getInstance(this).pitchCategoryDAO().getCategoryPitchWithId(pitch.getCategoryId()).get(0);
            customer = MyDatabase.getInstance(this).customerDAO().getCustomerWithID(order.getCustomerId()).get(0);
            customer.setCoin(customer.getCoin() + order.getTotal());

            datePlay = order.getDatePlay();
            dateCreate = order.getDate();
            totalMoneyPitch = order.getTotalPitchMoney();

            currentImgSelect = (int) order.getStartTime() / 2;
            setUpTvStartAndEndTime(currentImgSelect);

            listService = (List<ServiceBall>) getIntent().getBundleExtra("bundle")
                    .getSerializable("LIST_SERVICE");
            numberOfService = (List<Integer>) getIntent().getBundleExtra("bundle")
                    .getSerializable("LIST_NUMBER");

            showLichHoatDong();
            setImageResourceCancel(currentImgSelect);

            btnDatSan.setText("Cập nhật");
        } else {
            pitch = (Pitch) getIntent().getSerializableExtra("PITCH");
            categoryPitch = MyDatabase.getInstance(this).pitchCategoryDAO()
                    .getCategoryPitchWithId(pitch.getCategoryId()).get(0);

            totalMoneyPitch = categoryPitch.getMoney() * 2;

            Calendar calendar = Calendar.getInstance();
            datePlay = getStringDate(calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
            setUpTvDate();

            showLichHoatDong();
        }

        setUpTvDate();
        setUpTvMoneyAndTvPitch();
        setUpMoneyAndTvService();
        setUpTotalMoney();
    }

    public void datSan() {
        if (!isUpdate) {
            if (currentImgSelect <= -1) {
                Toast.makeText(this, "Vui lòng chọn khung thời gian", Toast.LENGTH_SHORT).show();
            } else {
                if (customer == null) {
                    Toast.makeText(this, "Vui lòng chọn khách hàng", Toast.LENGTH_SHORT).show();
                } else {
                    int total = totalMoneyPitch + totalMoneyService;
                    if(customer.getCoin() < total){
                        Toast.makeText(this, "Tài khoản khách hàng không đủ", Toast.LENGTH_SHORT).show();
                    }else{
                        customer.setCoin(customer.getCoin()-total);
                        MyDatabase.getInstance(this).customerDAO().update(customer);

                        order = new Order();
                        order.setId(++MainActivity.ID_MAX_ORDER);
                        Calendar calendar = Calendar.getInstance();
                        dateCreate = getStringDate(calendar.get(Calendar.DATE),
                                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                        order.setDate(dateCreate);
                        order.setPitchId(pitch.getId());
                        order.setCustomerId(customer.getId());
                        order.setTotalPitchMoney(totalMoneyPitch);
                        order.setDatePlay(datePlay);
                        order.setEndTime(listTime.get(currentImgSelect).endTime);
                        order.setStartTime(listTime.get(currentImgSelect).startTime);
                        order.setTotal(totalMoneyPitch + totalMoneyService);
                        order.setStatus(MyApplication.CHUA_STATUS);

                        MyDatabase.getInstance(this).orderDAO().insert(order);

                        for (int i = 0; i < listService.size(); i++) {
                            OrderDetails orderDetails = new OrderDetails();
                            orderDetails.setServiceId(listService.get(i).getId());
                            orderDetails.setOrderId(order.getId());
                            orderDetails.setSoLuong(numberOfService.get(i));
                            orderDetails.setTongTien(listService.get(i).getMoney() * numberOfService.get(i));

                            MyDatabase.getInstance(this).orderDetailsDAO().insert(orderDetails);
                        }
                        Toast.makeText(this, "Đặt sân thành công", Toast.LENGTH_SHORT).show();

                        resetData();
                        showLichHoatDong();
                    }
                }
            }
        } else {
            if (currentImgSelect <= -1) {
                Toast.makeText(this, "Vui lòng chọn khung thời gian", Toast.LENGTH_SHORT).show();
            } else {
                int total = totalMoneyPitch + totalMoneyService;
                if(customer.getCoin() < total){
                    Toast.makeText(this, "Tài khoản khách hàng không đủ", Toast.LENGTH_SHORT).show();
                }else{
                    customer.setCoin(customer.getCoin()-total);
                    MyDatabase.getInstance(this).customerDAO().update(customer);

                    order.setDatePlay(datePlay);
                    order.setEndTime(listTime.get(currentImgSelect).endTime);
                    order.setStartTime(listTime.get(currentImgSelect).startTime);
                    order.setTotal(totalMoneyPitch + totalMoneyService);

                    MyDatabase.getInstance(this).orderDAO().update(order);

                    MyDatabase.getInstance(this).orderDetailsDAO().deleteWithOrderId(order.getId());
                    for (int i = 0; i < listService.size(); i++) {
                        OrderDetails orderDetails = new OrderDetails();
                        orderDetails.setServiceId(listService.get(i).getId());
                        orderDetails.setOrderId(order.getId());
                        orderDetails.setSoLuong(numberOfService.get(i));
                        orderDetails.setTongTien(listService.get(i).getMoney() * numberOfService.get(i));

                        MyDatabase.getInstance(this).orderDetailsDAO().insert(orderDetails);
                    }
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void resetData() {
        currentImgSelect = -1;
        tv_startTime.setText("0h");
        tv_endTime.setText("0h");
        listService = new ArrayList<>();
        numberOfService = new ArrayList<>();
        tvMoneyCustomer.setText(MyApplication.convertMoneyToString(customer.getCoin()) + " VNĐ");
        setUpMoneyAndTvService();
        setUpTotalMoney();
    }

    public void showLichHoatDong() {
        resetTypeSelect();

        Calendar calendar = Calendar.getInstance();
        String s = getStringDate(calendar.get(Calendar.DATE),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));

        if (s.equals(datePlay)) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            for (int i = 0; i < 12; i++) {
                if (hour >= i * 2) {
                    typeSelect[i] = 2;
                }
            }
        }

        List<Order> orders;
        if (isUpdate) {
            orders = MyDatabase.getInstance(this).orderDAO().getOrderWithPitchAndDate(pitch.getId(), datePlay, (int) order.getStartTime());
        } else {
            orders = MyDatabase.getInstance(this).orderDAO().getOrderWithPitchAndDate(pitch.getId(), datePlay);
        }
        for (int i = 0; i < orders.size(); i++) {
            float orderStart = orders.get(i).getStartTime();
            int pos = (int) (orderStart / 2);
            typeSelect[pos] = 1;
        }

        setResourceForImageSelect();
    }

    public void resetTypeSelect() {
        Arrays.fill(typeSelect, 0);
    }

    public void setResourceForImageSelect() {
        for (int i = 0; i < listSelect.size(); i++) {
            if (typeSelect[i] == 0) {
                listSelect.get(i).setImageResource(R.drawable.ic_add);
            } else if (typeSelect[i] == 1) {
                listSelect.get(i).setImageResource(R.drawable.soldout_png);
            } else if (typeSelect[i] == 2) {
                listSelect.get(i).setImageResource(R.drawable.ic_add_gray);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_CUSTOMER == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                customer = (Customer) data.getSerializableExtra("CUSTOMER");
                setUpTvMoneyAndTvCustomer();
            }
        } else if (REQUEST_CODE_SERVICE == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                listService = (List<ServiceBall>) data.getBundleExtra("bundle").getSerializable("LIST_SERVICE");
                numberOfService = (List<Integer>) data.getBundleExtra("bundle").getSerializable("LIST_NUMBER");
                setUpMoneyAndTvService();
            }
        }
    }

    public String getStringDate(int d, int m, int y) {
        return d + "-" + m + "-" + y;
    }

    public int[] getArrayDate() {
        String[] str = datePlay.split("-");
        int arr[] = new int[str.length];
        try {
            for (int i = 0; i < str.length; i++) {
                arr[i] = Integer.parseInt(str[i]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return arr;
    }

    public String getThu() {
        int[] arr = getArrayDate();
        Calendar calendar = Calendar.getInstance();
        if (arr != null) calendar.set(arr[2], arr[1] - 1, arr[0]);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return "Sun " + datePlay;
            case 2:
                return "Mon " + datePlay;
            case 3:
                return "Tue " + datePlay;
            case 4:
                return "Wed " + datePlay;
            case 5:
                return "Thur " + datePlay;
            case 6:
                return "Fri " + datePlay;
            case 7:
                return "Sat " + datePlay;
            default:
                return "";
        }
    }

    public void initView() {
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
        imgBack = findViewById(R.id.btn_back_datsanchitiet);
        layoutMoneyCustomer = findViewById(R.id.layoutMoneyCustomer);
        tvMoneyCustomer = findViewById(R.id.tv_money_customer_datsanchitiet);

    }

    public void addImageView() {
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
    }

    public void addTime() {
        for (int i = 0; i < 12; i++) {
            listTime.add(new MyTime(i * 2, (i + 1) * 2));
        }
    }

    public void setOnClickForImageView() {
        for (int i = 0; i < listSelect.size(); i++) {
            int finalI = i;
            listSelect.get(i).setOnClickListener(v -> {
                if (typeSelect[finalI] == 0) {
                    if (currentImgSelect != finalI) {
                        if (currentImgSelect != -1) setImageResourceAdd(currentImgSelect);
                        setUpTvStartAndEndTime(finalI);
                        listSelect.get(finalI).setImageResource(R.drawable.ic_cancel);
                        currentImgSelect = finalI;
                    } else {
                        listSelect.get(finalI).setImageResource(R.drawable.ic_add);
                        currentImgSelect = -1;
                        tv_startTime.setText("0h");
                        tv_endTime.setText("0h");
                    }
                } else if (typeSelect[finalI] == 1) {
                    Toast.makeText(this, "Lịch đã được khách hàng khác đặt", Toast.LENGTH_SHORT).show();
                } else if (typeSelect[finalI] == 2) {
                    Toast.makeText(this, "Đã quá thời gian", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setUpImageSelect() {
        addImageView();
        addTime();
    }

    public void setImageResourceAdd(int pos) {
        listSelect.get(pos).setImageResource(R.drawable.ic_add);
    }

    public void setImageResourceCancel(int pos) {
        listSelect.get(pos).setImageResource(R.drawable.ic_cancel);
    }

    public void openDateDialogTvDate() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDate = calendar.get(Calendar.DATE);
        DatePickerDialog pickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog, (view, year, month, dayOfMonth) -> {
            if (year < mYear) {
                Toast.makeText(DatSanChiTietActivity.this,
                        "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
            } else if (year == mYear) {
                if (month < mMonth) {
                    Toast.makeText(DatSanChiTietActivity.this,
                            "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
                } else if (month == mMonth) {
                    if (dayOfMonth < mDate) {
                        Toast.makeText(DatSanChiTietActivity.this,
                                "Không thể chọn ngày của quá khứ", Toast.LENGTH_SHORT).show();
                    } else {
                        String dateChose = getStringDate(dayOfMonth, month + 1, year);
                        if(!dateChose.equals(datePlay)) {
                            datePlay = dateChose;
                            setUpTvDate();
                            showLichHoatDong();
                            currentImgSelect = -1;
                        }
                    }
                } else {
                    String dateChose = getStringDate(dayOfMonth, month + 1, year);
                    if(!dateChose.equals(datePlay)) {
                        datePlay = dateChose;
                        setUpTvDate();
                        showLichHoatDong();
                        currentImgSelect = -1;
                    }
                }
            } else {
                String dateChose = getStringDate(dayOfMonth, month + 1, year);
                if(!dateChose.equals(datePlay)) {
                    datePlay = dateChose;
                    setUpTvDate();
                    showLichHoatDong();
                    currentImgSelect = -1;
                }
            }
        }, mYear, mMonth, mDate);

        pickerDialog.setCancelable(false);
        pickerDialog.setTitle("Chọn ngày : ");
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    public void setOnClickForView() {
        tvCustomer.setOnClickListener(v -> {
            if(!isUpdate) {
                Intent intent = new Intent(DatSanChiTietActivity.this, ListCustomerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
            }else{
                Toast.makeText(this, "Bạn không thể chọn khách hàng khi cập nhật phiếu thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        tvDate.setOnClickListener(v -> {
            openDateDialogTvDate();
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
            if (currentImgSelect != -1) {
                datSan();
            } else {
                Toast.makeText(this, "Bạn chưa chọn thời gian thi đấu", Toast.LENGTH_SHORT).show();
            }
        });

        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    // set up text view
    public void setUpTvDate() {
        tvDate.setText(getThu());
    }

    public void setUpTvStartAndEndTime(int i) {
        tv_startTime.setText(listTime.get(i).startTime + "h");
        tv_endTime.setText(listTime.get(i).endTime + "h");
    }

    public void setUpMoneyAndTvService() {
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
        setUpTvMoneyAndTvCustomer();
    }

    public void setUpTvMoneyAndTvCustomer() {
        if (customer != null) {
            layoutMoneyCustomer.setVisibility(View.VISIBLE);
            tvCustomer.setText(customer.getName());
            tvMoneyCustomer.setText(MyApplication.convertMoneyToString(customer.getCoin()) + " VNĐ");
            if (customer.getCoin() >= (totalMoneyService + totalMoneyPitch))
                tvMoneyCustomer.setTextColor(Color.parseColor("#23C82A"));
            else tvMoneyCustomer.setTextColor(Color.parseColor("#FF1100"));
        }
    }

    public void setUpTvMoneyAndTvPitch() {
        tv_tensan.setText(pitch.getName());
        tvSanBongMoney.setText(MyApplication.convertMoneyToString(totalMoneyPitch) + " VNĐ");
    }
}