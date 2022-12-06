package com.example.duan1_pro1121.fragment.userfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.user.UserDatSanChiTietActivity;
import com.example.duan1_pro1121.activity.user.UserMainActivity;
import com.example.duan1_pro1121.adapter.admin.SpinnerLoaiNVAdapter;
import com.example.duan1_pro1121.adapter.user.HistoryDatSanAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.MyTime;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.OrderDetails;
import com.example.duan1_pro1121.model.ServiceBall;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HistoryFragment extends Fragment {

    private TextView tvCount;
    private Spinner spinner;
    private List<Order> orders;
    private RecyclerView recyclerView;
    HistoryDatSanAdapter adapter;
    public static int CODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataWithStatus(-1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCount = view.findViewById(R.id.tv_count_order_user);
        spinner = view.findViewById(R.id.spn_type_order_user);
        recyclerView = view.findViewById(R.id.recy_history_user);

        adapter = new HistoryDatSanAdapter(getContext(), orders);
        adapter.setMyOnClick(order -> {
            Intent intent = new Intent(getContext(), UserDatSanChiTietActivity.class);
            intent.putExtra("ORDER", order);
            Bundle bundle = new Bundle();

            List<ServiceBall> serviceBalls = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>();

            List<OrderDetails> list = MyDatabase.getInstance(getContext()).orderDetailsDAO().getOrderDetailsByOrderId(order.getId());
            for (int i = 0; i < list.size(); i++) {
                int serviceId = list.get(i).getServiceId();
                int number = list.get(i).getSoLuong();
                serviceBalls.add(MyDatabase.getInstance(getContext()).serviceDAO().getServiceWithId(serviceId).get(0));
                numbers.add(number);
            }

            bundle.putSerializable("LIST_SERVICE", (Serializable) serviceBalls);
            bundle.putSerializable("LIST_NUMBER", (Serializable) numbers);

            intent.putExtra("bundle", bundle);
            startActivityForResult(intent, CODE);
        });
        recyclerView.setAdapter(adapter);
        setUp();

        ArrayList<String> list = new ArrayList<>(Arrays.asList("Tất cả", "Chưa đá", "Đang đá", "Đang nghỉ", "Đã đá"));
        MyCustomSpinnerAdapter adapter1 = new MyCustomSpinnerAdapter(getContext(), R.layout.line_spinner_loai_nv, list);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setDataWithStatus(-1);
                } else if (position == 1) {
                    setDataWithStatus(MyApplication.CHUA_STATUS);
                } else if (position == 2) {
                    setDataWithStatus(MyApplication.DANG_STATUS);
                } else if (position == 3) {
                    setDataWithStatus(MyApplication.NGHI_STATUS);
                } else if (position == 4) {
                    setDataWithStatus(MyApplication.DA_STATUS);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
            adapter.setData(MyDatabase.getInstance(getContext()).orderDAO().getOrderWithCustomerId(UserMainActivity.customer.getId()));
        }
    }

    public void setDataWithStatus(int status) {
        if (status == -1) {
            orders = MyDatabase.getInstance(getContext()).orderDAO()
                    .getOrderWithCustomerId(UserMainActivity.customer.getId());
            if (adapter != null) {
                setUp();
            }
        } else {
            if (status == MyApplication.CHUA_STATUS) {
                orders = MyDatabase.getInstance(getContext()).orderDAO()
                        .getOrderWithCustomerIdAndStatus(UserMainActivity.customer.getId(), MyApplication.CHUA_STATUS);
            } else if (status == MyApplication.DANG_STATUS) {
                orders = MyDatabase.getInstance(getContext()).orderDAO()
                        .getOrderWithCustomerIdAndStatus(UserMainActivity.customer.getId(), MyApplication.DANG_STATUS);
            } else if (status == MyApplication.NGHI_STATUS) {
                orders = MyDatabase.getInstance(getContext()).orderDAO()
                        .getOrderWithCustomerIdAndStatus(UserMainActivity.customer.getId(), MyApplication.NGHI_STATUS);
            } else if (status == MyApplication.DA_STATUS) {
                orders = MyDatabase.getInstance(getContext()).orderDAO()
                        .getOrderWithCustomerIdAndStatus(UserMainActivity.customer.getId(), MyApplication.DA_STATUS);
            }
            updateOrder();
            setUp();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(), "123", Toast.LENGTH_SHORT).show();
    }

    public void updateOrder() {
        for (int position = 0; position < orders.size(); position++) {

            if (orders.get(position).getStatus() != MyApplication.HUY_STATUS) {

                Calendar calendarStart = Calendar.getInstance();
                Calendar calendarEnd = Calendar.getInstance();
                Calendar calendarNow = Calendar.getInstance();

                List<MyTime> myTimeList = MyDatabase.getInstance(getContext())
                        .timeDAO().getTimeWithOrderId(orders.get(position).getId());

                int beginStatus = orders.get(position).getStatus();

                for (int i = 0; i < myTimeList.size(); i++) {
                    int[] arr = getArrayDate(orders.get(position).getDatePlay());
                    calendarStart.set(arr[2], arr[1] - 1, arr[0], myTimeList.get(i).getStartTime(), 0);
                    calendarEnd.set(arr[2], arr[1] - 1, arr[0], myTimeList.get(i).getEndTime(), 0);

                    if (i == 0 && calendarStart.after(calendarNow)) {
                        orders.get(position).setStatus(MyApplication.CHUA_STATUS);
                        break;
                    }
                    if (i == myTimeList.size() - 1 && calendarEnd.before(calendarNow)) {
                        orders.get(position).setStatus(MyApplication.DA_STATUS);
                        break;
                    }
                    if (calendarStart.before(calendarNow) && calendarEnd.after(calendarNow)) {
                        orders.get(position).setStatus(MyApplication.DANG_STATUS);
                        break;
                    } else {
                        orders.get(position).setStatus(MyApplication.NGHI_STATUS);
                    }

                }

                if (beginStatus != orders.get(position).getStatus()) {
                    MyDatabase.getInstance(getContext()).orderDAO().update(orders.get(position));
                }
            }
        }
    }

    public int[] getArrayDate(String date) {
        String[] str = date.split("-");
        int arr[] = new int[str.length];
        try {
            for (int i = 0; i < str.length; i++) {
                arr[i] = Integer.parseInt(str[i]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return arr;
    }

    public void setUp() {
        adapter.setData(orders);
        tvCount.setText(orders.size() + "");
    }

    public class MyCustomSpinnerAdapter extends ArrayAdapter<String> {

        private Context context;
        private List<String> list;

        public MyCustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.list = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.line_spinner_loai_nv, parent, false);
                viewHolder.tv = convertView.findViewById(R.id.tv_name_loainv_line_spinner);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv.setText(list.get(position));

            return convertView;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.line_spinner_loai_nv_dropdown, parent, false);
                viewHolder.tv = convertView.findViewById(R.id.tv_name_loainv_line_spinner_dropdown);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv.setText(list.get(position));

            return convertView;
        }

        public class ViewHolder {
            TextView tv;
        }
    }
}