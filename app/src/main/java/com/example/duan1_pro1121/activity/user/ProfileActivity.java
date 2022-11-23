package com.example.duan1_pro1121.activity.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.SelectTypeActivity;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.fragment.adminfragment.KhachHangFragment;

public class ProfileActivity extends AppCompatActivity {

    public static final int CHANGE_INFO_CODE = 2;
    public static final int CHANGE_INFO_PASS = 3;

    private ImageView img,imgBack;
    private Button btnNap,btnLogout;
    private TextView tvName,tvMoney,tvShowInfo,tvChangePass,tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
        setOnClick();
        setUp();

    }

    public void setOnClick(){
        tvShowInfo.setOnClickListener(v->{
            Intent intent = new Intent(this,ChangeProfileActivity.class);
            startActivityForResult(intent,CHANGE_INFO_CODE);
        });
        tvHistory.setOnClickListener(v->{
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
        tvChangePass.setOnClickListener(v->{
            Intent intent = new Intent(this,ChangePassActivity.class);
            startActivityForResult(intent,CHANGE_INFO_PASS);
        });
        btnNap.setOnClickListener(v->{
            Intent intent = new Intent(this,NapTienActivity.class);
            intent.putExtra("CUSTOMER_ID",UserMainActivity.customer.getId());
            startActivityForResult(intent, KhachHangFragment.NAPTIEN_CODE);
        });
        btnLogout.setOnClickListener(v->{
            MyApplication.CURRENT_TYPE = -1;
            Intent intent = new Intent(this,SelectTypeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Animatoo.INSTANCE.animateShrink(this);
        });
        imgBack.setOnClickListener(v->{
            onBackPressed();
        });
    }

    public void initView(){
        img = findViewById(R.id.img_avatar_change_profile_activity);
        tvName = findViewById(R.id.tv_name_profile_activity);
        tvMoney = findViewById(R.id.tv_money_profile_activity);
        btnNap = findViewById(R.id.btn_naptien_profile_activity);
        btnLogout = findViewById(R.id.btn_logout);
        tvShowInfo = findViewById(R.id.tv_show_info_profile_activity);
        tvHistory = findViewById(R.id.tv_history_naptien_profile_activity);
        tvChangePass = findViewById(R.id.tv_change_pass_profile_activity);
        imgBack = findViewById(R.id.img_back_profile_activity);
    }

    public void setUp(){
        if(UserMainActivity.customer.getImg() == null){
            img.setImageResource(R.drawable.ic_avatar);
        }else{
            img.setImageBitmap(MyApplication.getBitMapFromByte(UserMainActivity.customer.getImg()));
        }
        tvName.setText(UserMainActivity.customer.getName());
        tvMoney.setText(MyApplication.convertMoneyToString(UserMainActivity.customer.getCoin()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == KhachHangFragment.NAPTIEN_CODE || requestCode == CHANGE_INFO_CODE
                || requestCode == CHANGE_INFO_PASS) && resultCode == RESULT_OK){
            UserMainActivity.customer = MyDatabase.getInstance(this).customerDAO()
                    .getCustomerWithID(UserMainActivity.customer.getId()).get(0);
            setUp();
        }
    }
}