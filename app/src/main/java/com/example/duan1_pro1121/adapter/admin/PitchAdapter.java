package com.example.duan1_pro1121.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Pitch;
import com.example.duan1_pro1121.model.PithCategory;

import java.util.List;

public class PitchAdapter extends RecyclerView.Adapter<PitchAdapter.ViewHolder> {
    private Context context;
    private List<Pitch> list;

    private MyOnClick onClick;

    public PitchAdapter(Context context, List<Pitch> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PitchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recy_pitch,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PitchAdapter.ViewHolder holder, int position) {
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
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_name_item_pitch);
            tv2 = itemView.findViewById(R.id.tv_status_item_pitch);
            tv3 = itemView.findViewById(R.id.tv_money_item_pitch);
            tv4 = itemView.findViewById(R.id.tv_type_item_pitch);
            itemView.setOnClickListener(v->{
                onClick.myOnClick(list.get(getAdapterPosition()));
            });
            cardView = itemView.findViewById(R.id.cardview_pitch);
        }
    }


    public void setOnClick(MyOnClick onClick) {
        this.onClick = onClick;
    }

    public interface MyOnClick{
        void myOnClick(Pitch pitch);
    }
    public void setData(List<Pitch> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
