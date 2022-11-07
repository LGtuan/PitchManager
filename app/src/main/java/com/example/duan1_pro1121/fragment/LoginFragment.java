package com.example.duan1_pro1121.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.SplashActivity;

public class LoginFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_user = view.findViewById(R.id.btn_user_login);
        Button btn_admin = view.findViewById(R.id.btn_admin_login);

        btn_user.setOnClickListener(v -> {
            if (getContext() != null) {
                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.putExtra("type_account","user");
                getContext().startActivity(intent);
            }
        });

        btn_admin.setOnClickListener(v -> {
            if (getContext() != null) {
                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.putExtra("type_account","admin");
                getContext().startActivity(intent);
            }
        });

    }
}
