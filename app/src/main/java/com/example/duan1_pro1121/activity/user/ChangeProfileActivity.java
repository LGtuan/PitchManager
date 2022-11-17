package com.example.duan1_pro1121.activity.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ChangeProfileActivity extends AppCompatActivity {

    public static final int PHOTO_REQUEST_CODE = 1;

    private EditText edtPhone, edtName, edtCmnd, edtAddress;
    private TextView tv1, tv2, tv3;
    private ImageView img, imgEdit;
    private Button btnUpdate;
    private byte[] bytes;
    Customer customer = UserMainActivity.customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        initView();
        setOnClick();

        edtPhone.setText(customer.getPhone());
        edtAddress.setText(customer.getAddress());
        edtCmnd.setText(customer.getCmnd());
        edtName.setText(customer.getName());
    }

    public void setOnClick() {
        imgEdit.setOnClickListener(v -> {
            ImagePicker.with(this).crop().maxResultSize(120,120).start(PHOTO_REQUEST_CODE);
        });
        btnUpdate.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString();
            String name = edtName.getText().toString();
            String cmnd = edtCmnd.getText().toString();
            String address = edtAddress.getText().toString();

            if (!phone.matches(MyApplication.PHONE_REGEX)) {
                tv1.setText("* Số điện thoại không hợp lệ");
                invisible(tv1, tv2, tv3);
                tv1.setVisibility(View.VISIBLE);
            } else if (!name.matches(MyApplication.NAME_REGEX)) {
                invisible(tv2, tv2, tv3);
                tv2.setVisibility(View.VISIBLE);
            } else if (!address.matches(MyApplication.ADDRESS_REGEX)) {
                invisible(tv1, tv2, tv3);
                tv3.setVisibility(View.VISIBLE);
            } else {
                if (MyDatabase.getInstance(this).customerDAO().getCustomerWithPhone(phone, customer.getId()).size() == 0) {
                    customer.setPhone(phone);
                    customer.setName(name);
                    customer.setCmnd(cmnd);
                    customer.setAddress(address);

                    if(bytes!=null) customer.setImg(bytes);

                    MyDatabase.getInstance(this).customerDAO().update(customer);
                    Toast.makeText(this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();

                    setResult(RESULT_OK);
                    finish();
                } else {
                    tv1.setText("* Số điện thoại đã tồn tại");
                    invisible(tv1, tv2, tv3);
                    tv1.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    public void invisible(TextView... tvs) {
        for (TextView tv : tvs) {
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public void initView() {
        edtPhone = findViewById(R.id.edt_phone_change_profile_activity);
        edtName = findViewById(R.id.edt_name_change_profile_activity);
        edtCmnd = findViewById(R.id.edt_cmnd_change_profile_activity);
        edtAddress = findViewById(R.id.edt_address_change_profile_activity);

        tv2 = findViewById(R.id.tv_check_name_change_profile_activity);
        tv1 = findViewById(R.id.tv_check_phone_change_profile_activity);
        tv3 = findViewById(R.id.tv_check_address_change_profile_activity);

        img = findViewById(R.id.img_avatar_change_profile_activity);
        imgEdit = findViewById(R.id.btn_edit_img_profile);
        btnUpdate = findViewById(R.id.btn_update_change_profile);
    }

    public byte[] getBytes(Uri uri) throws FileNotFoundException {
        InputStream iStream = getContentResolver().openInputStream(uri);
        try {
            return getBytes(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ /* do nothing */ }
        }
        return bytesResult;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            Uri uri = data.getData();
            try {
                bytes = getBytes(uri);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}