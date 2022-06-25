package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_book.Adapter.Adapter_HoSoBN1;
import com.example.app_book.Adapter.Adapter_userDatLich;
import com.example.app_book.Model.Model_HosoBN;
import com.example.app_book.Model.Model_US_DKy_LK;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User_Dky_LichKhamDT extends AppCompatActivity {
    RecyclerView RCVHSo;
    Adapter_userDatLich Adapter_HoSoBN;
    List<Model_US_DKy_LK> Model_HosoBN;
    String idlkDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__dky__lich_kham_d_t);

        idlkDT = getIntent().getStringExtra("malichkham");


        //// rcv lichkham
        RCVHSo = findViewById(R.id.rcvhosass);
        RCVHSo.setHasFixedSize(true);
        RCVHSo.setLayoutManager(new LinearLayoutManager(this));
        ImageView back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Model_HosoBN = new ArrayList<>();
        Adapter_HoSoBN = new Adapter_userDatLich(this, Model_HosoBN);
        TextView ABCd = findViewById(R.id.tvcontent1);
        ABCd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Trangthai", "old");
                DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Lichkham_Doctor");
                ref3.child(idlkDT).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(User_Dky_LichKhamDT.this, "Hoàn thành ngày khám...", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(User_Dky_LichKhamDT.this, "thất bại..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        checkUserStatus();
        showhoso();
    }

    private void showhoso() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SoLuongĐKy").child(idlkDT);
//        Query rs = ref.orderByChild("uid").equalTo(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_HosoBN.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_US_DKy_LK model_post = ds.getValue(Model_US_DKy_LK.class);
                    Model_HosoBN.add(model_post);
                    RCVHSo.setAdapter(Adapter_HoSoBN);
                    Adapter_HoSoBN.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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