package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import java.util.HashMap;

public class Detail_Doctor_AD extends AppCompatActivity {

    ImageView avatar, image;
    String Uid, key, type;
    ProgressDialog pd;
    FirebaseUser user;
    Uri image_url = null;
    TextView tv5, tv6, tv7, tv8, tv9, tv10,tv11,tv12,tv13,tv14, tv15, tv16,tv17,tv18,tv19, tv20, BhYT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__doctor__a_d);
         user = FirebaseAuth.getInstance().getCurrentUser();

        key = getIntent().getStringExtra("key");
        Uid = getIntent().getStringExtra("uid");

        tv5 = findViewById(R.id.nametvdb);
        tv6 = findViewById(R.id.emailtvdb);tv15 = findViewById(R.id.tvv15);
        tv7 = findViewById(R.id.ooop);tv9 = findViewById(R.id.tvcontent2);
        tv8 = findViewById(R.id.tvcontent1);tv17 = findViewById(R.id.tvv17);
        tv18 = findViewById(R.id.tvv18);
        pd = new ProgressDialog(this);
        avatar = findViewById(R.id.avatar_db);
        image = findViewById(R.id.imag2);
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
        checkUserStatus();
        if(key.equals("kiemduyet")){
            tv8.setText("Ph?? Duy???t");
            tv9.setText("Y/C c???p nh???t th??ng tin");
        }else if(key.equals("xem")){
            tv8.setText("kh??a t??i kho???n");
            tv9.setText("x??a t??i kho???n");
        }

        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("Doctor");
        final Query ref5 = databaseReference5.orderByChild("uid").equalTo(Uid);
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    tv12.setText("H??? v?? t??n: "+""+ ds.child("Name").getValue().toString());
                    tv13.setText("Gi???i t??nh: "+""+ ds.child("sex").getValue().toString());
                    BhYT.setText("Chuy??n Khoa: "+""+ ds.child("chuyenkhoa").getValue().toString());
                    tv20.setText("Ng??y Sinh: "+""+ ds.child("ngaysinh").getValue().toString());
                    tv15.setText("?????a ch???: "+""+ ds.child("DiaChiLamViec").getValue().toString());
                    tv17.setText("H??nh th???c ho???t ?????ng: "+""+ ds.child("typehoatdong").getValue().toString());
                    tv18.setText("M?? t??? qu?? tr??nh h???c t???p: "+""+ ds.child("mota").getValue().toString());

                    ///
                    tv5.setText(""+ ds.child("Name").getValue().toString());
                    tv6.setText(""+ ds.child("STD").getValue().toString());
                    tv7.setText("Email: "+ ""+ ds.child("email").getValue().toString());

                    String imga = "" + ds.child("avatar").getValue().toString();
                    String imga2 = "" + ds.child("imgbangcap").getValue().toString();
                    try {
                        Picasso.get().load(imga).into(avatar);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(avatar);
                    }
                    try {
                        Picasso.get().load(imga2).into(image);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(image);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference("Users");
        final Query ref = databaseReference6.orderByChild("uid").equalTo(Uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    type = ""+ ds.child("Type").getValue().toString();

                    if(type.equals("null") && key.equals("xem")){
                        tv8.setText("m??? kh??a t??i kho???n");
                    }else if(type.equals("doctor") && key.equals("xem")){
                        tv8.setText("kh??a t??i kho???n");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        TextView nt = findViewById(R.id.nhantin);
        nt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Doctor_AD.this, ChatActivity.class);
                intent.putExtra("hisUid",Uid);
                intent.putExtra("uidchat",user.getUid());
                startActivity(intent);
            }
        });


        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(key.equals("kiemduyet")){

                    tv8.setText("Ph?? Duy???t");
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("kiemduyet", "true");
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Doctor");
                    ref3.child(Uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Detail_Doctor_AD.this, "Ph?? duy???t th??nh c??ng...", Toast.LENGTH_SHORT).show();

                            String timestamp = String.valueOf(System.currentTimeMillis());
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("idsender", user.getUid());
                            hashMap.put("idTake", Uid);
                            hashMap.put("Ma_Noti", timestamp);
                            hashMap.put("title", "Th??ng b??o");
                            hashMap.put("ippost", " ");
                            hashMap.put("type", "1");
                            hashMap.put("content", "T??i kho???n c???a b???n ???? ???? ???????c ph?? duy???t");
                            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                            ref3.child(timestamp).setValue(hashMap);
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Detail_Doctor_AD.this, "Ph?? duy???t th???t b???i...", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else if(key.equals("xem")){

                     if(type.equals("null")){
                         tv8.setText("m??? kh??a t??i kho???n");
                         HashMap<String, Object> hashMap = new HashMap<>();
                         hashMap.put("Type", "doctor");
                         DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                         ref3.child(Uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Toast.makeText(Detail_Doctor_AD.this, "Kh??a th??nh c??ng...", Toast.LENGTH_SHORT).show();
                                 String timestamp = String.valueOf(System.currentTimeMillis());
                                 HashMap<String, Object> hashMap = new HashMap<>();
                                 hashMap.put("idsender", user.getUid());
                                 hashMap.put("idTake", Uid);
                                 hashMap.put("Ma_Noti", timestamp);
                                 hashMap.put("title", "Th??ng b??o m??? t??i kho???n");
                                 hashMap.put("ippost", " ");
                                 hashMap.put("type", "1");
                                 hashMap.put("content", "T??i kho???n c???a b???n ???? ???????c m??? kh??a");
                                 DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                                 ref3.child(timestamp).setValue(hashMap);

                                 onBackPressed();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(Detail_Doctor_AD.this, "Kh??a th???t b???i...", Toast.LENGTH_SHORT).show();
                             }
                         });
                        }else{
                         tv8.setText("kh??a t??i kho???n");
                         HashMap<String, Object> hashMap = new HashMap<>();
                         hashMap.put("Type", "null");
                         DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                         ref3.child(Uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Toast.makeText(Detail_Doctor_AD.this, "Kh??a th??nh c??ng...", Toast.LENGTH_SHORT).show();
                                 String timestamp = String.valueOf(System.currentTimeMillis());
                                 HashMap<String, Object> hashMap = new HashMap<>();
                                 hashMap.put("idsender", user.getUid());
                                 hashMap.put("idTake", Uid);
                                 hashMap.put("Ma_Noti", timestamp);
                                 hashMap.put("title", "Th??ng b??o kh??a t??i kho???n");
                                 hashMap.put("ippost", " ");
                                 hashMap.put("type", "1");
                                 hashMap.put("content", "T??i kho???n c???a b???n ???? b??? kh??a");
                                 DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                                 ref3.child(timestamp).setValue(hashMap);

                                 onBackPressed();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(Detail_Doctor_AD.this, "Kh??a th???t b???i...", Toast.LENGTH_SHORT).show();
                             }
                         });
                     }

                }

            }
        });
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.equals("kiemduyet")){
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    tv9.setText("Y/C c???p nh???t th??ng tin");
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("idsender", user.getUid());
                    hashMap.put("idTake", Uid);
                    hashMap.put("Ma_Noti", timestamp);
                    hashMap.put("title", "Th??ng b??o Y/C c???p nh???t th??ng tin");
                    hashMap.put("ippost", " ");
                    hashMap.put("type", "1");
                    hashMap.put("content", "Th??ng tin h??? s?? c???a b???n kh??ng ???????c ph?? duy???t, Y/C c???p nh???t l???i th??ng tin c?? nh??n");
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                    ref3.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Detail_Doctor_AD.this, "Y/C th??nh c??ng...", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Detail_Doctor_AD.this, "Y/C th???t b???i...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(key.equals("xem")){
                    tv9.setText("x??a t??i kho???n");
                    DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("Doctor");
                    ref4.child(Uid).removeValue();
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                    ref3.child(Uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Detail_Doctor_AD.this, "X??a th??nh c??ng...", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Detail_Doctor_AD.this, "X??a th???t b???i...", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
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