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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.admin.CustomerAdapter;
import com.example.duan1_pro1121.adapter.admin.PitchAdapter;
import com.example.duan1_pro1121.adapter.admin.RecyclerDatSanAdapter;
import com.example.duan1_pro1121.model.Customer;
import com.example.duan1_pro1121.model.Pitch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SanBongFragment extends Fragment {

    public static List<Pitch> pitchsList = new ArrayList<>();
    private FloatingActionButton button;
    PitchAdapter adapter;
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
        recyclerView = view.findViewById(R.id.recycler_pitch);
        button = view.findViewById(R.id.btn_create_dialog_add_pitch);
        button.setOnClickListener(v->{
            createDialogAdd();
        });

        adapter = new PitchAdapter(getContext(),pitchsList);
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this::createDialogUpdate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

    public void setUp(){

    }

    public void createDialogAdd(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_pitch);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    public void createDialogUpdate(Pitch p) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_pitch);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    public void setData(){
        pitchsList.add(new Pitch(1,"Sân A1",1,0));
        pitchsList.add(new Pitch(1,"Sân A2",2,1));
        pitchsList.add(new Pitch(1,"Sân A3",1,0));
        pitchsList.add(new Pitch(1,"Sân B1",3,1));
        pitchsList.add(new Pitch(1,"Sân A4",2,0));
        pitchsList.add(new Pitch(1,"Sân B2",1,0));
        pitchsList.add(new Pitch(1,"Sân B3",3,0));
        pitchsList.add(new Pitch(1,"Sân C1",2,1));
    }
}