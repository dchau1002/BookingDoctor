package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app_book.Adapter.Adapter_HoSoBN1;
import com.example.app_book.Adapter.Adpter_post;
import com.example.app_book.Model.Mode_Post;
import com.example.app_book.Model.Model_HosoBN;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowPostOfDoctor extends AppCompatActivity {
    RecyclerView LichkhamUS;
    Adpter_post Adapter_LichkhamOfUS;
    List<Mode_Post> Model_LichKhamUS;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post_of_doctor);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        ImageButton backkks = findViewById(R.id.backc);
        backkks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView ABCd = findViewById(R.id.tvcontent1);
        ABCd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPostOfDoctor.this, Add_Post.class);
                intent.putExtra("key","add" );
                startActivity(intent);
            }
        });
        LichkhamUS = findViewById(R.id.rcvhosass);
        LichkhamUS.setHasFixedSize(true);
        LichkhamUS.setLayoutManager(new LinearLayoutManager(this));


        Model_LichKhamUS = new ArrayList<>();
        Adapter_LichkhamOfUS = new Adpter_post(this, Model_LichKhamUS);
        showposst();

    }
    private void showposst() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Mode_Post model_post = ds.getValue(Mode_Post.class);
                    String adss =""+ ds.child("iddoctor").getValue().toString();
                    if(user.getUid().equals(adss)){
                        Model_LichKhamUS.add(model_post);
                    }


                    LichkhamUS.setAdapter(Adapter_LichkhamOfUS);
                    Adapter_LichkhamOfUS.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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