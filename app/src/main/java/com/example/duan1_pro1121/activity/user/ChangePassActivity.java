package com.example.duan1_pro1121.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.MainActivity;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.Manager;

public class ChangePassActivity extends AppCompatActivity {

    private EditText edt1,edt2,edt3;
    private TextView tv1,tv2,tv3;
    private ImageView imgBack;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        initView();

        imgBack.setOnClickListener(v->{
            onBackPressed();
        });
        btn.setOnClickListener(v->{
            if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_USER) {
                String passNow = UserMainActivity.customer.getPassword();
                String passUser = edt1.getText().toString();

                if(checkPass(passNow,passUser)){
                    UserMainActivity.customer.setPassword(passUser);
                    MyDatabase.getInstance(this).customerDAO().update(UserMainActivity.customer);
                    Toast.makeText(this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }

            }else{
                Manager manager = MyDatabase.getInstance(this).managerDAO().getManagerWithPhone(MainActivity.ACCOUNT,-1).get(0);
                String passNow = manager.getPassword();
                String passAdmin = edt1.getText().toString();

                if(checkPass(passNow,passAdmin)){
                    manager.setPassword(passAdmin);
                    MyDatabase.getInstance(this).managerDAO().update(manager);
                    Toast.makeText(this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public boolean checkPass(String pass1,String pass2){
        if (!pass1.equals(pass2)) {
            invisibleAll(tv1, tv2, tv3);
            tv1.setVisibility(View.VISIBLE);
            return false;
        } else {
            String passNew = edt2.getText().toString();
            if (!passNew.matches(MyApplication.PASS_REGEX)) {
                invisibleAll(tv1, tv2, tv3);
                tv2.setVisibility(View.VISIBLE);
                return false;
            } else {
                String passNew2 = edt3.getText().toString();
                if (!passNew.equals(passNew2)) {
                    invisibleAll(tv1, tv2, tv3);
                    tv3.setVisibility(View.VISIBLE);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public void invisibleAll(TextView ...tvs){
        for (TextView tv : tvs){
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public void initView(){
        edt1 = findViewById(R.id.edt_pass_now);
        edt2 = findViewById(R.id.edt_pass_new1);
        edt3 = findViewById(R.id.edt_pass_new2);
        tv1 = findViewById(R.id.tv_check_pass1_change_pass_activity);
        tv2 = findViewById(R.id.tv_check_pass2_change_pass_activity);
        tv3 = findViewById(R.id.tv_check_pass3_change_pass_activity);
        imgBack = findViewById(R.id.img_back_change_pass_activity);
        btn = findViewById(R.id.btn_confirn_change_pass_activity);
    }
}