package com.example.duan1_pro1121.adapter.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.fragment.adminfragment.LoginFragment;
import com.example.duan1_pro1121.fragment.adminfragment.SignupFragment;

public class AdapterForm extends FragmentStateAdapter {

    public AdapterForm(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (MyApplication.CURRENT_TYPE != MyApplication.TYPE_ADMIN) {
            if (position == 1) return new SignupFragment();
        }
        return new LoginFragment();
    }

    @Override
    public int getItemCount() {
        if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN) return 1;
        return 2;
    }
}
