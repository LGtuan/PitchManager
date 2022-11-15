package com.example.duan1_pro1121.fragment.adminfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.admin.OrderAdapter;
import com.example.duan1_pro1121.adapter.admin.PitchAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.Pitch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhieuThongTinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhieuThongTinFragment extends Fragment {
    public static List<Order> ordersList;
    private FloatingActionButton button;
    OrderAdapter adapter;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhieuThongTinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhieuThongTinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhieuThongTinFragment newInstance(String param1, String param2) {
        PhieuThongTinFragment fragment = new PhieuThongTinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ordersList = MyDatabase.getInstance(getContext()).orderDAO().getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phieu_thong_tin, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_order);
        ImageView find = view.findViewById(R.id.img_search_customerFragment);

        adapter = new OrderAdapter(ordersList,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }


}