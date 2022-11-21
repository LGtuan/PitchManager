package com.example.duan1_pro1121.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.user.UserMainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    TextView tv;

    String textAdmin = "Bạn sẽ quản lý sân bóng dễ dàng hơn nhờ Soccer Management.";
    String textAdmin2 = "Bạn sẽ quản lý khách hàng dễ dàng hơn nhờ Soccer Management.";
    String textAdmin3 = "Bạn sẽ tích kiệm được thời gian quản lý nếu dùng Soccer Management";
    String textAdmin4 = "Bạn đã biết, có thể thêm sửa dịch vụ trong Soccer Management";
    List<String> listStringAdmin = new ArrayList<>(Arrays.asList(textAdmin,textAdmin2,textAdmin3,textAdmin4));

    String textUser = "Bạn sẽ tích kiệm thời gian đặt sân với Soccer Management.";
    String textUser2 = "Bạn đã biết, có thể thay đổi avatar trong profile.";
    String textUser3 = "Bạn có thể đặt sân bóng online nhanh chóng với Soccer Management";
    String textUser4 = "Bạn có thể xem lại lịch sử đặt sân trong phần lịch sử đặt hàng";
    List<String> listStringUser = new ArrayList<>(Arrays.asList(textUser,textUser2,textUser3,textUser4));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String stk = getIntent().getStringExtra("account");

        lottieAnimationView = findViewById(R.id.lottie_splsah);
        int posRand = new Random().nextInt(3);
        tv = findViewById(R.id.tv_splash);
        if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN){
            lottieAnimationView.setAnimation(R.raw.admin_anim);
            tv.setText(listStringAdmin.get(posRand));
        }else{
            lottieAnimationView.setAnimation(R.raw.user_anim);
            tv.setText(listStringUser.get(posRand));
        }


        new Handler().postDelayed(() -> {
            if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("account",stk);
                startActivity(intent);
            }else if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_USER){
                Intent intent = new Intent(SplashActivity.this, UserMainActivity.class);
                intent.putExtra("account",stk);
                startActivity(intent);
            }
        },4000);
    }

    @Override
    public void onBackPressed() {
    }
}