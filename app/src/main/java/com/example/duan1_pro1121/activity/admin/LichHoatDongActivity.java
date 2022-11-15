package com.example.duan1_pro1121.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.model.Pitch;

import java.util.ArrayList;
import java.util.List;

public class LichHoatDongActivity extends AppCompatActivity {

    private ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img15,img16,img17,img18,img19,img20,img21,img22,img23,img24;
    private ImageView img25,img26,img27,img28,img29,img30,img31,img32,img33,img34,img35,img36,img37,img38,img39,img40,img41,img42,img43,img44,img45,img46,img47,img48;
    private List<ImageView> imageViewList = new ArrayList<>();
    private TextView tvBack;

    private Pitch pitch;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_hoat_dong);

        pitch = (Pitch) getIntent().getSerializableExtra("PITCH");
        date = getIntent().getStringExtra("DATE");

        Log.e("123","Pitch" + pitch.getStatus());
        Log.e("123","date "+date);

        tvBack = findViewById(R.id.tvBackLichHoatDong);
        tvBack.setText("Lịch đấu của "+pitch.getName()+" ngày "+date);
        tvBack.setOnClickListener(v->{
            finish();
        });

    }

    public void addTextView(){

    }
}