package com.example.duan1_pro1121.fragment.adminfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RadioButton;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.DatSanChiTietActivity;
import com.example.duan1_pro1121.activity.user.UserDatSanChiTietActivity;
import com.example.duan1_pro1121.adapter.admin.RecyclerDatSanAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Pitch;

import java.util.ArrayList;
import java.util.List;

public class DatSanFragment extends Fragment {

    public static List<Pitch> list;
    RadioButton rd5,rd7,rd11,rdAll;
    RecyclerView recyclerView;
    RecyclerDatSanAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = MyDatabase.getInstance(getContext()).pitchDao().getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dat_san, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_datsan);
        adapter =new RecyclerDatSanAdapter(getContext(),list);
        adapter.setOnClickDatSan(pitch -> {
            if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN) {
                Intent intent = new Intent(getContext(), DatSanChiTietActivity.class);
                intent.putExtra("PITCH",pitch);
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(getContext());
            }else if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_USER){
                Intent intent = new Intent(getContext(), UserDatSanChiTietActivity.class);
                intent.putExtra("PITCH",pitch);
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(getContext());
            }
        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));

        rd5 = view.findViewById(R.id.rd_5);
        rd7 = view.findViewById(R.id.rd_7);
        rd11 = view.findViewById(R.id.rd_11);
        rdAll = view.findViewById(R.id.rd_all);
        rd5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                        .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_5));
        });
        rd7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                        .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_7));
        });
        rd11.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                        .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_11));
        });
        rdAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                adapter.setData(list);
        });
    }
}