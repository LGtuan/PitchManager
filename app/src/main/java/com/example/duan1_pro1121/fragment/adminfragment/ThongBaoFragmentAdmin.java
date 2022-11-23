package com.example.duan1_pro1121.fragment.adminfragment;

import android.app.Dialog;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.admin.NotificationAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.MyNotification;
import com.example.duan1_pro1121.model.NotificationDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class ThongBaoFragmentAdmin extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton btn;
    private List<MyNotification> list;
    private NotificationAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = MyDatabase.getInstance(getContext()).notificationDAO().getALl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_thong_bao_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.btn_create_dialog_add_thongbao);
        btn.setOnClickListener(v->{
            createDialogAdd();
        });
        adapter = new NotificationAdapter(getContext(),list);
        recyclerView = view.findViewById(R.id.recy_thongbao_fragment);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    public void createDialogAdd(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_thongbao);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtTitle = dialog.findViewById(R.id.edt_title_dialog_add_thongbao);
        EditText edtContent = dialog.findViewById(R.id.edt_content_dialog_thongbao);
        RadioButton rdoHoatDong = dialog.findViewById(R.id.rdo_hoatdong_dialog_add_thongbao);
        Button btn = dialog.findViewById(R.id.btn_add_thongbao);
        btn.setOnClickListener(v->{
            if(!edtTitle.getText().toString().equals("") && !edtContent.getText().toString().equals("")){
                MyNotification notification = new MyNotification();
                if(rdoHoatDong.isChecked())notification.setStatus(MyApplication.HOATDONG_STATUS);
                else notification.setStatus(MyApplication.BAOTRI_STATUS);
                notification.setContent(edtContent.getText().toString());
                notification.setTitle(edtTitle.getText().toString());
                Calendar calendar = Calendar.getInstance();
                notification.setDate(calendar.get(Calendar.DATE)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR));

                MyDatabase.getInstance(getContext()).notificationDAO().insert(notification);
                list = MyDatabase.getInstance(getContext()).notificationDAO().getALl();
                adapter.setData(list);

                Cursor cursor = MyDatabase.getInstance(getContext()).customerDAO().getAllId();
                while (cursor.moveToNext()){
                    int cusId = cursor.getInt(0);
                    NotificationDetails details = new NotificationDetails();
                    details.setCustomerId(cusId);
                    details.setNotificationId(notification.getId());
                    MyDatabase.getInstance(getContext()).notificationDetailsDAO().insert(details);
                }
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}