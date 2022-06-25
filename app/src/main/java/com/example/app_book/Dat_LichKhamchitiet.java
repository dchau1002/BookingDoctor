package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_book.Adapter.Adapter_HoSoBN;
import com.example.app_book.Adapter.Apdapter_LichKham_DTUS;
import com.example.app_book.Model.Model_HosoBN;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dat_LichKhamchitiet extends AppCompatActivity {
    TextView tv1, tv2, tv3;
    Uri image_url = null;
    String Giohen,uid;
    ImageView img1;
    ProgressDialog pd;
    RecyclerView RCVHSo;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Adapter_HoSoBN Adapter_HoSoBN;
    List<Model_HosoBN> Model_HosoBN;
    TextView h1, h2, h3, h4, h5, h6, h7, h8, h9, h10,h11,h12,h13;
    String tenhsss, ngaydat, hisUid, mahoso,buoi,malichkham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat__lich_khamchitiet);
        ImageView back = findViewById(R.id.backc);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
         h1 = findViewById(R.id.h1);h2= findViewById(R.id.h2);
         h3 = findViewById(R.id.h3);h4 = findViewById(R.id.h4);
         h5 = findViewById(R.id.h5);h6 = findViewById(R.id.h6);
         h7 = findViewById(R.id.h7);h8 = findViewById(R.id.h8);
         h9 = findViewById(R.id.h9);h10 = findViewById(R.id.h10);
         h11 = findViewById(R.id.h11);h12 = findViewById(R.id.h12);
         h13 = findViewById(R.id.h13);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        pd = new ProgressDialog(this);
        img1 = findViewById(R.id.img1);
        tv1 = findViewById(R.id.tvcontentdate);
        tv2 = findViewById(R.id.hosso);


        hisUid =  Apdapter_LichKham_DTUS.hisID;
        ngaydat =  Apdapter_LichKham_DTUS.NgayThang;
        malichkham = Apdapter_LichKham_DTUS.Malichkham;


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout haka = findViewById(R.id.thigian);
                haka.setVisibility(View.VISIBLE);
                initthoigianShow();
            }
        });
        initthoigian();
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Dat_LichKhamchitiet.this, Choose_HoSoBenhNhan.class);
//                startActivity(intent);
                LinearLayout bottom_sheet1 = findViewById(R.id.bottom_sheet1);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Dat_LichKhamchitiet.this, R.style.BottomsheetTheme);
                View sheetView = LayoutInflater.from(Dat_LichKhamchitiet.this).inflate(R.layout.more_hoso_bottomsheet, bottom_sheet1);
                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
                RCVHSo = sheetView.findViewById(R.id.rcvhoso);
                RCVHSo.setHasFixedSize(true);
                RCVHSo.setLayoutManager(new LinearLayoutManager(Dat_LichKhamchitiet.this));
                Model_HosoBN = new ArrayList<>();
                Adapter_HoSoBN = new Adapter_HoSoBN(Dat_LichKhamchitiet.this, Model_HosoBN);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("HoSo_BenhNhan");
                Query rs = ref.orderByChild("uid").equalTo(user.getUid());
                rs.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Model_HosoBN.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Model_HosoBN model_post = ds.getValue(Model_HosoBN.class);
                            if(model_post.getUid().equals(uid)){
                                Model_HosoBN.add(model_post);
                            }

                            RCVHSo.setAdapter(Adapter_HoSoBN);
                            Adapter_HoSoBN.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                TextView hehe = sheetView.findViewById(R.id.tvcontent1);
                hehe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent intent = new Intent(Dat_LichKhamchitiet.this, Choose_HoSoBenhNhan.class);
                      startActivity(intent);
                    }
                });
                TextView kaka = sheetView.findViewById(R.id.tvcontent2);
                kaka.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        mahoso = Adapter_HoSoBN.Mahoso;
                        tenhsss = Adapter_HoSoBN.tenhoso;
                        if (tenhsss.equals("null")){

                        }else{
                            tv2.setText(tenhsss);
                        }
                    }
                });
            }
        });

        tv3 = findViewById(R.id.login_email);
        LinearLayout haka = findViewById(R.id.themha);
        haka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImagePic();
            }
        });
        checkUserStatus();
        Button xacnhasna = findViewById(R.id.btn_post);
        xacnhasna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abx = tv1.getText().toString();
                String acsd = tv2.getText().toString();
                String vxvc = tv3.getText().toString();
                if(TextUtils.isEmpty(acsd)){
                    Toast.makeText(Dat_LichKhamchitiet.this, "Vui Lòng Chọn hồ sơ", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(abx)){
                    Toast.makeText(Dat_LichKhamchitiet.this, "Vui Lòng Chọn thời gian", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(vxvc)){
                    Toast.makeText(Dat_LichKhamchitiet.this, "Vui Lòng mô tả triệu chứng", Toast.LENGTH_SHORT).show();
                }else if(image_url != null){
                    updata();
                }
                else if(image_url == null){
                    updata2();
                }


            }
        });


    }
    private void updata2() {
        pd.show();
        String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, Object> hashMapss = new HashMap<>();
        hashMapss.put("uid", uid);
        hashMapss.put("iddoctor", hisUid);
        hashMapss.put("Ngaykham", ngaydat);
        hashMapss.put("Buoi", buoi);
        hashMapss.put("ThoiGian", tv1.getText().toString());
        hashMapss.put("TenHoSo", tenhsss);
        hashMapss.put("MaHoSo", mahoso);
        hashMapss.put("MoTa", tv3.getText().toString());
        hashMapss.put("MaLichKhamofDT", malichkham);
        hashMapss.put("MaLichKhamofUS", timestamp);
        hashMapss.put("trangthai", "new");
        hashMapss.put("hinhanh", "null");
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
        ref3.child(timestamp).setValue(hashMapss)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        onBackPressed();

                        img1.setImageURI(null);
                        img1.setVisibility(View.GONE);
                        image_url = null;
                        HashMap<String, Object> hashMaps22 = new HashMap<>();
                        hashMaps22.put("mahoso", mahoso);
                        hashMaps22.put("MaLichKhamofUS", timestamp);
                        hashMaps22.put("MaLichKhamofDT", malichkham);
                        hashMaps22.put("Time", tv1.getText().toString());
                        hashMaps22.put("iduser", uid);
                        hashMaps22.put("trangthai", "new");
                        DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("SoLuongĐKy");
                        ref4.child(malichkham).child(uid).setValue(hashMaps22);

                        String timestamp22 = String.valueOf(System.currentTimeMillis());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("idsender", uid);
                        hashMap.put("idTake", hisUid);
                        hashMap.put("Ma_Noti", timestamp22);
                        hashMap.put("title", "Thông báo lịch hẹn mới");
                        hashMap.put("ippost", " ");
                        hashMap.put("MaHoSo", mahoso);
                        hashMap.put("MaLichKhamofDT", malichkham);
                        hashMap.put("MaLichKhamofUS", timestamp);
                        hashMap.put("type", "2");
                        hashMap.put("content", "Bạn vừa có một lịch hẹn mới");
                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                        ref3.child(timestamp22).setValue(hashMap);

                        Toast.makeText(Dat_LichKhamchitiet.this, "Đặt Lịch Thành Công", Toast.LENGTH_SHORT).show();

                        tv1.setText("");
                        tv2.setText("");
                        tv3.setText("");
                        pd.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Dat_LichKhamchitiet.this, "Đặt Lịch Thất Bại", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                finish();
            }
        });
    }

    private void updata() {
        pd.show();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "ImgachitietLichDat/" + "post_"+timestamp;
        Bitmap bitmap = ((BitmapDrawable)img1.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);
        ref.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                String dowloadUri = uriTask.getResult().toString();
                if (uriTask.isSuccessful()) {
                    HashMap<String, Object> hashMapss = new HashMap<>();
                    hashMapss.put("uid", uid);
                    hashMapss.put("iddoctor", hisUid);
                    hashMapss.put("Ngaykham", ngaydat);
                    hashMapss.put("Buoi", buoi);
                    hashMapss.put("ThoiGian", tv1.getText().toString());
                    hashMapss.put("TenHoSo", tenhsss);
                    hashMapss.put("MaHoSo", mahoso);
                    hashMapss.put("MoTa", tv3.getText().toString());
                    hashMapss.put("MaLichKhamofDT", malichkham);
                    hashMapss.put("MaLichKhamofUS", timestamp);
                    hashMapss.put("trangthai", "new");
                    hashMapss.put("hinhanh", dowloadUri);
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
                    ref3.child(uid).setValue(hashMapss)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    onBackPressed();

                                    img1.setImageURI(null);
                                    img1.setVisibility(View.GONE);
                                    image_url = null;

                                    HashMap<String, Object> hashMaps22 = new HashMap<>();
                                    hashMaps22.put("mahoso", mahoso);
                                    hashMaps22.put("MaLichKhamofDT", malichkham);
                                    hashMaps22.put("MaLichKhamofUS", timestamp);
                                    hashMaps22.put("Time", tv1.getText().toString());
                                    hashMaps22.put("iduser", uid);
                                    hashMaps22.put("trangthai", "new");
                                    DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("SoLuongĐKy");
                                    ref4.child(malichkham).child(uid).setValue(hashMaps22);

                                    String timestamp22 = String.valueOf(System.currentTimeMillis());
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("idsender", uid);
                                    hashMap.put("idTake", hisUid);
                                    hashMap.put("Ma_Noti", timestamp22);
                                    hashMap.put("title", "Thông báo lịch hẹn mới");
                                    hashMap.put("ippost", " ");
                                    hashMap.put("MaHoSo", mahoso);
                                    hashMap.put("MaLichKhamofDT", malichkham);
                                    hashMap.put("MaLichKhamofUS", timestamp);
                                    hashMap.put("type", "2");
                                    hashMap.put("content", "Bạn vừa có một lịch hẹn mới");
                                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                                    ref3.child(timestamp22).setValue(hashMap);
                                    
                                    Toast.makeText(Dat_LichKhamchitiet.this, "Đặt Lịch Thành Công", Toast.LENGTH_SHORT).show();
                                    tv1.setText("");
                                    tv2.setText("");
                                    tv3.setText("");
                                    pd.dismiss();
                                    finish();


                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Dat_LichKhamchitiet.this, "Đặt Lịch Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void ShowImagePic() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(5, 4)
                .start(this);
    }
    private void checkUserStatus(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){


        }else{
            startActivity(new Intent(this, PharmacyActivity.class));
            finish();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            image_url = result.getUri();
            img1.setImageURI(image_url);

        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Intent intent = new Intent(this, Comfirm_In4_Doctor_Activity.class);
            image_url = null;
            startActivity(intent);
        }        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initthoigianShow() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SoLuongĐKy").child(malichkham);
        final Query ref = databaseReference.orderByChild("MaLichKhamofDT").equalTo(malichkham);
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
        }else if(time.equals(h2.getText().toString())){
            h2.setVisibility(View.GONE);
        }else if(time.equals(h3.getText().toString())){
            h3.setVisibility(View.GONE);
        }else if(time.equals(h4.getText().toString())){
            h4.setVisibility(View.GONE);
        }else if(time.equals(h5.getText().toString())){
            h5.setVisibility(View.GONE);
        }else if(time.equals(h6.getText().toString())){
            h6.setVisibility(View.GONE);
        }else if(time.equals(h7.getText().toString())){
            h7.setVisibility(View.GONE);
        }else if(time.equals(h8.getText().toString())){
            h8.setVisibility(View.GONE);
        }else if(time.equals(h9.getText().toString())){
            h9.setVisibility(View.GONE);
        }else if(time.equals(h10.getText().toString())){
            h10.setVisibility(View.GONE);
        }else if(time.equals(h11.getText().toString())){
            h11.setVisibility(View.GONE);
        }else if(time.equals(h12.getText().toString())){
            h12.setVisibility(View.GONE);

        }else if(time.equals(h13.getText().toString())){
            h13.setVisibility(View.GONE);
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
                buoi = "Sang";
                tv1.setText(Giohen);
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
                buoi = "Sang";
                tv1.setText(Giohen);
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
                buoi = "Sang";
                tv1.setText(Giohen);
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
                buoi = "Sang";
                tv1.setText(h4.getText().toString());
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
                buoi = "Sang";
                tv1.setText(Giohen);
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
                buoi = "Sang";
                tv1.setText(Giohen);
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
                buoi = "Chieu";
                tv1.setText(Giohen);
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
                buoi = "Chieu";
                tv1.setText(Giohen);
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
                buoi = "Chieu";
                tv1.setText(Giohen);
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
                buoi = "Chieu";
                tv1.setText(Giohen);
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
                buoi = "Chieu";
                tv1.setText(Giohen);
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
                buoi = "Chieu";
                tv1.setText(Giohen);
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
                buoi = "Chieu";
                tv1.setText(Giohen);
            }
        });


    }
}