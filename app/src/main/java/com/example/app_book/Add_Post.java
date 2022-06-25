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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class Add_Post extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner Spinnerchuyenkhoa;
    Uri image_url = null;
    ImageView img1;
    Button submit;
    EditText  title, mota;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private StorageReference storageReference1;
    String uid, idpost, jkey;
    ProgressDialog pd;
    String[] chuyenkhoa = { "Nam khoa", "Nội tiết", "Nhi Khoa", "Hô hấp", "Tai – mũi – họng", "Răng - hàm – mặt", "Da liễu", "Tim mạch", "Nhãn khoa", "Thần Kinh"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__post);
        pd = new ProgressDialog(this);
        checkUserStatus();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        title = findViewById(R.id.tv1);
        mota = findViewById(R.id.tvcontent2);
        ImageButton back = findViewById(R.id.backc);

        idpost = getIntent().getStringExtra("maposst");
        jkey = getIntent().getStringExtra("key");

        if(jkey.equals("update")){
           TextView fdgf = findViewById(R.id.tevaa);
            fdgf.setText("Cập nhật bài viêt");
             showdatapdate();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ////////////set hình thức
        Spinnerchuyenkhoa = (Spinner) findViewById(R.id.spinnerdata);
        Spinnerchuyenkhoa.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, chuyenkhoa);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinnerchuyenkhoa.setAdapter(aaa);
        LinearLayout themha = findViewById(R.id.themha);
        themha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImagePic();
            }
        });
        img1 = findViewById(R.id.img1);
        submit = findViewById(R.id.btn_post);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jkey.equals("update")){
                    if(image_url != null) {
                        updateDataImgae();
                    }else if (image_url == null){
                        updateData();
                    }
                }else if(jkey.equals("add")){
                        upData();

                }

            }

        });
    }

    private void showdatapdate() {
        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("Post");
        final Query ref5 = databaseReference5.orderByChild("time").equalTo(idpost);
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    title.setText(""+ ds.child("Title").getValue().toString());
                    mota.setText(""+ ds.child("Noidung").getValue().toString());

                    String imga = "" + ds.child("image").getValue().toString();
                    try {
                        Picasso.get().load(imga).into(img1);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(img1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateDataImgae() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "ImagePosst/" + "post_"+timestamp;

        Bitmap bitmap = ((BitmapDrawable) img1.getDrawable()).getBitmap();
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
                    HashMap<String, Object> hashMapss = new HashMap<>();
                    hashMapss.put("Title", title.getText().toString().trim());
                    hashMapss.put("Noidung", mota.getText().toString().trim());
                    hashMapss.put("chuyenkhoa", Spinnerchuyenkhoa.getSelectedItem().toString().trim());
                    hashMapss.put("image", dowloadUri);
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Post");
                    ref3.child(idpost).updateChildren(hashMapss)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(Add_Post.this, "Update thành công...", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                    title.setText("");
                                    mota.setText("");
                                    img1.setImageURI(null);
                                    image_url = null;
                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Add_Post.this, "Thất bại" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData() {
        HashMap<String, Object> hashMapss = new HashMap<>();
        hashMapss.put("Title", title.getText().toString().trim());
        hashMapss.put("Noidung", mota.getText().toString().trim());
        hashMapss.put("chuyenkhoa", Spinnerchuyenkhoa.getSelectedItem().toString().trim());
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Post");
        ref3.child(idpost).updateChildren(hashMapss)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(Add_Post.this, "Update thành công...", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        title.setText("");
                        mota.setText("");
                        img1.setImageURI(null);
                        image_url = null;
                    }
                });
    }

    private void upData() {
        String tt = title.getText().toString().trim();
        String ct = mota.getText().toString().trim();
        if(TextUtils.isEmpty(tt) || TextUtils.isEmpty(ct)) {
            Toast.makeText(Add_Post.this, "Vui lòng điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
        }
        else if(image_url != null) {
            uploaddata();
        } else {
            Toast.makeText(Add_Post.this, "Vui lòng điền thêm các hình ảnh thông tin cần thiết !", Toast.LENGTH_SHORT).show();
        }


    }
    private void uploaddata(){
        pd.show();
        String tv11 = title.getText().toString().trim();
        String motaa = mota.getText().toString().trim();
        String chuyenkhoa = Spinnerchuyenkhoa.getSelectedItem().toString().trim();
        String timestamp = String.valueOf(System.currentTimeMillis());
            String filePathAndName = "ImagePosst/" + "post_"+timestamp;


            Bitmap bitmap = ((BitmapDrawable) img1.getDrawable()).getBitmap();
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
                        HashMap<String, Object> hashMapss = new HashMap<>();
                        hashMapss.put("iddoctor", uid);
                        hashMapss.put("Title", title.getText().toString().trim());
                        hashMapss.put("Noidung", mota.getText().toString().trim());
                        hashMapss.put("chuyenkhoa", Spinnerchuyenkhoa.getSelectedItem().toString().trim());
                        hashMapss.put("time", timestamp);
                        hashMapss.put("image", dowloadUri);
                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Post");
                        ref3.child(timestamp).setValue(hashMapss)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pd.dismiss();
                                        Toast.makeText(Add_Post.this, "Đăng tin thành công...", Toast.LENGTH_SHORT).show();
                                       onBackPressed();
                                        title.setText("");
                                        mota.setText("");
                                        img1.setImageURI(null);
                                        image_url = null;
                                    }
                                });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(Add_Post.this, "Thất bại" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            img1.setImageURI(image_url);

        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Intent intent = new Intent(this, Comfirm_In4_Doctor_Activity.class);
            startActivity(intent);
        }        super.onActivityResult(requestCode, resultCode, data);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}