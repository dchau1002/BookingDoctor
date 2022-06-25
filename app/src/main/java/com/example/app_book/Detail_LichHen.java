package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

public class Detail_LichHen extends AppCompatActivity {
    String uid;
    private DatePickerDialog datePickerDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ProgressDialog pd;
    ImageView im1, im2;
    LinearLayout kals;
    Button ntnsim;
    String idDT, idUS, maHoSo, MaLichHen,malichDT, key, Giohen, buoi,ngaykham;
    TextView h1, h2, h3, h4, h5, h6, h7, h8, h9, h10,h11,h12,h13, bn1, bn2, bn3;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10,tv11,tv12,tv13,tv14, tv15, tv16,tv17,tv18,tv19, tv20, BhYT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__lich_hen);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();


        idDT = getIntent().getStringExtra("idoctor");
        maHoSo = getIntent().getStringExtra("Mahoso");
        malichDT = getIntent().getStringExtra("malichkhamDT");
        MaLichHen = getIntent().getStringExtra("maLichen");
        key = getIntent().getStringExtra("key");
        idUS = getIntent().getStringExtra("iduser");

        bn1 = findViewById(R.id.bn1);kals = findViewById(R.id.thigian);
        bn2= findViewById(R.id.bn2); im1= findViewById(R.id.image11);
        bn3 = findViewById(R.id.bn3); im2 = findViewById(R.id.image22);
        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initthoigianShow();
                kals.setVisibility(View.VISIBLE);
            }
        });
        bn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v);
            }
        });
        bn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.equals("user")){
                    Intent intent = new Intent(Detail_LichHen.this, ChatActivity.class);
                    intent.putExtra("hisUid",idDT);
                    intent.putExtra("uidchat",uid);
                    startActivity(intent);
                }else if(key.equals("doctor")){
                    Intent intent = new Intent(Detail_LichHen.this, ChatActivity.class);
                    intent.putExtra("hisUid",idUS);
                    intent.putExtra("uidchat",uid);
                    startActivity(intent);
                }

            }
        });

        ImageButton backc = findViewById(R.id.backc);
        backc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        checkUserStatus();
        ntnsim = findViewById(R.id.btn_post);
        h1 = findViewById(R.id.h1);h2= findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);h4 = findViewById(R.id.h4);
        h5 = findViewById(R.id.h5);h6 = findViewById(R.id.h6);
        h7 = findViewById(R.id.h7);h8 = findViewById(R.id.h8);
        h9 = findViewById(R.id.h9);h10 = findViewById(R.id.h10);
        h11 = findViewById(R.id.h11);h12 = findViewById(R.id.h12);
        h13 = findViewById(R.id.h13);
        ///////////
        tv20 = findViewById(R.id.tvv20);
        tv19 = findViewById(R.id.dsdsds);BhYT= findViewById(R.id.tvvBHYT);
        tv1 = findViewById(R.id.tvv1);tv10= findViewById(R.id.tvv10);
        tv2 = findViewById(R.id.tvv2);tv11 = findViewById(R.id.tvv11);
        tv3 = findViewById(R.id.tvv3);tv12 = findViewById(R.id.tvv12);
        tv4 = findViewById(R.id.tvv4);tv13 = findViewById(R.id.tvv13);
        tv5 = findViewById(R.id.tvv5);tv14 = findViewById(R.id.tvv14);
        tv6 = findViewById(R.id.tvv6);tv15 = findViewById(R.id.tvv15);
        tv7 = findViewById(R.id.tvv7);tv16 = findViewById(R.id.tvv16);
        tv8 = findViewById(R.id.tvv8);tv17 = findViewById(R.id.tvv17);
        tv9 = findViewById(R.id.tvv9);tv18 = findViewById(R.id.tvv18);
        //////////
        initDatePicker();
        /////////////
        initthoigian();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
        final Query ref = databaseReference.orderByChild("MaLichKhamofUS").equalTo(MaLichHen);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ngaykham = "" + ds.child("Ngaykham").getValue().toString();
                    tv1.setText("Lịch Hẹn: " + "" + ds.child("Ngaykham").getValue().toString());
                    tv2.setText("Vào Lúc: " + "" + ds.child("ThoiGian").getValue().toString());
                    tv3.setText("Tên Hồ Sơ: " + "" + ds.child("TenHoSo").getValue().toString());

                    tv6.setText("Mô Tả: " + "" + ds.child("MoTa").getValue().toString());
                    String imga = "" + ds.child("hinhanh").getValue().toString();
                    try {
                        Picasso.get().load(imga).into(im2);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(im2);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(key.equals("user")){

            tv19.setText("Thông tin bác sĩ");
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

                        tv4.setText("Địa Chỉ: "+""+ ds.child("diachi").getValue().toString());
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
            tv19.setText("Thông tin bệnh nhân");
            ntnsim.setText("Khám lâm sàn");
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

                        tv4.setText("Địa Chỉ: "+"Tại phòng khám");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("HoSo_BenhNhan");
        final Query ref5 = databaseReference5.orderByChild("MaHoSo").equalTo(maHoSo);
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    tv12.setText("Họ và tên: "+""+ ds.child("Name").getValue().toString());
                    tv13.setText("Giới tính: "+""+ ds.child("sex").getValue().toString());
                    BhYT.setText("Mã BHYT: "+""+ ds.child("MaBHYT").getValue().toString());
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


        ntnsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.equals("user")){
                    onBackPressed();
                }else if(key.equals("doctor")){
                    Intent intent = new Intent(Detail_LichHen.this, KhamLamSan_DT.class);
                    intent.putExtra("MaLichHen",MaLichHen);
                    intent.putExtra("idUS",idUS);
                    intent.putExtra("idDT",idDT);
                    intent.putExtra("malichDT",malichDT);
                    intent.putExtra("ngaykham",ngaykham);
                    startActivity(intent);
                }

            }
        });
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                updatengathangnam(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private void updatengathangnam(String date) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Ngaykham", date);
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
        ref3.child(MaLichHen).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Detail_LichHen.this, "Cập nhật thành công...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Detail_LichHen.this, "Cập nhật thất bại..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void updatetime(String time, String buoi) {

        if (malichDT.equals("null")){

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ThoiGian", time);
            hashMap.put("Buoi", buoi);
            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
            ref3.child(MaLichHen).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Detail_LichHen.this, "Cập nhật thành công...", Toast.LENGTH_SHORT).show();
                    initthoigianShow();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Detail_LichHen.this, "Cập nhật thất bại..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("Time", time);
            DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("SoLuongĐKy");
            ref4.child(malichDT).child(uid).updateChildren(hashMap1);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ThoiGian", time);
            hashMap.put("Buoi", buoi);
            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
            ref3.child(MaLichHen).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Detail_LichHen.this, "Cập nhật thành công...", Toast.LENGTH_SHORT).show();
                    initthoigianShow();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Detail_LichHen.this, "Cập nhật thất bại..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }




    }

    private String makeDateString(int day, int month, int year)
    {
        return "Ngày " + day + " - " + getMonthFormat(month) + " - " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Tháng 1";
        if(month == 2)
            return "Tháng 2";
        if(month == 3)
            return "Tháng 3";
        if(month == 4)
            return "Tháng 4";
        if(month == 5)
            return "Tháng 5";
        if(month == 6)
            return "Tháng 6";
        if(month == 7)
            return "Tháng 7";
        if(month == 8)
            return "Tháng 8";
        if(month == 9)
            return "Tháng 9";
        if(month == 10)
            return "Tháng 10";
        if(month == 11)
            return "Tháng 11";
        if(month == 12)
            return "Tháng 12";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    private void initthoigianShow() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SoLuongĐKy").child(malichDT);
        final Query ref = databaseReference.orderByChild("MaLichKhamofDT").equalTo(malichDT);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String time = ""+ ds.child("Time").getValue().toString();
                    dieukienshowtiime(time);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void dieukienshowtiime(String time) {
        if(time.equals(h1.getText().toString())){
            h1.setVisibility(View.GONE);
            h2.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h2.getText().toString())){
            h2.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h3.getText().toString())){
            h3.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h4.getText().toString())){
            h4.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h5.getText().toString())){
            h5.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h6.getText().toString())){
            h6.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h7.getText().toString())){
            h7.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h8.getText().toString())){
            h8.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h9.getText().toString())){
            h9.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h10.getText().toString())){
            h10.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h11.getText().toString())){
            h11.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h12.getText().toString())){
            h12.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
            h13.setVisibility(View.VISIBLE);
        }else if(time.equals(h13.getText().toString())){
            h13.setVisibility(View.GONE);
            h1.setVisibility(View.VISIBLE);
            h2.setVisibility(View.VISIBLE);
            h4.setVisibility(View.VISIBLE);
            h5.setVisibility(View.VISIBLE);
            h6.setVisibility(View.VISIBLE);
            h7.setVisibility(View.VISIBLE);
            h8.setVisibility(View.VISIBLE);
            h9.setVisibility(View.VISIBLE);
            h10.setVisibility(View.VISIBLE);
            h11.setVisibility(View.VISIBLE);
            h12.setVisibility(View.VISIBLE);
            h3.setVisibility(View.VISIBLE);
        }

    }
    private void initthoigian() {
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button1);
                Giohen = h1.getText().toString();
                buoi = "Sáng";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button1);
                Giohen = h2.getText().toString();
                buoi = "Sáng";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button1);
                Giohen = h3.getText().toString();
                buoi = "Sáng";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button1);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h4.getText().toString();
                buoi = "Sáng";
                tv1.setText(h4.getText().toString());
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button1);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h5.getText().toString();
                buoi = "Sáng";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button1);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h6.getText().toString();
                buoi = "Sáng";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button1);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h7.getText().toString();
                buoi = "Chiều";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button1);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h8.getText().toString();
                buoi = "Chiều";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button1);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h9.getText().toString();
                buoi = "Chiều";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button1);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h10.getText().toString();
                buoi = "Chiều";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button1);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h11.getText().toString();
                buoi = "Chiều";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button);
                h12.setBackgroundResource(R.drawable.skip_button1);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h12.getText().toString();
                buoi = "Chiều";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });

        h13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h13.setBackgroundResource(R.drawable.skip_button1);
                h12.setBackgroundResource(R.drawable.skip_button);
                h11.setBackgroundResource(R.drawable.skip_button);
                h10.setBackgroundResource(R.drawable.skip_button);
                h9.setBackgroundResource(R.drawable.skip_button);
                h8.setBackgroundResource(R.drawable.skip_button);
                h7.setBackgroundResource(R.drawable.skip_button);
                h6.setBackgroundResource(R.drawable.skip_button);
                h5.setBackgroundResource(R.drawable.skip_button);
                h4.setBackgroundResource(R.drawable.skip_button);
                h2.setBackgroundResource(R.drawable.skip_button);
                h1.setBackgroundResource(R.drawable.skip_button);
                h3.setBackgroundResource(R.drawable.skip_button);
                Giohen = h13.getText().toString();
                buoi = "Chiều";
                tv1.setText(Giohen);
                kals.setVisibility(View.GONE);
                updatetime(Giohen, buoi);
            }
        });


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