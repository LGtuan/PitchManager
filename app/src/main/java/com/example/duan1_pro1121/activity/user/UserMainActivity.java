package com.example.duan1_pro1121.activity.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.fragment.adminfragment.DatSanFragment;
import com.example.duan1_pro1121.fragment.userfragment.ProfileFragment;
import com.example.duan1_pro1121.fragment.userfragment.ThongBaoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayoutMediator;

public class UserMainActivity extends AppCompatActivity {

    private static int CURRENT_FRAGMENT = -1;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        navigationView = findViewById(R.id.bottom_navi);
        navigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.item_datsan_user){
                handle(0);
            }else if(item.getItemId() == R.id.item_notify_user){
                handle(1);
            }else if(item.getItemId() == R.id.item_profile_user){
                handle(2);
            }
            return true;
        });

        handle(0);
    }

    public void handle(int position){
        if(position == 0 && CURRENT_FRAGMENT!=0){
            replaceFragment(new DatSanFragment());
            CURRENT_FRAGMENT = 0;
        }else if(position == 1 && CURRENT_FRAGMENT!=1){
            replaceFragment(new ThongBaoFragment());
            CURRENT_FRAGMENT = 1;
        }else if(position == 2 && CURRENT_FRAGMENT!=2){
            replaceFragment(new ProfileFragment());
            CURRENT_FRAGMENT = 2;
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_user,fragment);
        transaction.commit();
    }

}