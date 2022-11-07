package com.example.duan1_pro1121.fragment;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.LichHoatDongActivity;
import com.example.duan1_pro1121.adapter.RecyclerDatSanAdapter;
import com.example.duan1_pro1121.model.Pitch;

import java.util.ArrayList;
import java.util.List;

public class DatSanFragment extends Fragment {
    public static List<Pitch> list = new ArrayList<>();
    RadioButton rd5,rd7,rd11,rdAll;
    RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
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
        rd5 = view.findViewById(R.id.rd_5);
        rd5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                List<Pitch> list1 = new ArrayList<>();
                for(int i = 0;i<list.size();i++){
                    if(list.get(i).getCategoryId() == 0){
                        list1.add(list.get(i));
                    }
                    setUp(list1);
                }
            }
        });
        rd7 = view.findViewById(R.id.rd_7);
        rd7.setOnCheckedChangeListener((btn,isChecked)->{
            if(isChecked){
                List<Pitch> list1 = new ArrayList<>();
                for(int i = 0;i<list.size();i++){
                    if(list.get(i).getCategoryId() == 1){
                        list1.add(list.get(i));
                    }
                    setUp(list1);
                }
            }
        });
        rd11 = view.findViewById(R.id.rd_11);
        rd11.setOnCheckedChangeListener((btn,isChecked)->{
            if(isChecked){
                List<Pitch> list1 = new ArrayList<>();
                for(int i = 0;i<list.size();i++){
                    if(list.get(i).getCategoryId() == 2){
                        list1.add(list.get(i));
                    }
                    setUp(list1);
                }
            }
        });
        rdAll = view.findViewById(R.id.rd_all);
        rdAll.setOnCheckedChangeListener((btn,isChecked)->{
            if(isChecked){
                setUp(list);
            }
        });

        recyclerView = view.findViewById(R.id.recycler_datsan);
        setUp(list);
    }

    public void setUp(List<Pitch> listX){
        RecyclerDatSanAdapter adapter =new RecyclerDatSanAdapter(getContext(),listX);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));
    }

    public void setData(){
        if(list.size()>0) return;
        list.add(new Pitch(1,"Sân A1",0,0));
        list.add(new Pitch(1,"Sân A2",2,1));
        list.add(new Pitch(1,"Sân A3",1,0));
        list.add(new Pitch(1,"Sân B1",0,1));
        list.add(new Pitch(1,"Sân A4",2,0));
        list.add(new Pitch(1,"Sân B2",1,0));
        list.add(new Pitch(1,"Sân B3",0,0));
        list.add(new Pitch(1,"Sân C1",2,1));
        list.add(new Pitch(1,"Sân A4",2,0));
        list.add(new Pitch(1,"Sân B2",1,0));
        list.add(new Pitch(1,"Sân B3",0,0));
        list.add(new Pitch(1,"Sân C1",2,1));
    }

}