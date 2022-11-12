package com.example.duan1_pro1121.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.user.UserMainActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String stk = getIntent().getStringExtra("account");

        new Handler().postDelayed(() -> {
            if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("account",stk);
                startActivity(intent);
            }else if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_USER){
                Intent intent = new Intent(SplashActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        },3000);
    }

    @Override
    public void onBackPressed() {
    }
}