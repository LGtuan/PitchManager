package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.fragment.adminfragment.NhanVienFragment;
import com.example.duan1_pro1121.model.ManagerCategory;

import java.util.List;

public class SpinnerLoaiNVAdapter extends ArrayAdapter<ManagerCategory> {
    private Context context;
    private List<ManagerCategory> list;

    public SpinnerLoaiNVAdapter(@NonNull Context context, int resource, @NonNull List<ManagerCategory> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.line_spinner_loai_nv,parent,false);
            viewHolder.tv = convertView.findViewById(R.id.tv_name_loainv_line_spinner);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(list.get(position).getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.line_spinner_loai_nv_dropdown,parent,false);
            viewHolder.tv = convertView.findViewById(R.id.tv_name_loainv_line_spinner_dropdown);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(list.get(position).getName());

        return convertView;
    }

    public class ViewHolder{
        TextView tv;
    }
}