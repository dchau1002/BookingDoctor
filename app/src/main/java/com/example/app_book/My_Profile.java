package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class My_Profile extends AppCompatActivity {
    ImageView avatar;
    String Uid;
    TextView tv5, tv6, tv7, tv8, tv9, tv10,tv11,tv12,tv13,tv14, tv15, tv16,tv17,tv18,tv19, tv20, BhYT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__profile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();
        tv5 = findViewById(R.id.nametvdb);
        tv6 = findViewById(R.id.emailtvdb);tv15 = findViewById(R.id.tvv15);
        tv7 = findViewById(R.id.ooop);tv16 = findViewById(R.id.tvv16);
        tv8 = findViewById(R.id.tvcontent1);tv17 = findViewById(R.id.tvv17);
        tv9 = findViewById(R.id.tvcontent2);tv18 = findViewById(R.id.tvv18);

        avatar = findViewById(R.id.avatar_db);
        tv12 = findViewById(R.id.tvv12);
        tv13 = findViewById(R.id.tvv13);
        BhYT = findViewById(R.id.tvv20);
        tv20 = findViewById(R.id.tvv14);

        ImageView back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("Patient");
        final Query ref5 = databaseReference5.orderByChild("uid").equalTo(Uid);
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    tv12.setText("Họ và tên: "+""+ ds.child("Name").getValue().toString());
                    tv13.setText("Giới tính: "+""+ ds.child("sex").getValue().toString());
                    BhYT.setText("Mã BHYT: "+""+ ds.child("MaBHYT").getValue().toString());
                    tv20.setText("Ngày Sinh: "+""+ ds.child("ngaysinh").getValue().toString());
                    tv15.setText("Địa chỉ: "+""+ ds.child("diachi").getValue().toString());
                    tv16.setText("Chiều cao: "+""+ ds.child("chieucao").getValue().toString()+ " ( Cm ) ");
                    tv17.setText("Cân nặng: "+""+ ds.child("diachi").getValue().toString()+ " ( Kg )");
                    tv18.setText("Nhóm máu: "+""+ ds.child("nhommau").getValue().toString());

                    ///
                    tv5.setText(""+ ds.child("Name").getValue().toString());
                    tv6.setText(""+ ds.child("STD").getValue().toString());
                    tv7.setText("Email: "+""+ ds.child("email").getValue().toString());

                    String imga = "" + ds.child("avatar").getValue().toString();
                    try {
                        Picasso.get().load(imga).into(avatar);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(avatar);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_Profile.this, Add_Hoso.class);
                intent.putExtra("Key","update" );
                intent.putExtra("key1","aaaaa" );
                startActivity(intent);

            }
        });
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_Profile.this, Choose_HoSoBenhNhan.class);
                startActivity(intent);
            }
        });

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