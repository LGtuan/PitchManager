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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.adapter.admin.AcceptAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.HistoryBuy;

import java.util.List;

public class RequestBuyFragment extends Fragment {

    private RecyclerView recyclerView;
    private AcceptAdapter adapter;
    private List<HistoryBuy> list;
    private ImageView img;
    private EditText edt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = MyDatabase.getInstance(getContext()).historyBuyDAO().getAllWithStatus(MyApplication.NAPTIEN_CHOXACNHAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_buy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_request_buy);
        adapter = new AcceptAdapter(getContext(),list);

        img = view.findViewById(R.id.img_search_fragment_request);
        edt = view.findViewById(R.id.edt_searchName_fragment_request);

        img.setOnClickListener(v->{
            if(!edt.getText().toString().equals("")) {
                adapter.setData(MyDatabase.getInstance(getContext()).historyBuyDAO().getAllWithCustommer("%"+edt.getText().toString()+"%",MyApplication.NAPTIEN_CHOXACNHAN));
            }else{
                adapter.setData(list);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }
}