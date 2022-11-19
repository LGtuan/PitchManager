package com.example.duan1_pro1121.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.SelectTypeActivity;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.fragment.adminfragment.DatSanFragment;
import com.example.duan1_pro1121.fragment.adminfragment.DichVuFrgment;
import com.example.duan1_pro1121.fragment.adminfragment.KhachHangFragment;
import com.example.duan1_pro1121.fragment.adminfragment.ManHinhCaNhanFragment;
import com.example.duan1_pro1121.fragment.adminfragment.NhanVienFragment;
import com.example.duan1_pro1121.fragment.adminfragment.PhieuThongTinFragment;
import com.example.duan1_pro1121.fragment.adminfragment.SanBongFragment;
import com.example.duan1_pro1121.fragment.adminfragment.ThongKeFrgment;
import com.example.duan1_pro1121.fragment.adminfragment.TimeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    public static String ACCOUNT = "";
    public static int ID_MAX_ORDER = 0;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    public static int CURRENT_FRAGMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ID_MAX_ORDER = MyDatabase.getInstance(this).orderDAO().getIdMax();
        Log.e("123","max" + ID_MAX_ORDER);

        ACCOUNT = getIntent().getStringExtra("account");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);

        toolbar.setNavigationOnClickListener(v->{
            drawerLayout.openDrawer(GravityCompat.START);
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Menu menu = navigationView.getMenu();

                if(item.getItemId() == R.id.item_datsan && CURRENT_FRAGMENT!=0){
                    menu.findItem(item.getItemId()).setChecked(true);
                    CURRENT_FRAGMENT = 0;
                    replaceFragment(new DatSanFragment());
                    getSupportActionBar().setTitle("Đặt Sân");
                }else if(item.getItemId() == R.id.item_sanbong && CURRENT_FRAGMENT!=1){
                    menu.findItem(item.getItemId()).setChecked(true);
                    CURRENT_FRAGMENT = 1;
                    replaceFragment(new SanBongFragment());
                    getSupportActionBar().setTitle("Sân Bóng");
                }else if(item.getItemId() == R.id.item_giothidau && CURRENT_FRAGMENT!=2){
                    menu.findItem(item.getItemId()).setChecked(true);
                    CURRENT_FRAGMENT = 2;
                    replaceFragment(new TimeFragment());
                    getSupportActionBar().setTitle("Giờ thi đấu");
                } else if(item.getItemId() == R.id.item_khachhang && CURRENT_FRAGMENT!=3){
                    menu.findItem(item.getItemId()).setChecked(true);
                    CURRENT_FRAGMENT = 3;
                    replaceFragment(new KhachHangFragment());
                    getSupportActionBar().setTitle("Khách Hàng");
                }else if(item.getItemId() == R.id.item_phieuthongtin && CURRENT_FRAGMENT!=4){
                    menu.findItem(item.getItemId()).setChecked(true);
                    CURRENT_FRAGMENT = 4;
                    replaceFragment(new PhieuThongTinFragment());
                    getSupportActionBar().setTitle("Phiếu Thông Tin");
                }else if(item.getItemId() == R.id.item_dichvu && CURRENT_FRAGMENT!=5){
                    menu.findItem(item.getItemId()).setChecked(true);
                    CURRENT_FRAGMENT = 5;
                    replaceFragment(new DichVuFrgment());
                    getSupportActionBar().setTitle("Dịch Vụ");
                }else if(item.getItemId() == R.id.item_nhanvien && CURRENT_FRAGMENT!=6) {
                    if (ACCOUNT.equals(MyApplication.ADMIN_CATEGORY)) {
                        menu.findItem(item.getItemId()).setChecked(true);
                        CURRENT_FRAGMENT = 6;
                        replaceFragment(new NhanVienFragment());
                        getSupportActionBar().setTitle("Nhân Viên");
                    } else {
                        Toast.makeText(MainActivity.this, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else if(item.getItemId() == R.id.item_man_hinh_ca_nhan && CURRENT_FRAGMENT!=7)  {
                    menu.findItem(item.getItemId()).setChecked(true);
                    CURRENT_FRAGMENT = 7;
                    replaceFragment(new ManHinhCaNhanFragment());
                    getSupportActionBar().setTitle("Màn hình cá nhân");
                }else if(item.getItemId() == R.id.item_thongke && CURRENT_FRAGMENT!=8){
                    if(ACCOUNT.equals(MyApplication.ADMIN_CATEGORY)) {
                        menu.findItem(item.getItemId()).setChecked(true);
                        CURRENT_FRAGMENT = 8;
                        replaceFragment(new ThongKeFrgment());
                        getSupportActionBar().setTitle("Thống Kê");
                    }else{
                        Toast.makeText(MainActivity.this, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else if(item.getItemId() == R.id.item_dangxuat){
                    finishAffinity();
                    MyApplication.CURRENT_TYPE = -1;
                    Intent intent = new Intent(MainActivity.this, SelectTypeActivity.class);
                    startActivity(intent);
                }

                drawerLayout.close();
                return true;
            }
        });

        replaceFragment(new DatSanFragment());
        getSupportActionBar().setTitle("Đặt Sân");
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_content,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isOpen())drawerLayout.close();
        else{
            finishAffinity();
            System.exit(0);
        }
    }
}