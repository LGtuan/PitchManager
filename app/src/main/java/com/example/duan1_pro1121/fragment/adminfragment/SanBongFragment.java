package com.example.duan1_pro1121.fragment.adminfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.model.Pitch;

import java.util.ArrayList;
import java.util.List;

public class SanBongFragment extends Fragment {

    public static List<Pitch> list = new ArrayList<>();
    CheckBox cbx5,cbx7,cbx11;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_san_bong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void setUp(){

    }

    public void setData(){
        list.add(new Pitch(1,"Sân A1",1,0));
        list.add(new Pitch(1,"Sân A2",2,1));
        list.add(new Pitch(1,"Sân A3",1,0));
        list.add(new Pitch(1,"Sân B1",3,1));
        list.add(new Pitch(1,"Sân A4",2,0));
        list.add(new Pitch(1,"Sân B2",1,0));
        list.add(new Pitch(1,"Sân B3",3,0));
        list.add(new Pitch(1,"Sân C1",2,1));
    }
}