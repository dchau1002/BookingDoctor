package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Detail_Post extends AppCompatActivity {
    String idpost, iddoctor;
    FirebaseUser user;
    TextView name, sdt, Chuyenkhoa,tillr, content, btnxoa, btncapnhat;
    ImageView avatar, imageView;
    FirebaseAuth firebaseAuth;
    LinearLayout fhfgh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__post);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        idpost = getIntent().getStringExtra("maposst");
        iddoctor = getIntent().getStringExtra("iddoctor");

        name = findViewById(R.id.nametvdb);
        sdt = findViewById(R.id.emailtvdb);
        Chuyenkhoa = findViewById(R.id.ooop);
        content = findViewById(R.id.ct);
        tillr = findViewById(R.id.tilte);
        btnxoa = findViewById(R.id.tvcontent2);
        btncapnhat = findViewById(R.id.tvcontent1);
        avatar = findViewById(R.id.avatar_db);
        imageView = findViewById(R.id.coverdb);
      fhfgh = findViewById(R.id.lfffdsf);
      if(user.getUid().equals(iddoctor)){
          fhfgh.setVisibility(View.VISIBLE);
      }else {
          fhfgh.setVisibility(View.GONE);
      }

        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(Detail_Post.this);
                pd.setMessage("Đang Xoá...");
                pd.show();
                Query fquery = FirebaseDatabase.getInstance().getReference("Post").orderByChild("time").equalTo(idpost);
                fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(Detail_Post.this, "Xoá thành công.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Detail_Post.this, "Xoá thất bại.", Toast.LENGTH_SHORT).show();
                        ProgressDialog pd = new ProgressDialog(Detail_Post.this);
                        pd.setMessage("Đang Xoá...");
                        pd.dismiss();
                    }
                });
            }
        });
        btncapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Post.this, Add_Post.class);
                intent.putExtra("maposst",idpost);
                intent.putExtra("key","update");
                startActivity(intent);
            }
        });
        ImageView back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("Post");
        final Query ref5 = databaseReference5.orderByChild("time").equalTo(idpost);
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Chuyenkhoa.setText(""+ ds.child("chuyenkhoa").getValue().toString());
                    content.setText(""+ ds.child("Noidung").getValue().toString());
                    tillr.setText(""+ ds.child("Title").getValue().toString());

                    String imga = "" + ds.child("image").getValue().toString();
                    try {
                        Picasso.get().load(imga).into(imageView);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference("Doctor");
        final Query ref6 = databaseReference6.orderByChild("uid").equalTo(iddoctor);
        ref6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    name.setText(""+ ds.child("Name").getValue().toString());
                    sdt.setText(""+ ds.child("STD").getValue().toString());

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