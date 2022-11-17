package com.example.duan1_pro1121.fragment.adminfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.DatSanChiTietActivity;
import com.example.duan1_pro1121.adapter.admin.OrderAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.OrderDetails;
import com.example.duan1_pro1121.model.ServiceBall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhieuThongTinFragment extends Fragment {
    public static List<Order> ordersList;
    OrderAdapter adapter;
    RecyclerView recyclerView;
    private EditText edtFind;
    private ImageView imgFind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordersList = MyDatabase.getInstance(getContext()).orderDAO().getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phieu_thong_tin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_order);

        imgFind = view.findViewById(R.id.img_search_phieu_thong_tin_fragment);
        edtFind = view.findViewById(R.id.edt_searchPhieuThongTin);

        imgFind.setOnClickListener(v -> {
            if (edtFind.getText().toString().equals("")) adapter.setData(ordersList);
            else {
                int x;
                try {
                    x = Integer.parseInt(edtFind.getText().toString());
                } catch (NumberFormatException e) {
                    return;
                }
                adapter.setData(MyDatabase.getInstance(getContext()).orderDAO().getOrderWithID(x));
            }
        });

        adapter = new OrderAdapter(ordersList, getContext());
        adapter.setOnClick(order -> {
            Intent intent = new Intent(getContext(),DatSanChiTietActivity.class);
            intent.putExtra("ORDER",order);
            Bundle bundle = new Bundle();

            List<ServiceBall> serviceBalls = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>();

            List<OrderDetails> list = MyDatabase.getInstance(getContext()).orderDetailsDAO().getOrderDetailsByOrderId(order.getId());
            for(int i = 0;i<list.size();i++){
                int serviceId = list.get(i).getServiceId();
                int number = list.get(i).getSoLuong();
                serviceBalls.add(MyDatabase.getInstance(getContext()).serviceDAO().getServiceWithId(serviceId).get(0));
                numbers.add(number);
            }

            bundle.putSerializable("LIST_SERVICE", (Serializable) serviceBalls);
            bundle.putSerializable("LIST_NUMBER", (Serializable) numbers);

            intent.putExtra("bundle",bundle);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        ordersList = MyDatabase.getInstance(getContext()).orderDAO().getAll();
        adapter.setData(ordersList);
    }
}