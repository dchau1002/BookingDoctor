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

import com.example.app_book.Adapter.Adapter_HoSoBN;
import com.example.app_book.Adapter.Adapter_HoSoBN1;
import com.example.app_book.Adapter.Apdapter_LichKham_DTUS;
import com.example.app_book.Model.Model_HosoBN;
import com.example.app_book.Model.Model_LichKhamDT;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Choose_HoSoBenhNhan extends AppCompatActivity {
    RecyclerView RCVHSo;
    Adapter_HoSoBN1 Adapter_HoSoBN;
    List<Model_HosoBN> Model_HosoBN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_ho_so_benh_nhan);
        checkUserStatus();
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
        Adapter_HoSoBN = new Adapter_HoSoBN1(this, Model_HosoBN);
        TextView ABCd = findViewById(R.id.tvcontent1);
        ABCd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose_HoSoBenhNhan.this, Add_Hoso.class);
                intent.putExtra("Key","add" );
                intent.putExtra("idhoSo","null");
                startActivity(intent);
            }
        });

        showhoso();
    }

    private void showhoso() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("HoSo_BenhNhan");
        Query rs = ref.orderByChild("uid").equalTo(user.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_HosoBN.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_HosoBN model_post = ds.getValue(Model_HosoBN.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}