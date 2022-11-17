package com.example.duan1_pro1121.fragment.adminfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.FormActivity;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;

public class SignupFragment extends Fragment {

    private EditText edtPhone,edtName,edtAddress,edtPass,edtPass2;
    private TextView tvCheckAddress,tvCheckName,tvCheckPass,tvCheckPass2,tvCheckPhone;
    private Button btnSignUp;
    FormActivity formActivity;

    public SignupFragment(FormActivity formActivity){
        this.formActivity = formActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtPhone = view.findViewById(R.id.edt_phone_signUp);
        edtName = view.findViewById(R.id.edtNameSignUp);
        edtPass = view.findViewById(R.id.edt_pass_fragment_signup);
        edtPass2 = view.findViewById(R.id.edt_pass2_fragment_signup);
        edtAddress = view.findViewById(R.id.edtAddressSignUp);

        tvCheckAddress = view.findViewById(R.id.tv_check_address_signup);
        tvCheckName = view.findViewById(R.id.tv_check_name_signup);
        tvCheckPass = view.findViewById(R.id.tv_check_pass1_signup);
        tvCheckPass2 = view.findViewById(R.id.tv_check_pass2_signup);
        tvCheckPhone = view.findViewById(R.id.tv_check_phone_signup);

        btnSignUp = view.findViewById(R.id.btn_signUp);
        btnSignUp.setOnClickListener(v->{
            signUp();
        });
    }

    public void signUp(){
        String phone = edtPhone.getText().toString();
        String name = edtName.getText().toString();
        String pass = edtPass.getText().toString();
        String pass2 = edtPass2.getText().toString();
        String address = edtAddress.getText().toString();

        if(!phone.matches(MyApplication.PHONE_REGEX)){
            tvCheckPhone.setText("* Số điện thoại không hợp lệ");
            invisible(tvCheckAddress,tvCheckName,tvCheckPass,tvCheckPhone,tvCheckPass2);
            tvCheckPhone.setVisibility(View.VISIBLE);
        }else if(!name.matches(MyApplication.NAME_REGEX)){
            invisible(tvCheckAddress,tvCheckName,tvCheckPass,tvCheckPhone,tvCheckPass2);
            tvCheckName.setVisibility(View.VISIBLE);
        }else if(!address.matches(MyApplication.ADDRESS_REGEX)){
            invisible(tvCheckAddress,tvCheckName,tvCheckPass,tvCheckPhone,tvCheckPass2);
            tvCheckAddress.setVisibility(View.VISIBLE);
        } else if(!pass.matches(MyApplication.PASS_REGEX)){
            invisible(tvCheckAddress,tvCheckName,tvCheckPass,tvCheckPhone,tvCheckPass2);
            tvCheckPass.setVisibility(View.VISIBLE);
        }else if(!pass.equals(pass2)){
            invisible(tvCheckAddress,tvCheckName,tvCheckPass,tvCheckPhone,tvCheckPass2);
            tvCheckPass2.setVisibility(View.VISIBLE);
        }else{
            Customer customer = new Customer();
            if(MyDatabase.getInstance(getContext()).customerDAO().getCustomerWithPhone(phone,-1).size()==0) {
                customer.setPhone(phone);
                customer.setName(name);
                customer.setPassword(pass);
                customer.setAddress(address);

                MyDatabase.getInstance(getContext()).customerDAO().insert(customer);
                Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                reset();
                invisible(tvCheckAddress,tvCheckName,tvCheckPass,tvCheckPass2,tvCheckPhone);
                formActivity.registerSuccess(customer.getPhone(),customer.getPassword());
            }else{
                tvCheckPhone.setText("* Số điện thoại đã tồn tại");
                invisible(tvCheckAddress,tvCheckName,tvCheckPhone,tvCheckPass,tvCheckPass2);
                tvCheckPhone.setVisibility(View.VISIBLE);
            }
        }
    }

    public void reset(){
        edtAddress.setText("");
        edtPass.setText("");
        edtName.setText("");
        edtPhone.setText("");
        edtPass2.setText("");
    }

    public void invisible(TextView ...tvs){
        for(TextView tv : tvs){
            tv.setVisibility(View.INVISIBLE);
        }
    }
}
