package com.example.app_book.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.ChatActivity;
import com.example.app_book.Choose_HoSoBenhNhan;
import com.example.app_book.Dat_LichKhamchitiet;
import com.example.app_book.Details_Lichkham_Doctor;
import com.example.app_book.Model.Model_LichKhamDT;
import com.example.app_book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Apdapter_LichKham_DTUS extends RecyclerView.Adapter<Apdapter_LichKham_DTUS.Myholder> {
    Context context;
    public static String hisID, NgayThang,Malichkham;
    List<Model_LichKhamDT> userList;
    int countcmt = 0;
    private DatabaseReference benhanh;
    private DatabaseReference Likeref;
    String myUid;
    private DatabaseReference user;
    public Apdapter_LichKham_DTUS(Context context, List<Model_LichKhamDT> userList) {
        this.context = context;
        this.userList = userList;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        benhanh = FirebaseDatabase.getInstance().getReference("SoLuongĐKy");
        Likeref = FirebaseDatabase.getInstance().getReference("SoLuongĐKy");

    }
    @NonNull
    @Override
    public Apdapter_LichKham_DTUS.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lickham_dtus, parent,false);

        return new Apdapter_LichKham_DTUS.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Apdapter_LichKham_DTUS.Myholder holder, int i) {
        Model_LichKhamDT Model_LichKhamDT = userList.get(i);
        if (Model_LichKhamDT == null){
            return;
        }
        //get data
        String uid = userList.get(i).getUid();
        String ngaythang = userList.get(i).getNgaythang();
        String phone = userList.get(i).getTrangthai();
        String malichkham = userList.get(i).getMaLichKham();
        holder.ngaykham.setText(ngaythang);
        benhanh.child(malichkham).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    countcmt = (int) snapshot.getChildrenCount();
                    holder.soluong.setText("Số lượng bệnh nhân đăng ký: " + Integer.toString(countcmt));
                }else{
                    holder.soluong.setText("Số lượng bệnh nhân đăng ký: " + "0");

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Dat_LichKhamchitiet.class);
//                intent.putExtra("hisUid", uid);
//                intent.putExtra("Ngaythang",ngaythang);
//                intent.putExtra("malichkham",malichkham);
                intent.putExtra("TenHSo","null");
                hisID = uid;
                NgayThang = ngaythang;
                Malichkham = malichkham;
                context.startActivity(intent);
            }
        });
        Likeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(malichkham).hasChild(myUid) ){
                    holder.ghichu.setVisibility(View.VISIBLE);
                    holder.chitiet.setVisibility(View.GONE);

                }else{
                    holder.ghichu.setVisibility(View.GONE);
                    holder.chitiet.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        Button huy, chitiet;
        TextView ngaykham, ghichu, soluong;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            huy = itemView.findViewById(R.id.huylichkham);
            chitiet = itemView.findViewById(R.id.follow);
            ngaykham = itemView.findViewById(R.id.ngaykham);
            soluong = itemView.findViewById(R.id.songuoidatlich);
            ghichu = itemView.findViewById(R.id.follow2);
        }
    }

}
