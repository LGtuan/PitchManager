package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Pitch;
import com.example.duan1_pro1121.model.PithCategory;

import java.util.List;

public class RecyclerDatSanAdapter extends RecyclerView.Adapter<RecyclerDatSanAdapter.ViewHolder> {
    private Context context;
    private List<Pitch> list;
    private MyOnClickDatSan onClickDatSan;

    public RecyclerDatSanAdapter(Context context, List<Pitch> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerDatSanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recy_datsan,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDatSanAdapter.ViewHolder holder, int position) {
        holder.tv1.setText(list.get(position).getName());
        if(list.get(position).getStatus() == MyApplication.BAOTRI_STATUS){
            holder.tv2.setText("Bảo trì");
            holder.tv2.setTextColor(context.getResources().getColor(R.color.dark_gray));
            holder.tv1.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }else{
            holder.tv2.setText("Hoạt động");
            holder.tv2.setTextColor(context.getResources().getColor(R.color.green));
            holder.tv1.setBackgroundColor(context.getResources().getColor(R.color.dark_blue));
        }
        PithCategory category = MyDatabase.getInstance(context).
                pitchCategoryDAO().getCategoryPitchWithId(list.get(position).getCategoryId()).get(0);
        if(category!=null) {
            holder.tv3.setText(MyApplication.convertMoneyToString(category.getMoney()) + "VNĐ");
            holder.tv4.setText(category.getName());
        }

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_down_to_up));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3,tv4;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_name_item_datsan);
            tv2 = itemView.findViewById(R.id.tv_status_item_datsan);
            tv3 = itemView.findViewById(R.id.tv_money_item_datsan);
            tv4 = itemView.findViewById(R.id.tv_type_item_datsan);
            cardView = itemView.findViewById(R.id.cardViewDatSan);
            itemView.setOnClickListener(v->{
                Pitch pitch = list.get(getAdapterPosition());
                if(pitch.getStatus() == MyApplication.BAOTRI_STATUS) {
                    Toast.makeText(context, pitch.getName()+" hiện đang bảo trì vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }else{
                    onClickDatSan.myOnClick(list.get(getAdapterPosition()));
                }
            });
        }
    }

    public void setOnClickDatSan(MyOnClickDatSan onClickDatSan) {
        this.onClickDatSan = onClickDatSan;
    }

    public interface MyOnClickDatSan{
        void myOnClick(Pitch pitch);
    }

    public void setData(List<Pitch> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
