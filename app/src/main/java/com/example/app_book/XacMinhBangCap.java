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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class XacMinhBangCap extends AppCompatActivity {
    Uri image_url = null;
    ImageView img;
    ProgressDialog pd;
    FirebaseAuth firebaseAuth;

    String uid,sdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xac_minh_bang_cap);
        img = findViewById(R.id.img1);
        sdt = getIntent().getStringExtra("sdt");
        LinearLayout themha = findViewById(R.id.themha);
        pd = new ProgressDialog(this);
        themha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImagePic();
            }
        });
        ImageButton back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       Button submit = findViewById(R.id.btn_post);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upData();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void upData() {

        if(img.getDrawable() == null) {
            Toast.makeText(XacMinhBangCap.this, "Vui lòng điền thêm các hình ảnh thông tin cần thiết !", Toast.LENGTH_SHORT).show();
        }

        pd.show();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "BangCapDoctor/" + "post_"+timestamp;

        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
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
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("kiemduyet", "false");
                    hashMap.put("imgbangcap", dowloadUri);
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Doctor");
                    ref3.child(uid).updateChildren(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();

                                    String timestamp = String.valueOf(System.currentTimeMillis());
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("idsender", uid);
                                    hashMap.put("idTake", "123");
                                    hashMap.put("Ma_Noti", timestamp);
                                    hashMap.put("title", "Thông báo ");
                                    hashMap.put("ippost", " ");
                                    hashMap.put("type", "3");
                                    hashMap.put("content", "Vừa có 1 yêu cầu phê duyệt hồ sơ tài khoản bác sĩ mới");
                                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                                    ref3.child(timestamp).setValue(hashMap);

                                    Toast.makeText(XacMinhBangCap.this, "xác nhận thành công...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Dashboard_Doctor.class);
                                    intent.putExtra("phar","1" );
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(XacMinhBangCap.this, "123" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(XacMinhBangCap.this, "466"+e.getMessage(), Toast.LENGTH_SHORT).show();
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
            img.setImageURI(image_url);

        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Intent intent = new Intent(this, Comfirm_In4_Doctor_Activity.class);
            startActivity(intent);
        }        super.onActivityResult(requestCode, resultCode, data);
    }

}