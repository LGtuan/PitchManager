package com.example.duan1_pro1121.activity.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.fragment.adminfragment.DatSanFragment;
import com.example.duan1_pro1121.fragment.userfragment.ProfileFragment;
import com.example.duan1_pro1121.fragment.userfragment.ThongBaoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserMainActivity extends AppCompatActivity {

    private static int CURRENT_FRAGMENT = 0;
    private BottomNavigationView navigationView;
    private String STRING_ACCOUNT = "";
    private ImageView btnNap;
    private MeowBottomNavigation bottomNavigation;

    int id_pitch = 1;
    int id_notification = 2;
    int id_profile = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        btnNap = findViewById(R.id.btn_nap_acount_activity);
        btnNap.setOnClickListener(v->{
            Intent intent = new Intent(UserMainActivity.this,NapTienActivity.class);
            startActivity(intent);
        });
        bottomNavigation = findViewById(R.id.meowNavi);
        bottomNavigation.add(new MeowBottomNavigation.Model(id_pitch,R.drawable.ic_pitch));
        bottomNavigation.add(new MeowBottomNavigation.Model(id_notification,R.drawable.ic_notification));
        bottomNavigation.add(new MeowBottomNavigation.Model(id_profile,R.drawable.ic_user_circle));

        bottomNavigation.show(id_pitch,true);
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                handle(model.getId());
                return null;
            }
        });

//        navigationView = findViewById(R.id.bottom_navi);
//        navigationView.setOnItemSelectedListener(item -> {
//            if(item.getItemId() == R.id.item_datsan_user){
//                handle(0);
//            }else if(item.getItemId() == R.id.item_notify_user){
//                handle(1);
//            }else if(item.getItemId() == R.id.item_profile_user){
//                handle(2);
//            }
//            return true;
//        });

        handle(id_pitch);
    }

    public void handle(int position){
        if(position == 1 && CURRENT_FRAGMENT!=1){
            replaceFragment(new DatSanFragment());
            CURRENT_FRAGMENT = 1;
        }else if(position == 2 && CURRENT_FRAGMENT!=2){
            replaceFragment(new ThongBaoFragment());
            CURRENT_FRAGMENT = 2;
        }else if(position == 3 && CURRENT_FRAGMENT!=3){
            replaceFragment(new ProfileFragment());
            CURRENT_FRAGMENT = 3;
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_user,fragment);
        transaction.commit();
    }

}