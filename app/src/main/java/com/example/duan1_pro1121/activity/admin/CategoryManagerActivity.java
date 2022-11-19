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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.ManagerCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryManagerActivity extends AppCompatActivity {

    List<ManagerCategory> list;
    private RecyclerView recyclerView;
    private ImageView img;
    private FloatingActionButton btn;
    private ManagerCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_manager);

        list = MyDatabase.getInstance(this).managerCategoryDAO().getAll();

        recyclerView = findViewById(R.id.recycler_danhsach_loainv);
        img = findViewById(R.id.btn_back_danhsach_loai_nhanvien);
        btn = findViewById(R.id.btn_create_dialog_add_loainv);

        adapter = new ManagerCategoryAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        img.setOnClickListener(v->{
            finish();
        });
        btn.setOnClickListener(v->{
            createDialogAddLoaiNV();
        });
    }

    private void createDialogAddLoaiNV() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_loai_nv);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edt = dialog.findViewById(R.id.edt_name_dialog_add_loainv);
        Button btn = dialog.findViewById(R.id.btn_add_loainv);
        TextView tv = dialog.findViewById(R.id.tv_check_name_dialog_add_loainv);

        btn.setOnClickListener(v->{
            if(edt.getText().length()==0){
                tv.setVisibility(View.INVISIBLE);
            }else{
                tv.setVisibility(View.VISIBLE);
                ManagerCategory category = new ManagerCategory();
                category.setName(edt.getText().toString());
                MyDatabase.getInstance(this).managerCategoryDAO().insert(category);
                list = MyDatabase.getInstance(this).managerCategoryDAO().getAll();
                adapter.setData(list);
                Toast.makeText(this, "Thêm loại nhân viên thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void createDialogUpdate(ManagerCategory category){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_loai_nv);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_title = dialog.findViewById(R.id.tv_title_dialog_add_loainv);
        tv_title.setText("Cập nhật loại nhân viên");
        EditText edt = dialog.findViewById(R.id.edt_name_dialog_add_loainv);
        edt.setText(category.getName());
        Button btn = dialog.findViewById(R.id.btn_add_loainv);
        btn.setText("Cập nhật");
        TextView tv = dialog.findViewById(R.id.tv_check_name_dialog_add_loainv);

        btn.setOnClickListener(v->{
            if(edt.getText().length()==0){
                tv.setVisibility(View.INVISIBLE);
            }else{
                tv.setVisibility(View.VISIBLE);
                category.setName(edt.getText().toString());
                MyDatabase.getInstance(this).managerCategoryDAO().update(category);
                list = MyDatabase.getInstance(this).managerCategoryDAO().getAll();
                adapter.setData(list);
                Toast.makeText(this, "Cập nhật loại nhân viên thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public class ManagerCategoryAdapter extends RecyclerView.Adapter<ManagerCategoryAdapter.ViewHolder>{

        private List<ManagerCategory> cList;
        private Context context;

        public ManagerCategoryAdapter(List<ManagerCategory> cList, Context context) {
            this.cList = cList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loainv,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tv1.setText((position+1)+"");
            holder.tv2.setText(cList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return cList.size();
        }

        public void setData(List<ManagerCategory> list){
            cList = list;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv1,tv2;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tv_number_loai_nv);
                tv2 = itemView.findViewById(R.id.tv_name_loai_nv);

                itemView.setOnClickListener(v->{
                    if(cList.get(getAdapterPosition()).getName().equals(MyApplication.ADMIN_CATEGORY)){
                        Toast.makeText(CategoryManagerActivity.this, "Không thể chỉnh sửa Admin", Toast.LENGTH_SHORT).show();
                    }else{
                        createDialogUpdate(cList.get(getAdapterPosition()));
                    }
                });

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(this);
    }
}