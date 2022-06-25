package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.HashMap;

public class Profile_Doctor extends AppCompatActivity {
    ImageView avatar, image;
    String Uid;
    ProgressDialog pd;
    Uri image_url = null;
    TextView tv5, tv6, tv7, tv8, tv9, tv10,tv11,tv12,tv13,tv14, tv15, tv16,tv17,tv18,tv19, tv20, BhYT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__doctor);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();
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
                    tv15.setText("Địa chỉ: "+""+ ds.child("diachi").getValue().toString());
                    tv17.setText("Hình thức hoạt động: "+""+ ds.child("typehoatdong").getValue().toString());
                    tv18.setText("Mô tả quá trình học tập: "+""+ ds.child("mota").getValue().toString());

                    ///
                    tv5.setText(""+ ds.child("Name").getValue().toString());
                    tv6.setText(""+ ds.child("STD").getValue().toString());
                    tv7.setText("Email: "+""+ ds.child("email").getValue().toString());

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
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Doctor.this, Update_profile_Doctor.class);
                startActivity(intent);

            }
        });
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Doctor.this, ShowPostOfDoctor.class);
                startActivity(intent);

            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImagePic();
            }
        });
    }

    private void ShowImagePic() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(5, 4)
                .start(this);
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            image_url = result.getUri();
            image.setImageURI(image_url);
            upData();

        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Intent intent = new Intent(this, Comfirm_In4_Doctor_Activity.class);
            startActivity(intent);
        }        super.onActivityResult(requestCode, resultCode, data);
    }
    private void upData() {

        if (image.getDrawable() == null) {
            Toast.makeText(Profile_Doctor.this, "Vui lòng điền thêm các hình ảnh thông tin cần thiết !", Toast.LENGTH_SHORT).show();
        }

        pd.show();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "BangCapDoctor/" + "post_" + timestamp;

        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);
        ref.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                String dowloadUri = uriTask.getResult().toString();
                if (uriTask.isSuccessful()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("imgbangcap", dowloadUri);
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Doctor");
                    ref3.child(Uid).updateChildren(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(Profile_Doctor.this, "cập nhật thành công...", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(Profile_Doctor.this, "123" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Profile_Doctor.this, "466" + e.getMessage(), Toast.LENGTH_SHORT).show();
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