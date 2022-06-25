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
            tv8.setText("Phê Duyệt");
            tv9.setText("Y/C cập nhật thông tin");
        }else if(key.equals("xem")){
            tv8.setText("khóa tài khoản");
            tv9.setText("xóa tài khoản");
        }

        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("Doctor");
        final Query ref5 = databaseReference5.orderByChild("uid").equalTo(Uid);
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    tv12.setText("Họ và tên: "+""+ ds.child("Name").getValue().toString());
                    tv13.setText("Giới tính: "+""+ ds.child("sex").getValue().toString());
                    BhYT.setText("Chuyên Khoa: "+""+ ds.child("chuyenkhoa").getValue().toString());
                    tv20.setText("Ngày Sinh: "+""+ ds.child("ngaysinh").getValue().toString());
                    tv15.setText("Địa chỉ: "+""+ ds.child("DiaChiLamViec").getValue().toString());
                    tv17.setText("Hình thức hoạt động: "+""+ ds.child("typehoatdong").getValue().toString());
                    tv18.setText("Mô tả quá trình học tập: "+""+ ds.child("mota").getValue().toString());

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
                        tv8.setText("mở khóa tài khoản");
                    }else if(type.equals("doctor") && key.equals("xem")){
                        tv8.setText("khóa tài khoản");
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

                    tv8.setText("Phê Duyệt");
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("kiemduyet", "true");
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Doctor");
                    ref3.child(Uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Detail_Doctor_AD.this, "Phê duyệt thành công...", Toast.LENGTH_SHORT).show();

                            String timestamp = String.valueOf(System.currentTimeMillis());
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("idsender", user.getUid());
                            hashMap.put("idTake", Uid);
                            hashMap.put("Ma_Noti", timestamp);
                            hashMap.put("title", "Thông báo");
                            hashMap.put("ippost", " ");
                            hashMap.put("type", "1");
                            hashMap.put("content", "Tài khoản của bạn đã đã được phê duyệt");
                            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                            ref3.child(timestamp).setValue(hashMap);
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Detail_Doctor_AD.this, "Phê duyệt thất bại...", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else if(key.equals("xem")){

                     if(type.equals("null")){
                         tv8.setText("mở khóa tài khoản");
                         HashMap<String, Object> hashMap = new HashMap<>();
                         hashMap.put("Type", "doctor");
                         DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                         ref3.child(Uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Toast.makeText(Detail_Doctor_AD.this, "Khóa thành công...", Toast.LENGTH_SHORT).show();
                                 String timestamp = String.valueOf(System.currentTimeMillis());
                                 HashMap<String, Object> hashMap = new HashMap<>();
                                 hashMap.put("idsender", user.getUid());
                                 hashMap.put("idTake", Uid);
                                 hashMap.put("Ma_Noti", timestamp);
                                 hashMap.put("title", "Thông báo mở tài khoản");
                                 hashMap.put("ippost", " ");
                                 hashMap.put("type", "1");
                                 hashMap.put("content", "Tài khoản của bạn đã được mở khóa");
                                 DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                                 ref3.child(timestamp).setValue(hashMap);

                                 onBackPressed();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(Detail_Doctor_AD.this, "Khóa thất bại...", Toast.LENGTH_SHORT).show();
                             }
                         });
                        }else{
                         tv8.setText("khóa tài khoản");
                         HashMap<String, Object> hashMap = new HashMap<>();
                         hashMap.put("Type", "null");
                         DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                         ref3.child(Uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Toast.makeText(Detail_Doctor_AD.this, "Khóa thành công...", Toast.LENGTH_SHORT).show();
                                 String timestamp = String.valueOf(System.currentTimeMillis());
                                 HashMap<String, Object> hashMap = new HashMap<>();
                                 hashMap.put("idsender", user.getUid());
                                 hashMap.put("idTake", Uid);
                                 hashMap.put("Ma_Noti", timestamp);
                                 hashMap.put("title", "Thông báo khóa tài khoản");
                                 hashMap.put("ippost", " ");
                                 hashMap.put("type", "1");
                                 hashMap.put("content", "Tài khoản của bạn đã bị khóa");
                                 DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                                 ref3.child(timestamp).setValue(hashMap);

                                 onBackPressed();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(Detail_Doctor_AD.this, "Khóa thất bại...", Toast.LENGTH_SHORT).show();
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
                    tv9.setText("Y/C cập nhật thông tin");
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("idsender", user.getUid());
                    hashMap.put("idTake", Uid);
                    hashMap.put("Ma_Noti", timestamp);
                    hashMap.put("title", "Thông báo Y/C cập nhật thông tin");
                    hashMap.put("ippost", " ");
                    hashMap.put("type", "1");
                    hashMap.put("content", "Thông tin hồ sơ của bạn không được phê duyệt, Y/C cập nhật lại thông tin cá nhân");
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                    ref3.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Detail_Doctor_AD.this, "Y/C thành công...", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Detail_Doctor_AD.this, "Y/C thất bại...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(key.equals("xem")){
                    tv9.setText("xóa tài khoản");
                    DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("Doctor");
                    ref4.child(Uid).removeValue();
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                    ref3.child(Uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Detail_Doctor_AD.this, "Xóa thành công...", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Detail_Doctor_AD.this, "Xóa thất bại...", Toast.LENGTH_SHORT).show();
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