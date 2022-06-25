package com.example.app_book.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Add_Hoso;
import com.example.app_book.Choose_HoSoBenhNhan;
import com.example.app_book.Dat_LichKhamchitiet;
import com.example.app_book.Details_Lichkham_Doctor;
import com.example.app_book.Model.Model_HosoBN;
import com.example.app_book.Model.Model_LichKhamDT;
import com.example.app_book.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Adapter_HoSoBN1 extends RecyclerView.Adapter<Adapter_HoSoBN1.Myholder> {
    public static String tenhoso ="null", Mahoso;
    Context context;
    View sheetView;
    List<Model_HosoBN> userList;
    public Adapter_HoSoBN1(Context context, List<Model_HosoBN> userList) {
        this.context = context;
        this.userList = userList;

    }
    @NonNull
    @Override
    public Adapter_HoSoBN1.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hosobennhan, parent,false);

        return new Adapter_HoSoBN1.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_HoSoBN1.Myholder holder, int i) {
        Model_HosoBN model_HosoBN = userList.get(i);
        if (model_HosoBN == null){
            return;
        }
        //get data
        String uid = userList.get(i).getUid();
        String ghichu = userList.get(i).getTenHoSo();
        String mahoso = userList.get(i).getMaHoSo();
        holder.nametv.setText(ghichu);


        holder.kkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.kkk.setBackgroundResource(R.drawable.skip_button1);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomsheetTheme);
                sheetView = LayoutInflater.from(context).inflate(R.layout.more_dialog_in4_hoso, holder.more2);
                bottomSheetDialog.setContentView(sheetView);
                TextView tv12 = sheetView.findViewById(R.id.tvv12);
                TextView tv13 = sheetView.findViewById(R.id.tvv13);
                TextView tv14 = sheetView.findViewById(R.id.tvv14);
                TextView tv20 = sheetView.findViewById(R.id.tvv20);
                TextView tv15 = sheetView.findViewById(R.id.tvv15);
                TextView tv16 = sheetView.findViewById(R.id.tvv16);
                TextView tv17 = sheetView.findViewById(R.id.tvv17);
                TextView tv18 = sheetView.findViewById(R.id.tvv18);
                bottomSheetDialog.show();
                DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("HoSo_BenhNhan");
                final Query ref5 = databaseReference5.orderByChild("MaHoSo").equalTo(mahoso);
                ref5.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            tv12.setText("Họ và tên: "+""+ ds.child("Name").getValue().toString());
                            tv13.setText("Giới tính: "+""+ ds.child("sex").getValue().toString());
                            tv14.setText("Tên hồ sơ: "+""+ ds.child("TenHoSo").getValue().toString());
                            tv20.setText("Ngày Sinh: "+""+ ds.child("ngaysinh").getValue().toString());
                            tv15.setText("Địa chỉ: "+""+ ds.child("diachi").getValue().toString());
                            tv16.setText("Chiều cao: "+""+ ds.child("chieucao").getValue().toString()+ " ( Cm ) ");
                            tv17.setText("Cân nặng: "+""+ ds.child("diachi").getValue().toString()+ " ( Kg )");
                            tv18.setText("Nhóm máu: "+""+ ds.child("nhommau").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                TextView hehe = sheetView.findViewById(R.id.tvcontent1);
                hehe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Add_Hoso.class);
                        intent.putExtra("Key","update" );
                        intent.putExtra("key1","null");
                        intent.putExtra("idhoSo",mahoso);
                        context.startActivity(intent);
                        bottomSheetDialog.dismiss();
                    }
                });
                TextView kaka = sheetView.findViewById(R.id.tvcontent2);
                kaka.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(mahoso);
                        bottomSheetDialog.dismiss();
                    }
                });
                if(ghichu.equals("Hồ sơ của bạn")){
                    kaka.setVisibility(View.GONE);
                }
            }
        });
    }
    private void delete(String mahoso) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Đang Xoá...");
        pd.show();
        Query fquery1 = FirebaseDatabase.getInstance().getReference("HoSo_BenhNhan").orderByChild("MaHoSo").equalTo(mahoso);
        fquery1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ds.getRef().removeValue();
                }

                Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {

        if (userList != null){
            return userList.size();
        }

        return 0;
    }

    class Myholder extends RecyclerView.ViewHolder {
        RelativeLayout kkk;
        TextView tv12,tv13,tv14, tv15, tv16,tv17,tv18,tv19, tv20;
        TextView nametv;
        LinearLayout more2;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            kkk = itemView.findViewById(R.id.bkkasa);
            nametv = itemView.findViewById(R.id.nametv_useruc);

            more2 = itemView.findViewById(R.id.bottom_sheet2);
        }

    }
}