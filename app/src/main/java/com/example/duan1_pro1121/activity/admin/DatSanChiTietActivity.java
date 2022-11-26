package com.example.duan1_pro1121.activity.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.MyTime;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.OrderDetails;
import com.example.duan1_pro1121.model.Pitch;
import com.example.duan1_pro1121.model.PithCategory;
import com.example.duan1_pro1121.model.ServiceBall;
import com.example.duan1_pro1121.model.TimeOrderDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatSanChiTietActivity extends AppCompatActivity {

    public static int REQUEST_CODE_CUSTOMER = 1;
    public static int REQUEST_CODE_SERVICE = 2;

    TextView tv_tensan, tvCustomer, tvMocTg,
            tvDate, tvService, tvServiceMoney, tvSanBongMoney, tvAllMoney, tvMoneyCustomer, tvShowCaThiDau, tvChiPhi;

    LinearLayout layoutMoneyCustomer;
    Button btnServiceDetails, btnDatSan;
    ImageView imgBack;
    List<ImageView> listSelect = new ArrayList<>();

    int[] typeSelect = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    ImageView imgSelect1, imgSelect2, imgSelect3, imgSelect4, imgSelect5, imgSelect6;
    ImageView imgSelect7, imgSelect8, imgSelect9, imgSelect10, imgSelect11, imgSelect12;

    Order order;
    Pitch pitch;
    PithCategory categoryPitch;
    Customer customer;
    List<ServiceBall> listService = new ArrayList<>();
    List<Integer> numberOfService = new ArrayList<>();

    int totalMoneyService;
    int totalMoneyPitch;
    int chiPhiKhac;
    String datePlay;
    String dateCreate;
    boolean isUpdate = false;

    int type_add = 0;
    int type_addGray = 1;
    int type_full = 2;
    int type_cancel = 3;
    int type_cancel_gray = 4;

    int count = 0;
    int maxCount = 5;

    boolean canEdit = true;
    boolean isShow = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datsan_chitiet_layout);

        initView();
        setUpImageSelect();

        order = (Order) getIntent().getSerializableExtra("ORDER");
        isShow = getIntent().getBooleanExtra("IS_SHOW",false);

        setOnCLickForView();
        if (order != null) {
            count = order.getSoCa();
            if(isShow){
                tvDate.setEnabled(false);
                tvDate.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                btnDatSan.setEnabled(false);
                btnDatSan.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                canEdit = false;
            }else {
                if (order.getStatus() == MyApplication.NGHI_STATUS || order.getStatus() == MyApplication.DANG_STATUS) {
                    setOnClickForImageView();
                    tvDate.setEnabled(false);
                    tvDate.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                } else if (order.getStatus() == MyApplication.CHUA_STATUS) {
                    setOnClickForImageView();
                } else if (order.getStatus() == MyApplication.DA_STATUS) {
                    //btnServiceDetails.setEnabled(false);
                    btnDatSan.setEnabled(false);
                    tvDate.setEnabled(false);
                    btnDatSan.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                    //btnServiceDetails.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                    tvDate.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                    canEdit = false;
                }
            }
            tvCustomer.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            tvCustomer.setEnabled(false);

            isUpdate = true;
            pitch = MyDatabase.getInstance(this).pitchDao().getPitchId(order.getPitchId()).get(0);
            categoryPitch = MyDatabase.getInstance(this).pitchCategoryDAO().getCategoryPitchWithId(pitch.getCategoryId()).get(0);
            customer = MyDatabase.getInstance(this).customerDAO().getCustomerWithID(order.getCustomerId()).get(0);
            Log.e("123", customer.getCoin() + "+" + order.getTotal());
            customer.setCoin(customer.getCoin() + order.getTotal());

            datePlay = order.getDatePlay();
            dateCreate = order.getDateCreate();
            totalMoneyPitch = order.getTotalPitchMoney();
            chiPhiKhac = order.getChiPhiKhac();
            changeChiPhiKhac();

            listService = (List<ServiceBall>) getIntent().getBundleExtra("bundle")
                    .getSerializable("LIST_SERVICE");
            numberOfService = (List<Integer>) getIntent().getBundleExtra("bundle")
                    .getSerializable("LIST_NUMBER");

            showLichHoatDong();
            setUpTvMocTg("", type_cancel);

            btnDatSan.setText("Cập nhật");
        } else {
            setOnClickForImageView();
            pitch = (Pitch) getIntent().getSerializableExtra("PITCH");
            categoryPitch = MyDatabase.getInstance(this).pitchCategoryDAO()
                    .getCategoryPitchWithId(pitch.getCategoryId()).get(0);

            Calendar calendar = Calendar.getInstance();
            datePlay = getStringDate(calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
            setUpTvDate();

            showLichHoatDong();
        }

        setUpTvDate();
        setUpTvMoneyAndTvPitch();
        setUpMoneyAndTvService();
        changeChiPhiKhac();
    }

    public void datSan() {
        if (count <= 0) {
            Toast.makeText(this, "Bạn cần chọn ít nhất 1 ca thi đấu", Toast.LENGTH_SHORT).show();
        } else {
            if (!isUpdate) {
                if (customer == null) {
                    Toast.makeText(this, "Vui lòng chọn khách hàng", Toast.LENGTH_SHORT).show();
                } else {
                    int total = totalMoneyPitch + totalMoneyService + chiPhiKhac;
                    if (customer.getCoin() < total) {
                        Toast.makeText(this, "Tài khoản khách hàng không đủ", Toast.LENGTH_SHORT).show();
                    } else {
                        customer.setCoin(customer.getCoin() - total);
                        MyDatabase.getInstance(this).customerDAO().update(customer);

                        order = new Order();
                        order.setId(++MainActivity.ID_MAX_ORDER);
                        order.setManagerId(MyDatabase.getInstance(this).managerDAO().getManagerWithPhone(MainActivity.ACCOUNT,-1).get(0).getId());
                        Calendar calendar = Calendar.getInstance();
                        dateCreate = getStringDate(calendar.get(Calendar.DATE),
                                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                        order.setDateCreate(dateCreate);
                        order.setPitchId(pitch.getId());
                        order.setCustomerId(customer.getId());
                        order.setTotalPitchMoney(totalMoneyPitch);
                        order.setDatePlay(datePlay);
                        order.setChiPhiKhac(chiPhiKhac);
                        order.setTotalServiceMoney(totalMoneyService);
                        order.setTotal(total);
                        order.setStatus(MyApplication.CHUA_STATUS);
                        order.setSoCa(count);

                        MyDatabase.getInstance(this).orderDAO().insert(order);

                        for (int i = 0; i < typeSelect.length; i++) {
                            if (typeSelect[i] == type_cancel) {
                                TimeOrderDetails timeOrderDetails = new TimeOrderDetails();
                                timeOrderDetails.setTimeId(i + 1);
                                timeOrderDetails.setOrderId(order.getId());
                                MyDatabase.getInstance(this).timeOrderDetailsDAO().insert(timeOrderDetails);
                            }
                        }

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
            } else {
                int total = totalMoneyPitch + totalMoneyService + chiPhiKhac;
                if (customer.getCoin() < total) {
                    Toast.makeText(this, "Tài khoản khách hàng không đủ", Toast.LENGTH_SHORT).show();
                } else {
                    customer.setCoin(customer.getCoin() - total);
                    MyDatabase.getInstance(this).customerDAO().update(customer);

                    order.setDatePlay(datePlay);
                    order.setTotalPitchMoney(totalMoneyPitch);
                    order.setChiPhiKhac(chiPhiKhac);
                    order.setTotalServiceMoney(totalMoneyService);
                    order.setTotal(total);
                    order.setSoCa(count);

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

                    int[] types = new int[12];
                    List<TimeOrderDetails> timeOrderDetails = MyDatabase.getInstance(this)
                            .timeOrderDetailsDAO().getTimeOrderWithOrderId(order.getId());
                    for (int i = 0; i < timeOrderDetails.size(); i++) {
                        int timeId = timeOrderDetails.get(i).getTimeId();
                        types[timeId - 1] = 1;
                    }
                    for (int i = 0; i < typeSelect.length; i++) {
                        if (typeSelect[i] == type_cancel && types[i] == 0) {
                            TimeOrderDetails timeOrderDetails1 = new TimeOrderDetails();
                            timeOrderDetails1.setOrderId(order.getId());
                            timeOrderDetails1.setTimeId(i + 1);
                            MyDatabase.getInstance(this).timeOrderDetailsDAO().insert(timeOrderDetails1);
                        } else if (typeSelect[i] == type_add && types[i] == 1) {
                            MyDatabase.getInstance(this).timeOrderDetailsDAO().deleteWithOrderIdAndTimeId(order.getId(), i + 1);
                        }
                    }
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


    public void resetData() {
        count = 0;
        totalMoneyPitch = 0;
        order = null;
        listService = new ArrayList<>();
        numberOfService = new ArrayList<>();
        setUpMoneyAndTvService();
        setUpTvMoneyAndTvPitch();
        tvMocTg.setText("");
    }

    public void showLichHoatDong() {
        resetTypeSelect();

        Calendar calendar = Calendar.getInstance();
        String s = getStringDate(calendar.get(Calendar.DATE),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));

        // kiểm tra thời gian quá khứ
        if (s.equals(datePlay)) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            for (int i = 0; i < 12; i++) {
                if (hour >= i * 2) {
                    typeSelect[i] = type_addGray;
                }
            }
        } else if (checkDate(s)) {
            for (int i = 0; i < 12; i++) {
                typeSelect[i] = type_addGray;
            }
        }
        // Kiểm tra thời gian bị full
        List<TimeOrderDetails> timeOrderDetails =
                MyDatabase.getInstance(this).timeOrderDetailsDAO().getTimeOrderWithDateAndPitch(datePlay, pitch.getId());

        for (int i = 0; i < timeOrderDetails.size(); i++) {
            int idTime = timeOrderDetails.get(i).getTimeId();
            if (order != null) {
                if (order.getId() == timeOrderDetails.get(i).getOrderId()) {
                    if (typeSelect[idTime - 1] == type_addGray) {
                        typeSelect[idTime - 1] = type_cancel_gray;
                    } else {
                        typeSelect[idTime - 1] = type_cancel;
                    }
                } else {
                    typeSelect[idTime - 1] = type_full;
                }
            } else {
                typeSelect[idTime - 1] = type_full;
            }
        }

        setResourceForImageSelect();
    }

    public boolean checkDate(String s) {

        int[] arr1 = getArrayDate(s);
        int[] arr2 = getArrayDate(datePlay);

        if (arr2[2] < arr1[2]) {
            return true;
        } else if (arr2[1] < arr1[1]) {
            return true;
        } else return arr2[0] < arr1[0];
    }

    public void resetTypeSelect() {
        Arrays.fill(typeSelect, 0);
    }

    public void setResourceForImageSelect() {
        for (int i = 0; i < listSelect.size(); i++) {
            if (typeSelect[i] == type_add) {
                listSelect.get(i).setImageResource(R.drawable.ic_add);
            } else if (typeSelect[i] == type_full) {
                listSelect.get(i).setImageResource(R.drawable.soldout_png);
            } else if (typeSelect[i] == type_addGray) {
                listSelect.get(i).setImageResource(R.drawable.ic_add_gray);
            } else if (typeSelect[i] == type_cancel) {
                listSelect.get(i).setImageResource(R.drawable.ic_cancel);
            } else if (typeSelect[i] == type_cancel_gray) {
                listSelect.get(i).setImageResource(R.drawable.ic_cancel_gray);
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

    public int[] getArrayDate(String s) {
        String[] str = s.split("-");
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
        int[] arr = getArrayDate(datePlay);
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
        tvMocTg = findViewById(R.id.tv_moctg_datsanchitiet);
        tvShowCaThiDau = findViewById(R.id.tv_caTime_datsanchitiet);
        tvChiPhi = findViewById(R.id.tv_chiphi_datsanchitiet);
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

    public void setOnClickForImageView() {
        for (int i = 0; i < listSelect.size(); i++) {
            int finalI = i;
            listSelect.get(i).setOnClickListener(v -> {
                if (typeSelect[finalI] == type_add && count < maxCount) {
                    count += 1;
                    typeSelect[finalI] = type_cancel;
                    setImageResourceAtPos(finalI);
                    setUpTvMocTg("Ca" + (finalI + 1), type_add);

                    MyTime time = MyDatabase.getInstance(this).timeDAO().getTimeWithId(finalI + 1).get(0);
                    chiPhiKhac += time.getMoney();
                    changeChiPhiKhac();
                    changeTotalMoneyPitch(categoryPitch.getMoney() * 2);
                } else if (typeSelect[finalI] == type_cancel) {
                    count -= 1;
                    typeSelect[finalI] = type_add;
                    setImageResourceAtPos(finalI);
                    setUpTvMocTg("", type_cancel);

                    MyTime time = MyDatabase.getInstance(this).timeDAO().getTimeWithId(finalI + 1).get(0);
                    chiPhiKhac -= time.getMoney();
                    changeChiPhiKhac();
                    changeTotalMoneyPitch(-categoryPitch.getMoney() * 2);
                } else if (typeSelect[finalI] == type_cancel_gray) {
                    Toast.makeText(this, "Bạn không thể hủy mốc thời gian đã đá", Toast.LENGTH_SHORT).show();
                } else if (typeSelect[finalI] == type_addGray) {
                    Toast.makeText(this, "Mốc thời gian đã quá hạn", Toast.LENGTH_SHORT).show();
                } else if (typeSelect[finalI] == type_full) {
                    Toast.makeText(this, "Mốc thời gian đã có khách hàng đặt", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void changeChiPhiKhac() {
        tvChiPhi.setText(MyApplication.convertMoneyToString(chiPhiKhac) + " VNĐ");
        setUpTotalMoney();
    }

    public void changeTotalMoneyPitch(int money) {
        totalMoneyPitch += money;
        setUpTvMoneyAndTvPitch();
    }

    public void setUpTvMocTg(String s, int type) {
        if (type == type_add) {
            if (tvMocTg.getText().toString().equals("")) {
                tvMocTg.setText(s);
            } else {
                tvMocTg.setText(tvMocTg.getText().toString() + "-" + s);
            }
        } else {
            tvMocTg.setText("");
            for (int i = 0; i < typeSelect.length; i++) {
                if (typeSelect[i] == type_cancel || typeSelect[i] == type_cancel_gray) {
                    setUpTvMocTg("Ca" + (i + 1), type_add);
                }
            }
        }
    }

    public void setUpImageSelect() {
        addImageView();
    }

    public void setImageResourceAtPos(int pos) {
        if (typeSelect[pos] == type_add) {
            listSelect.get(pos).setImageResource(R.drawable.ic_add);
        } else if (typeSelect[pos] == type_cancel) {
            listSelect.get(pos).setImageResource(R.drawable.ic_cancel);
        }
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
                        if (!dateChose.equals(datePlay)) {
                            datePlay = dateChose;
                            setUpTvDate();
                            showLichHoatDong();
                        }
                    }
                } else {
                    String dateChose = getStringDate(dayOfMonth, month + 1, year);
                    if (!dateChose.equals(datePlay)) {
                        datePlay = dateChose;
                        setUpTvDate();
                        showLichHoatDong();
                    }
                }
            } else {
                String dateChose = getStringDate(dayOfMonth, month + 1, year);
                if (!dateChose.equals(datePlay)) {
                    datePlay = dateChose;
                    setUpTvDate();
                    showLichHoatDong();
                }
            }
        }, mYear, mMonth, mDate);

        pickerDialog.setCancelable(false);
        pickerDialog.setTitle("Chọn ngày : ");
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    public void setOnCLickForView() {
        tvDate.setOnClickListener(v -> {
            if (order != null) {
                if (order.getStatus() == MyApplication.CHUA_STATUS && count > 0) {
                    Toast.makeText(DatSanChiTietActivity.this, "Bạn cần hủy lịch của ngày " + datePlay, Toast.LENGTH_SHORT).show();
                } else {
                    openDateDialogTvDate();
                }
            } else {
                openDateDialogTvDate();
            }
        });
        btnServiceDetails.setOnClickListener(v -> {
            Intent intent = new Intent(DatSanChiTietActivity.this, ListServiceActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("LIST_SERVICE", (Serializable) listService);
            bundle.putSerializable("LIST_NUMBER", (Serializable) numberOfService);
            intent.putExtra("CAN_EDIT", canEdit);
            intent.putExtra("bundle", bundle);
            startActivityForResult(intent, REQUEST_CODE_SERVICE);
            Animatoo.INSTANCE.animateShrink(this);
        });
        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });
        btnDatSan.setOnClickListener(v -> {
            datSan();
        });
        tvCustomer.setOnClickListener(v -> {
            if (!isUpdate) {
                Intent intent = new Intent(DatSanChiTietActivity.this, ListCustomerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
                Animatoo.INSTANCE.animateSlideLeft(this);
            } else {
                Toast.makeText(this, "Bạn không thể chọn khách hàng khi cập nhật phiếu thông tin", Toast.LENGTH_SHORT).show();
            }
        });
        tvShowCaThiDau.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowCaThiDauActivity.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateSlideLeft(this);
        });
    }

    // set up text view
    public void setUpTvDate() {
        tvDate.setText(getThu());
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
        tvAllMoney.setText(MyApplication.convertMoneyToString(totalMoneyPitch + totalMoneyService + chiPhiKhac) + " VNĐ");
        setUpTvMoneyAndTvCustomer();
    }

    public void setUpTvMoneyAndTvCustomer() {
        if (customer != null) {
            layoutMoneyCustomer.setVisibility(View.VISIBLE);
            tvCustomer.setText(customer.getName());
            tvMoneyCustomer.setText(MyApplication.convertMoneyToString(customer.getCoin()) + " VNĐ");
            if (customer.getCoin() >= (chiPhiKhac + totalMoneyService + totalMoneyPitch))
                tvMoneyCustomer.setTextColor(Color.parseColor("#23C82A"));
            else tvMoneyCustomer.setTextColor(Color.parseColor("#FF1100"));
        }
    }

    public void setUpTvMoneyAndTvPitch() {
        tv_tensan.setText(pitch.getName());
        tvSanBongMoney.setText(MyApplication.convertMoneyToString(totalMoneyPitch) + " VNĐ");
        setUpTotalMoney();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        customer = MyDatabase.getInstance(this)
//                .customerDAO().getCustomerWithID(customer.getId()).get(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(this);
    }
}