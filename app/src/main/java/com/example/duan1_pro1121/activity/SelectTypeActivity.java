package com.example.duan1_pro1121.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.FormActivity;

public class SelectTypeActivity extends AppCompatActivity {

    LinearLayout linearLayoutAdmin, linearLayoutUser;
    TextView tvAdmin, tvUser;
    Button btnConfirm;

    boolean isUserRoom = false;
    boolean isAdminRoom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        btnConfirm = findViewById(R.id.btn_confirn_select_type);
        linearLayoutAdmin = findViewById(R.id.linear_select_type_admin);
        linearLayoutUser = findViewById(R.id.linear_select_type_user);
        tvAdmin = findViewById(R.id.text_view_select_type_admin);
        tvUser = findViewById(R.id.text_view_select_type_user);

        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(linearLayoutAdmin,"scaleX",1.25f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(linearLayoutAdmin,"scaleY",1.2f);
        animatorScaleX.setDuration(400);
        animatorScaleY.setDuration(400);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorScaleX,animatorScaleY);

        ObjectAnimator animatorScaleX2 = ObjectAnimator.ofFloat(linearLayoutUser,"scaleX",1.25f);
        ObjectAnimator animatorScaleY2 = ObjectAnimator.ofFloat(linearLayoutUser,"scaleY",1.2f);
        animatorScaleX2.setDuration(400);
        animatorScaleY2.setDuration(400);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorScaleX2,animatorScaleY2);

        ObjectAnimator animatorScaleX3 = ObjectAnimator.ofFloat(linearLayoutAdmin,"scaleX",1f);
        ObjectAnimator animatorScaleY3 = ObjectAnimator.ofFloat(linearLayoutAdmin,"scaleY",1f);
        animatorScaleX3.setDuration(400);
        animatorScaleY3.setDuration(400);
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorScaleX3,animatorScaleY3);

        ObjectAnimator animatorScaleX4 = ObjectAnimator.ofFloat(linearLayoutUser,"scaleX",1f);
        ObjectAnimator animatorScaleY4 = ObjectAnimator.ofFloat(linearLayoutUser,"scaleY",1f);
        animatorScaleX4.setDuration(400);
        animatorScaleY4.setDuration(400);
        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(animatorScaleX4,animatorScaleY4);

        linearLayoutAdmin.setOnClickListener(v -> {
            if (MyApplication.CURRENT_TYPE != MyApplication.TYPE_ADMIN) {
                btnConfirm.setEnabled(true);
                btnConfirm.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_background));
                if(!isAdminRoom) {
                    linearLayoutAdmin.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_background));
                    animatorSet.start();
                    isAdminRoom = true;
                }
                if(isUserRoom) {
                    linearLayoutUser.setBackground(AppCompatResources.getDrawable(this, R.drawable.border_item_naptien));
                    animatorSet4.start();
                    isUserRoom = false;
                }
                tvAdmin.setTextSize(16);
                tvUser.setTextSize(13);
                tvAdmin.setTextColor(getResources().getColor(R.color.white));
                tvUser.setTextColor(getResources().getColor(R.color.dark_gray));

                MyApplication.CURRENT_TYPE = MyApplication.TYPE_ADMIN;
            }
        });

        linearLayoutUser.setOnClickListener(v -> {
            if (MyApplication.CURRENT_TYPE != MyApplication.TYPE_USER) {
                btnConfirm.setEnabled(true);
                btnConfirm.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_background));

                if(isAdminRoom) {
                    linearLayoutAdmin.setBackground(AppCompatResources.getDrawable(this, R.drawable.border_item_naptien));
                    animatorSet3.start();
                    isAdminRoom = false;
                }
                if(!isUserRoom) {
                    linearLayoutUser.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_background));
                    animatorSet2.start();
                    isUserRoom = true;
                }
                tvAdmin.setTextSize(13);
                tvUser.setTextSize(16);
                tvAdmin.setTextColor(getResources().getColor(R.color.dark_gray));
                tvUser.setTextColor(getResources().getColor(R.color.white));

                MyApplication.CURRENT_TYPE = MyApplication.TYPE_USER;
            }
        });

        btnConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormActivity.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateShrink(this);
        });
    }

}