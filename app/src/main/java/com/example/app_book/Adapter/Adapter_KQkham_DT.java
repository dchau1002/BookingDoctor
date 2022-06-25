package com.example.app_book.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Detail_KQKham;
import com.example.app_book.Model.Model_KQKham;
import com.example.app_book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_KQkham_DT extends RecyclerView.Adapter<Adapter_KQkham_DT.Myholder> {
    Context context;
    List<Model_KQKham> userList;
    int countcmt = 0;
    private DatabaseReference benhanh, benhanh2;
    private DatabaseReference user;

    public Adapter_KQkham_DT(Context context, List<Model_KQKham> userList) {
        this.context = context;
        this.userList = userList;
        benhanh = FirebaseDatabase.getInstance().getReference("Patient");
        benhanh2 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");

    }

    @NonNull
    @Override
    public Adapter_KQkham_DT.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ketquakham, parent, false);

        return new Adapter_KQkham_DT.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_KQkham_DT.Myholder holder, int i) {
        Model_KQKham Model_LichKhamUS = userList.get(i);
        if (Model_LichKhamUS == null) {
            return;
        }
        //get data
        String idDT = userList.get(i).getIdDT();
        String idUS = userList.get(i).getUid();

        String malickham = userList.get(i).getMaLichKham();


        holder.tvv3.setText("Chuẩn đoán ban đầu: " + userList.get(i).getCDBanDau());
        holder.tvv4.setText("chuẩn đoán cuối cùng: " + userList.get(i).getCDCuoiCung());


        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_KQKham.class);
                intent.putExtra("idoctor", userList.get(i).getIdDT());
                intent.putExtra("iduser", userList.get(i).getUid());
                intent.putExtra("makq", userList.get(i).getMaKQ());
                intent.putExtra("maLichen", userList.get(i).getMaLichKham());
                intent.putExtra("key", "doctor");
                context.startActivity(intent);
            }
        });

        final Query ref = benhanh.orderByChild("uid").equalTo(idUS);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String aa = "" + ds.child("Name").getValue().toString();
                    String ccc = "" + ds.child("STD").getValue().toString();
                    String imagedb = "" + ds.child("avatar").getValue().toString();

                    holder.tvv6.setText("Doctor: " + aa);
                    holder.tvv7.setText("SĐT: " + ccc);

                    try {
                        Picasso.get().load(imagedb).into(holder.vatta);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(holder.vatta);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        final Query ref2 = benhanh2.orderByChild("MaLichKhamofUS").equalTo(malickham);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String aa = "" + ds.child("TenHoSo").getValue().toString();
                    String ccc = "" + ds.child("Ngaykham").getValue().toString();
                    String hhh = "" + ds.child("ThoiGian").getValue().toString();
                    String bbb = "" + ds.child("Buoi").getValue().toString();

                    holder.tvv1.setText("Vào ngày: " + ccc);
                    holder.tvv2.setText("Vào Lúc: " + hhh + " / " + bbb);
                    holder.tvv5.setText("Hồ sơ khám: " + aa);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public int getItemCount() {

        if (userList != null) {
            return userList.size();
        }

        return 0;
    }

    class Myholder extends RecyclerView.ViewHolder {
        Button huy, chitiet;
        ImageView vatta;
        TextView tvv1, tvv2, tvv3, tvv4, tvv5, tvv6, tvv7, dakham;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            tvv1 = itemView.findViewById(R.id.tvv1);
            tvv2 = itemView.findViewById(R.id.tvv2);
            tvv3 = itemView.findViewById(R.id.tvv3);
            tvv4 = itemView.findViewById(R.id.tvv4);
            tvv5 = itemView.findViewById(R.id.tvv5);
            tvv6 = itemView.findViewById(R.id.tvv6);
            tvv7 = itemView.findViewById(R.id.tvv7);
            chitiet = itemView.findViewById(R.id.follow);
            vatta = itemView.findViewById(R.id.avatar_useruc);
        }
    }
}