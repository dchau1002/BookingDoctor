package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Detail_KQKham extends AppCompatActivity {
    ImageView im1;
    String idDT, idUS, MaLichHen,makq, key, Giohen, buoi;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10,tv11,tv12,tv13,tv14, tv15, tv16,tv17, bn11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__k_q_kham);


        idDT = getIntent().getStringExtra("idoctor");
        idUS = getIntent().getStringExtra("iduser");
        makq = getIntent().getStringExtra("makq");
        MaLichHen = getIntent().getStringExtra("maLichen");
        key = getIntent().getStringExtra("key");
        ////////////
        im1= findViewById(R.id.image11);
        tv1 = findViewById(R.id.tvv1);tv10= findViewById(R.id.tvv10);
        tv2 = findViewById(R.id.tvv2);tv11 = findViewById(R.id.tvv11);
        tv3 = findViewById(R.id.tvv3);
        tv4 = findViewById(R.id.tvv4);tv13 = findViewById(R.id.tvv13);
        tv5 = findViewById(R.id.tvv5);tv14 = findViewById(R.id.tvv14);
        tv6 = findViewById(R.id.tvv6);tv15 = findViewById(R.id.tvv15);
        tv7 = findViewById(R.id.tvv7);tv16 = findViewById(R.id.tvv16);
        tv8 = findViewById(R.id.tvv8);tv17 = findViewById(R.id.tvv17);
        tv9 = findViewById(R.id.tvv9);bn11 = findViewById(R.id.bn3);


        bn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.equals("user")){
                    Intent intent = new Intent(Detail_KQKham.this, ChatActivity.class);
                    intent.putExtra("hisUid",idDT);
                    intent.putExtra("uidchat",idUS);
                    startActivity(intent);
                }else if(key.equals("doctor")){
                    Intent intent = new Intent(Detail_KQKham.this, ChatActivity.class);
                    intent.putExtra("hisUid",idUS);
                    intent.putExtra("uidchat",idDT);
                    startActivity(intent);
                }

            }
        });
        ///////////////
       Button ntnsim = findViewById(R.id.btn_post);
        ntnsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onBackPressed();

            }
        });
        ImageButton backc = findViewById(R.id.backc);
        backc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    /////////////////////
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("KetQuaKham");
        final Query ref1 = databaseReference1.orderByChild("maKQ").equalTo(makq);
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    tv13.setText("Chuẩn đáng ban đầu: "+""+ ds.child("CDBanDau").getValue().toString());
                    tv14.setText("Nguồn gốc: "+""+ ds.child("NgGoc").getValue().toString());
                    tv15.setText("nguyên nhân: "+""+ ds.child("NgNhan").getValue().toString());
                    tv16.setText("Chuẩn đoán cuối cùng: "+""+ ds.child("CDCuoiCung").getValue().toString());
                    tv17.setText("Đơn thuốc: "+""+ ds.child("DonThuoc").getValue().toString());
                    tv4.setText("Lời khuyên: "+""+ ds.child("LoiKHuyen").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
        final Query ref0 = databaseReference0.orderByChild("MaLichKhamofUS").equalTo(MaLichHen);
        ref0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    tv1.setText("Vào ngày:" + ""+ ds.child("Ngaykham").getValue().toString());
                    tv2.setText("Vào lúc: "+""+ ds.child("ThoiGian").getValue().toString());
                    tv3.setText("Tên hồ sơ: "+""+ ds.child("TenHoSo").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(key.equals("user")){
            tv5.setText("Thông tin bác sĩ");
            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Doctor");
            final Query ref2 = databaseReference2.orderByChild("uid").equalTo(idDT);
            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds: snapshot.getChildren()){
                        tv6.setText(""+ ds.child("Name").getValue().toString());
                        tv7.setText(""+ ds.child("STD").getValue().toString());

                        tv8.setText("Chuyên Khoa: "+""+ ds.child("chuyenkhoa").getValue().toString());
                        tv9.setText("Giới Tính: "+""+ ds.child("sex").getValue().toString());
                        tv10.setText("Ngày Sinh: "+""+ ds.child("ngaysinh").getValue().toString());
                        tv11.setText("Địa Chỉ: "+""+ ds.child("diachi").getValue().toString());

                        String imga = "" + ds.child("avatar").getValue().toString();
                        try {
                            Picasso.get().load(imga).into(im1);
                        } catch (Exception e) {
                            Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(im1);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else if(key.equals("doctor")){
            tv5.setText("Thông tin bệnh nhân");
            DatabaseReference databaseReference4= FirebaseDatabase.getInstance().getReference("Patient");
            final Query ref4 = databaseReference4.orderByChild("uid").equalTo(idUS);
            ref4.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds: snapshot.getChildren()){
                        tv6.setText(""+ ds.child("Name").getValue().toString());
                        tv7.setText(""+ ds.child("STD").getValue().toString());
                        tv8.setVisibility(View.GONE);
                        tv9.setText("Giới Tính: "+""+ ds.child("sex").getValue().toString());
                        tv10.setText("Ngày Sinh: "+""+ ds.child("ngaysinh").getValue().toString());
                        tv11.setText("Địa Chỉ: "+""+ ds.child("diachi").getValue().toString());
                        String imga = "" + ds.child("avatar").getValue().toString();
                        try {
                            Picasso.get().load(imga).into(im1);
                        } catch (Exception e) {
                            Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(im1);
                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        checkUserStatus();
    }
    private void checkUserStatus(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){


        }else{
            startActivity(new Intent(this, PharmacyActivity.class));
            finish();
        }
    }

}