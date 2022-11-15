package com.example.duan1_pro1121.fragment.adminfragment;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.CategoryPitchActivity;
import com.example.duan1_pro1121.adapter.admin.CustomerAdapter;
import com.example.duan1_pro1121.adapter.admin.PitchAdapter;
import com.example.duan1_pro1121.adapter.admin.RecyclerDatSanAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.Pitch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SanBongFragment extends Fragment {

    public static List<Pitch> pitchsList;
    private FloatingActionButton button;
    private TextView tv_show_loaisan;
    PitchAdapter adapter;
    RecyclerView recyclerView;
    private RadioButton rdoCategory5,rdoCategory7,rdoCategory11,rdoCategoryAll;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_san_bong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_pitch);
        button = view.findViewById(R.id.btn_create_dialog_add_pitch);
        button.setOnClickListener(v -> {
            createDialogAdd();
        });

        rdoCategory5 = view.findViewById(R.id.rd_5_sanbong_fragment);
        rdoCategory7 = view.findViewById(R.id.rd_7_sanbong_fragment);
        rdoCategory11 = view.findViewById(R.id.rd_11_sanbong_fragment);
        rdoCategoryAll = view.findViewById(R.id.rd_all_sanbong_fragment);
        rdoCategoryAll.setChecked(true);
        tv_show_loaisan = view.findViewById(R.id.tv_show_loaisan);
        tv_show_loaisan.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), CategoryPitchActivity.class);
            startActivity(intent);
        });

        rdoCategory5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                        .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_5));
        });
        rdoCategory7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                        .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_7));
        });
        rdoCategory11.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                        .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_11));
        });
        rdoCategoryAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                adapter.setData(pitchsList);
        });

        adapter = new PitchAdapter(getContext(), pitchsList);
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this::createDialogUpdate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    public void createDialogAdd() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_pitch);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvCheck = dialog.findViewById(R.id.tv_check_name_dialog_add_pitch);
        TextView tvMoney = dialog.findViewById(R.id.tv_money_dialog_add_pitch);

        EditText edtName = dialog.findViewById(R.id.edt_tensan_dialog_add_pitch);
        RadioButton rdoHoatDong = dialog.findViewById(R.id.rdo_hoatdong_dialog_add_pitch);
        RadioButton rdoBaoTri = dialog.findViewById(R.id.rdo_baotri_dialog_add_pitch);
        RadioButton rdo5 = dialog.findViewById(R.id.rdo_san5_dialog_add_pitch);
        RadioButton rdo7 = dialog.findViewById(R.id.rdo_san7_dialog_add_pitch);
        RadioButton rdo11 = dialog.findViewById(R.id.rdo_san11_dialog_add_pitch);

        rdo5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tvMoney.setText(MyApplication.convertMoneyToString(MyDatabase.getInstance(getContext())
                        .pitchCategoryDAO().getCategoryPitchWithId(MyApplication.ID_CATEGORY_PITCH_5).get(0).getMoney())+"VNĐ");
        });
        rdo7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tvMoney.setText(MyApplication.convertMoneyToString(MyDatabase.getInstance(getContext())
                        .pitchCategoryDAO().getCategoryPitchWithId(MyApplication.ID_CATEGORY_PITCH_7).get(0).getMoney())+"VNĐ");
        });
        rdo11.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tvMoney.setText(MyApplication.convertMoneyToString(MyDatabase.getInstance(getContext())
                        .pitchCategoryDAO().getCategoryPitchWithId(MyApplication.ID_CATEGORY_PITCH_11).get(0).getMoney())+"VNĐ");
        });

        Button btnThem = dialog.findViewById(R.id.btn_add_sanbong);
        btnThem.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            if (name.equals("")) {
                tvCheck.setText("Tên sân không được bỏ trống");
                tvCheck.setVisibility(View.VISIBLE);
            } else {
                if (MyDatabase.getInstance(getContext()).pitchDao().getPitchWithName(name).size() > 0) {
                    tvCheck.setText("Tên sân đã tồn tại");
                    tvCheck.setVisibility(View.VISIBLE);
                } else {
                    tvCheck.setVisibility(View.INVISIBLE);
                    Pitch pitch = new Pitch();
                    pitch.setName(name);

                    if (rdo5.isChecked()) pitch.setCategoryId(MyApplication.ID_CATEGORY_PITCH_5);
                    else if (rdo7.isChecked())
                        pitch.setCategoryId(MyApplication.ID_CATEGORY_PITCH_7);
                    else if (rdo11.isChecked())
                        pitch.setCategoryId(MyApplication.ID_CATEGORY_PITCH_11);

                    if (rdoHoatDong.isChecked()) pitch.setStatus(MyApplication.HOATDONG_STATUS);
                    else if (rdoBaoTri.isChecked()) pitch.setStatus(MyApplication.BAOTRI_STATUS);

                    MyDatabase.getInstance(getContext()).pitchDao().insert(pitch);
                    Toast.makeText(getContext(), "Thêm sân bóng thành công", Toast.LENGTH_SHORT).show();
                    pitchsList = MyDatabase.getInstance(getContext()).pitchDao().getAll();
                    adapter.setData(pitchsList);

                    rdoCategoryAll.setChecked(true);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void createDialogUpdate(Pitch p) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_pitch);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = dialog.findViewById(R.id.tv_title_dialog_add_pitch);
        tvTitle.setText("Cập nhật sân bóng");
        TextView tvCheck = dialog.findViewById(R.id.tv_check_name_dialog_add_pitch);

        EditText edtName = dialog.findViewById(R.id.edt_tensan_dialog_add_pitch);
        edtName.setText(p.getName());
        RadioButton rdoHoatDong = dialog.findViewById(R.id.rdo_hoatdong_dialog_add_pitch);
        RadioButton rdoBaoTri = dialog.findViewById(R.id.rdo_baotri_dialog_add_pitch);
        RadioButton rdo5 = dialog.findViewById(R.id.rdo_san5_dialog_add_pitch);
        RadioButton rdo7 = dialog.findViewById(R.id.rdo_san7_dialog_add_pitch);
        RadioButton rdo11 = dialog.findViewById(R.id.rdo_san11_dialog_add_pitch);

        TextView tvMoney = dialog.findViewById(R.id.tv_money_dialog_add_pitch);

        rdo5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tvMoney.setText(MyApplication.convertMoneyToString(MyDatabase.getInstance(getContext())
                        .pitchCategoryDAO().getCategoryPitchWithId(MyApplication.ID_CATEGORY_PITCH_5).get(0).getMoney()));
        });
        rdo7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tvMoney.setText(MyApplication.convertMoneyToString(MyDatabase.getInstance(getContext())
                        .pitchCategoryDAO().getCategoryPitchWithId(MyApplication.ID_CATEGORY_PITCH_7).get(0).getMoney()));
        });
        rdo11.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tvMoney.setText(MyApplication.convertMoneyToString(MyDatabase.getInstance(getContext())
                        .pitchCategoryDAO().getCategoryPitchWithId(MyApplication.ID_CATEGORY_PITCH_11).get(0).getMoney()));
        });

        if (p.getCategoryId() == MyApplication.ID_CATEGORY_PITCH_5) {
            rdo5.setChecked(true);
        } else if (p.getCategoryId() == MyApplication.ID_CATEGORY_PITCH_7) {
            rdo7.setChecked(true);
        } else {
            rdo11.setChecked(true);
        }
        if (p.getStatus() == MyApplication.BAOTRI_STATUS) {
            rdoBaoTri.setChecked(true);
        } else {
            rdoHoatDong.setChecked(true);
        }

        Button btnSua = dialog.findViewById(R.id.btn_add_sanbong);
        btnSua.setText("Cập nhật");

        btnSua.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            if (name.equals("")) {
                tvCheck.setText("Tên sân không được bỏ trống");
                tvCheck.setVisibility(View.VISIBLE);
            } else if (!name.equals(p.getName()) && MyDatabase.getInstance(getContext()).pitchDao().getPitchWithName(name).size() > 0) {
                    tvCheck.setText("Tên sân đã tồn tại");
                    tvCheck.setVisibility(View.VISIBLE);
            } else {
                tvCheck.setVisibility(View.INVISIBLE);
                p.setName(name);
                if (rdo5.isChecked()) p.setCategoryId(MyApplication.ID_CATEGORY_PITCH_5);
                else if (rdo7.isChecked())
                    p.setCategoryId(MyApplication.ID_CATEGORY_PITCH_7);
                else if (rdo11.isChecked())
                    p.setCategoryId(MyApplication.ID_CATEGORY_PITCH_11);

                if (rdoHoatDong.isChecked()) p.setStatus(MyApplication.HOATDONG_STATUS);
                else if (rdoBaoTri.isChecked()) p.setStatus(MyApplication.BAOTRI_STATUS);

                MyDatabase.getInstance(getContext()).pitchDao().update(p);
                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                pitchsList = MyDatabase.getInstance(getContext()).pitchDao().getAll();
                adapter.setData(pitchsList);

                rdoCategoryAll.setChecked(true);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        pitchsList = MyDatabase.getInstance(getContext()).pitchDao().getAll();
        if(rdoCategoryAll.isChecked()) adapter.setData(pitchsList);
        else if(rdoCategory5.isChecked())adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_5));
        else if(rdoCategory7.isChecked())adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_7));
        else if(rdoCategory11.isChecked())adapter.setData(MyDatabase.getInstance(getContext()).pitchDao()
                .getPitchWithCategoryId(MyApplication.ID_CATEGORY_PITCH_11));
    }
}