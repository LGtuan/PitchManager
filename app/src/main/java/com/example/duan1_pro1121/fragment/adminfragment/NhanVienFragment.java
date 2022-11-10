package com.example.duan1_pro1121.fragment.adminfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.model.Manager;

import java.util.List;

public class NhanVienFragment extends Fragment {

    private List<Manager> list;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nhan_vien, container, false);
    }


}