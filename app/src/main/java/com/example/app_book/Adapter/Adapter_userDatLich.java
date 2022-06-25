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

import com.example.app_book.Detail_LichHen;
import com.example.app_book.Details_Lichkham_Doctor;
import com.example.app_book.Model.Model_US_DKy_LK;
import com.example.app_book.Model.Model_doctor;
import com.example.app_book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_userDatLich extends RecyclerView.Adapter<Adapter_userDatLich.Myholder> {
    Context context;
    List<Model_US_DKy_LK> userList;
    String myUid;
    private DatabaseReference benhanh;
    private DatabaseReference user;


    public Adapter_userDatLich(Context context, List<Model_US_DKy_LK> userList) {
        this.context = context;
        this.userList = userList;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        benhanh = FirebaseDatabase.getInstance().getReference("Patient");

    }


    @NonNull
    @Override
    public Adapter_userDatLich.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_datlich, parent, false);

        return new Adapter_userDatLich.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_userDatLich.Myholder holder, int i) {
        Model_US_DKy_LK model_doctor = userList.get(i);
        if (model_doctor == null) {
            return;
        }
        //get data
        String uid = userList.get(i).getIduser();

        String phone = userList.get(i).getTime();
        holder.phone_user.setText(phone);
        String tinhtrang = userList.get(i).getTrangthai();
        if(tinhtrang.equals("old")){
            holder.follow.setVisibility(View.GONE);
            holder.chuyenkhoa.setVisibility(View.VISIBLE);
            holder.chuyenkhoa.setText("Đã Khám");
        }
        final Query ref = benhanh.orderByChild("uid").equalTo(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String aa = ""+ ds.child("Name").getValue().toString();
                    String imagedb = ""+ ds.child("avatar").getValue().toString();
                    holder.nametv.setText(aa);

                    try {
                        Picasso.get().load(imagedb).into(holder.avatar);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(holder.avatar);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tinhtrang.equals("new")){
                    Intent intent = new Intent(context, Detail_LichHen.class);
                    intent.putExtra("Mahoso", userList.get(i).getMahoso());
                    intent.putExtra("idoctor","null");
                    intent.putExtra("iduser",userList.get(i).getIduser());
                    intent.putExtra("malichkhamDT",userList.get(i).getMaLichKhamofDT());
                    intent.putExtra("maLichen",userList.get(i).getMaLichKhamofUS());
                    intent.putExtra("key","doctor");
                    context.startActivity(intent);
                }

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
        ImageView avatar, block_us;
        Button follow;
        TextView nametv, phone_user, chuyenkhoa, diachi, theloai;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_user);
            nametv = itemView.findViewById(R.id.nametv_user);
            phone_user = itemView.findViewById(R.id.diachi_user);

            chuyenkhoa = itemView.findViewById(R.id.follow2);
            follow = itemView.findViewById(R.id.follow);
        }
    }
}