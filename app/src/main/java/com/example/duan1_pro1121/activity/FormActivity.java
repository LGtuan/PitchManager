package com.example.duan1_pro1121.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.AdapterForm;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FormActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        AdapterForm adapter = new AdapterForm(this);
        viewPager2 = findViewById(R.id.viewpager_form);
        viewPager2.setAdapter(adapter);
        tabLayout = findViewById(R.id.tablayout_form);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if(position == 0) tab.setText("Log In");
            else if(position == 1) tab.setText("Sign Up");
        }).attach();

    }
}