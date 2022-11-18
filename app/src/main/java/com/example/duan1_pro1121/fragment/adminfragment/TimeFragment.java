package com.example.duan1_pro1121.fragment.adminfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.admin.CaThiDauAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.MyTime;

import java.util.List;

public class TimeFragment extends Fragment {

    private List<MyTime> list;
    private RecyclerView recyclerView;
    private CaThiDauAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = MyDatabase.getInstance(getContext()).timeDAO().getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_time_fragment);
        adapter = new CaThiDauAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }
}