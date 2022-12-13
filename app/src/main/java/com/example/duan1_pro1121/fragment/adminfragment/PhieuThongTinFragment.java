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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.DatSanChiTietActivity;
import com.example.duan1_pro1121.adapter.admin.OrderAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.MyTime;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.OrderDetails;
import com.example.duan1_pro1121.model.ServiceBall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
        updateOrder();
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
        updateOrder();
        adapter.setData(ordersList);
    }

    public void updateOrder(){
        for(int position = 0;position<ordersList.size();position++) {
            if(ordersList.get(position).getStatus() != MyApplication.HUY_STATUS) {

                Calendar calendarStart = Calendar.getInstance();
                Calendar calendarEnd = Calendar.getInstance();
                Calendar calendarNow = Calendar.getInstance();

                List<MyTime> myTimeList = MyDatabase.getInstance(getContext())
                        .timeDAO().getTimeWithOrderId(ordersList.get(position).getId());

                int beginStatus = ordersList.get(position).getStatus();

                for (int i = 0; i < myTimeList.size(); i++) {
                    int[] arr = getArrayDate(ordersList.get(position).getDatePlay());
                    calendarStart.set(arr[2], arr[1] - 1, arr[0], myTimeList.get(i).getStartTime(), 0);
                    calendarEnd.set(arr[2], arr[1] - 1, arr[0], myTimeList.get(i).getEndTime(), 0);

                    if (i == 0 && calendarStart.after(calendarNow)) {
                        ordersList.get(position).setStatus(MyApplication.CHUA_STATUS);
                        break;
                    }
                    if (i == myTimeList.size() - 1 && calendarEnd.before(calendarNow)) {
                        ordersList.get(position).setStatus(MyApplication.DA_STATUS);
                        break;
                    }
                    if (calendarStart.before(calendarNow) && calendarEnd.after(calendarNow)) {
                        ordersList.get(position).setStatus(MyApplication.DANG_STATUS);
                        break;
                    } else {
                        ordersList.get(position).setStatus(MyApplication.NGHI_STATUS);
                    }
                }

                if (beginStatus != ordersList.get(position).getStatus()) {
                    MyDatabase.getInstance(getContext()).orderDAO().update(ordersList.get(position));
                }
            }
        }
    }

    public int[] getArrayDate(String date){
        String[] str = date.split("-");
        int arr[] = new int[str.length];
        try{
            for(int i = 0;i<str.length;i++){
                arr[i] = Integer.parseInt(str[i]);
            }
        }catch (NumberFormatException e){
            return null;
        }
        return arr;
    }
}