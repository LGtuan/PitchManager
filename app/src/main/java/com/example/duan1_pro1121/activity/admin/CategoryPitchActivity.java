package com.example.duan1_pro1121.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.PithCategory;

import java.util.List;

public class CategoryPitchActivity extends AppCompatActivity {

    private List<PithCategory> categories;
    private RecyclerView recyclerView;
    private ImageView img_back;
    CategoryPitchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_pitch);
        categories = MyDatabase.getInstance(this).pitchCategoryDAO().getAll();

        recyclerView = findViewById(R.id.recycler_danhsach_loaisan);
        img_back = findViewById(R.id.btn_back_danhsach_loaisan);
        img_back.setOnClickListener(v->{
            finish();
        });

        adapter = new CategoryPitchAdapter(categories,this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

    }

    public void createDialogUpdate(PithCategory category){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_pitch_category);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtMoney = dialog.findViewById(R.id.edt_money_dialog_update_loaisan);
        edtMoney.setText(category.getMoney()+"");
        TextView tv = dialog.findViewById(R.id.tv_check_money_dialog_update_pitch_category);

        Button btn = dialog.findViewById(R.id.btn_update_loaisan);
        btn.setText("Cập nhật");

        btn.setOnClickListener(v->{
            String money = edtMoney.getText().toString();
            if(money.equals("")){
                tv.setText("Hãy nhập đầy đủ dữ liệu");
                tv.setVisibility(View.VISIBLE);
            }else{
                tv.setVisibility(View.INVISIBLE);
                try{
                    category.setMoney(Integer.parseInt(money));
                }catch(NumberFormatException e){
                    tv.setText("Số tiền không hợp lệ");
                    tv.setVisibility(View.VISIBLE);
                }
                MyDatabase.getInstance(this).pitchCategoryDAO().update(category);
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                categories = MyDatabase.getInstance(this).pitchCategoryDAO().getAll();
                adapter.setData(categories);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private class CategoryPitchAdapter extends RecyclerView.Adapter<CategoryPitchAdapter.ViewHolder>{
        private List<PithCategory> list;
        private Context context;

        public CategoryPitchAdapter(List<PithCategory> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public CategoryPitchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recy_loaisan,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryPitchAdapter.ViewHolder holder, int position) {
            holder.tv1.setText(list.get(position).getId()+"");
            holder.tv2.setText(list.get(position).getName());
            holder.tv3.setText(MyApplication.convertMoneyToString(list.get(position).getMoney())+"VNĐ");
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv1,tv2,tv3;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tv_id_item_loaisan);
                tv2 = itemView.findViewById(R.id.tv_name_item_loaisan);
                tv3 = itemView.findViewById(R.id.tv_money_item_loaisan);
                itemView.setOnClickListener(v->{
                    createDialogUpdate(list.get(getAdapterPosition()));
                });
            }
        }

        public void setData(List<PithCategory> list){
            this.list = list;
            notifyDataSetChanged();
        }
    }
}