package com.example.duan1_pro1121.adapter.admin;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.fragment.adminfragment.NhanVienFragment;
import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.ManagerCategory;

import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ViewHolder>{

    private List<Manager> list;
    private Context context;

    public ManagerAdapter(List<Manager> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_manager,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerAdapter.ViewHolder holder, int position) {

        ManagerCategory category = MyDatabase.getInstance(context).managerCategoryDAO().getCategoryWithID(list.get(position).getCategory_id()).get(0);
        if(category != null) {
            holder.tv3.setText(category.getName());
        }else{
            holder.tv3.setText("Không có chức vụ");
        }

        holder.tv1.setText(list.get(position).getName());
        holder.tv2.setText(list.get(position).getPhone());
        holder.tv4.setText(list.get(position).getBankName());
        holder.tv5.setText(list.get(position).getBankNumber());
        holder.tv6.setText(MyApplication.convertMoneyToString(list.get(position).getSalary())+"VNĐ");
        if(list.get(position).getStatus() == MyApplication.DANG_LAM){
            holder.tv1.setBackgroundColor(context.getResources().getColor(R.color.dark_blue));
            holder.tv7.setText("Đang làm việc");
            holder.tv7.setTextColor(context.getResources().getColor(R.color.green));
        }else{
            holder.tv1.setBackgroundColor(context.getResources().getColor(R.color.gray));
            holder.tv7.setText("Đã nghỉ việc");
            holder.tv7.setTextColor(context.getResources().getColor(R.color.dark_gray));
        }

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_down_to_up));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_name_item_manager);
            tv2 = itemView.findViewById(R.id.tv_phone_item_manager);
            tv3 = itemView.findViewById(R.id.tv_position_item_manager);
            tv4 = itemView.findViewById(R.id.tv_bankname_item_manager);
            tv5 = itemView.findViewById(R.id.tv_banknumber_item_manager);
            tv6 = itemView.findViewById(R.id.tv_salary_item_manager);
            tv7 = itemView.findViewById(R.id.tv_status_item_manager);
            cardView = itemView.findViewById(R.id.cardView_manager);

            itemView.setOnClickListener(v->{
                int idCategory = list.get(getAdapterPosition()).getCategory_id();
                String nameCategory = MyDatabase.getInstance(context).managerCategoryDAO().getCategoryWithID(idCategory).get(0).getName();
                if(!nameCategory.equals(MyApplication.ADMIN_CATEGORY)){
                    createDialogUpdate(list.get(getAdapterPosition()));
                }else{
                    Toast.makeText(context, "Không thể chỉnh sửa admin", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void createDialogUpdate(Manager manager){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_nv);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_title = dialog.findViewById(R.id.tv_title_dialog_add_nv);
        tv_title.setText("Sửa đổi nhân viên");

        EditText edtSalary = dialog.findViewById(R.id.edt_salary_dialog_add_nhanvien);
        edtSalary.setText(manager.getSalary()+"");
        EditText edtPhone = dialog.findViewById(R.id.edt_phone_dialog_add_nhanvien);
        edtPhone.setText(manager.getPhone());
        EditText edtName = dialog.findViewById(R.id.edt_name_dialog_add_nhanvien);
        edtName.setText(manager.getName());
        EditText edtBankNumber = dialog.findViewById(R.id.edt_banknumber_dialog_add_nhanvien);
        edtBankNumber.setText(manager.getBankNumber());
        EditText edtBankName = dialog.findViewById(R.id.edt_bankname_dialog_add_nhanvien);
        edtBankName.setText(manager.getBankName());
        EditText edtPass = dialog.findViewById(R.id.edt_pass_dialog_add_nhanvien);
        edtPass.setText(manager.getPassword());
        EditText edtPass2 = dialog.findViewById(R.id.edt_pass2_dialog_add_nhanvien);
        edtPass2.setText(manager.getPassword());

        RadioButton rdLamViec = dialog.findViewById(R.id.rdo_lamviec_add_nv);
        RadioButton rdNghiViec = dialog.findViewById(R.id.rdo_nghi_add_nv);
        if(manager.getStatus() == MyApplication.DANG_LAM)rdLamViec.setChecked(true);
        else rdNghiViec.setChecked(true);

        Spinner spinner = dialog.findViewById(R.id.spinner_chucvu_dialog_add_nhanvien);
        List<ManagerCategory> listAllStaff = MyDatabase.getInstance(context).managerCategoryDAO().getAllStaff();
        SpinnerLoaiNVAdapter spAdapter = new SpinnerLoaiNVAdapter(context,R.layout.line_spinner_loai_nv,listAllStaff);
        spinner.setAdapter(spAdapter);
        for(int i = 0;i<listAllStaff.size();i++){
            if(listAllStaff.get(i).getId() == manager.getCategory_id()){
                spinner.setSelection(i);
                break;
            }
        }

        TextView tvCheckName = dialog.findViewById(R.id.tv_check_name_dialog_add_nhanvien);
        TextView tvCheckPass1 = dialog.findViewById(R.id.tv_check_pass_dialog_add_nhanvien);
        TextView tvCheckPass2 = dialog.findViewById(R.id.tv_check_pass2_dialog_add_nhanvien);
        TextView tvCheckPhone = dialog.findViewById(R.id.tv_check_phone_dialog_add_nhanvien);

        Button btnSua = dialog.findViewById(R.id.btn_add_nhanvien);
        btnSua.setText("Thay đổi");

        btnSua.setOnClickListener(v->{
            String phone = edtPhone.getText().toString();
            String name = edtName.getText().toString();
            String pass = edtPass.getText().toString();
            String pass2 = edtPass2.getText().toString();
            String bankNumber = edtBankNumber.getText().toString();
            String bankName = edtBankName.getText().toString();
            int salary;
            if(!phone.matches(MyApplication.PHONE_REGEX)){
                tvCheckPhone.setText("* Số điện thoại không hợp lệ");
                invisible(tvCheckName,tvCheckPass1,tvCheckPhone,tvCheckPass2);
                tvCheckPhone.setVisibility(View.VISIBLE);
            }else if(!name.matches(MyApplication.NAME_REGEX)){
                invisible(tvCheckName,tvCheckPass1,tvCheckPhone,tvCheckPass2);
                tvCheckName.setVisibility(View.VISIBLE);
            }else if(!pass.matches(MyApplication.PASS_REGEX)){
                invisible(tvCheckName,tvCheckPass1,tvCheckPhone,tvCheckPass2);
                tvCheckPass1.setVisibility(View.VISIBLE);
            }else if(!pass.equals(pass2)){
                invisible(tvCheckName,tvCheckPass1,tvCheckPhone,tvCheckPass2);
                tvCheckPass2.setVisibility(View.VISIBLE);
            }else{
                try{
                    salary = Integer.parseInt(edtSalary.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(context, "Lương phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(MyDatabase.getInstance(context).managerDAO().getManagerWithPhone(phone,manager.getId()).size()==0) {
                    if(rdLamViec.isChecked()) manager.setStatus(MyApplication.DANG_LAM);
                    else manager.setStatus(MyApplication.NGHI_VIEC);
                    manager.setPhone(phone);
                    manager.setName(name);
                    manager.setPassword(pass);
                    manager.setBankName(bankName);
                    manager.setBankNumber(bankNumber);
                    manager.setSalary(salary);
                    manager.setPosition(listAllStaff.get(spinner.getSelectedItemPosition()).getId());

                    MyDatabase.getInstance(context).managerDAO().update(manager);
                    Toast.makeText(context, "Update nhân viên thành công", Toast.LENGTH_SHORT).show();

                    list = MyDatabase.getInstance(context).managerDAO().getAll();
                    notifyDataSetChanged();

                    dialog.dismiss();
                }else{
                    tvCheckPhone.setText("* Số điện thoại đã tồn tại");
                    invisible(tvCheckName,tvCheckPhone,tvCheckPass1,tvCheckPass2);
                    tvCheckPhone.setVisibility(View.VISIBLE);
                }

            }


        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void invisible(TextView...tvs){
        for(TextView tv : tvs){
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public void setData(List<Manager> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
