package com.example.app_book;


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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Calendar;
import java.util.HashMap;

public class Comfirm_In4_Patient_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePicker datePicker;
    TextView buttonDate;
    EditText tv1, mabhyt, tvdiachi, tvchieucao, tvcannang;
    Uri image_url = null;
    ImageView img1;
    FirebaseAuth firebaseAuth;
    String uid, sdt, email;
    ProgressDialog pd;
    Button submit;
    RelativeLayout jjjj;
    Spinner spinnerEmployee;
    Spinner spinnerEmployee2;
    String[] country = { "Nam", "Nữ"};
    String[] country2 = { "Chưa rõ", "Nhóm máu A", "Nhóm máu B", "Nhóm máu O", "Nhóm máu AB"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_in4_patient);

        ImageButton back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        pd = new ProgressDialog(this);

        jjjj = findViewById(R.id.datechoo);
        buttonDate = findViewById(R.id.tvcontentdate);
        datePicker = (DatePicker) this.findViewById(R.id.datePicker);

        sdt = getIntent().getStringExtra("sdt");
        email = getIntent().getStringExtra("email");
        ////////////// set date
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Button cok = findViewById(R.id.btn_data);
        cok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jjjj.setVisibility(View.GONE);
            }
        });
        this.datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                datePickerChange(datePicker, year, month, dayOfMonth);
            }
        });

        this.buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });
        ////////////set giới tính
        spinnerEmployee = (Spinner) findViewById(R.id.spinner_employee);
        spinnerEmployee.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee.setAdapter(aa);
        ////////////set hình thức
        spinnerEmployee2 = (Spinner) findViewById(R.id.spinnerdata);
        spinnerEmployee2.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country2);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee2.setAdapter(aaa);


        buttonDate = findViewById(R.id.tvcontentdate);
        tv1 = findViewById(R.id.tv1);
        mabhyt = findViewById(R.id.tvcontent1);
        tvdiachi = findViewById(R.id.tvcontent2);
        tvchieucao = findViewById(R.id.tvchieucao);
        tvcannang = findViewById(R.id.tvcannang);
        submit = findViewById(R.id.btn_post);
        img1 = findViewById(R.id.img1);

        LinearLayout themha = findViewById(R.id.themha);
        themha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImagePic();
            }
        });
        ;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upData();
            }

        });
    }
    private void upData() {
        String tv11 = tv1.getText().toString().trim();
        String mabhYT = mabhyt.getText().toString().trim();
        String tvdiachii = tvdiachi.getText().toString().trim();
        String ngaysinh = buttonDate.getText().toString().trim();
        String chieucao = tvchieucao.getText().toString().trim();
        String canang = tvcannang.getText().toString().trim();
        String sex = spinnerEmployee.getSelectedItem().toString().trim();
        String nhommau = spinnerEmployee2.getSelectedItem().toString().trim();
        if(TextUtils.isEmpty(tv11) ||
                TextUtils.isEmpty(tvdiachii) || TextUtils.isEmpty(ngaysinh) ||
                TextUtils.isEmpty(chieucao) ||
                TextUtils.isEmpty(canang)) {
            Toast.makeText(Comfirm_In4_Patient_Activity.this, "Vui lòng điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();

        }
        else if(image_url == null) {
            Toast.makeText(Comfirm_In4_Patient_Activity.this, "Vui lòng điền thêm các hình ảnh thông tin cần thiết !", Toast.LENGTH_SHORT).show();
        }else {
            pd.show();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String filePathAndName = "AvtarUser/" + "post_"+timestamp;

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
                        hashMapss.put("Name", tv11);
                        hashMapss.put("OnlineStatus", "Online");
                        hashMapss.put("TyingTo", "noOne");
                        hashMapss.put("Type", "user");
                        hashMapss.put("STD", sdt);
                        hashMapss.put("avatar", dowloadUri);
                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                        ref3.child(uid).setValue(hashMapss)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        String mksas= String.valueOf(System.currentTimeMillis());
                                        String ngaysinhd = buttonDate.getText().toString();
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("uid", uid);
                                        hashMap.put("Name", tv11);
                                        hashMap.put("MaBHYT", mabhYT);
                                        hashMap.put("email", email);
                                        hashMap.put("diachi", tvdiachii);
                                        hashMap.put("sex", sex);
                                        hashMap.put("MaHoSo", mksas);
                                        hashMap.put("OnlineStatus", "Online");
                                        hashMap.put("TyingTo", "noOne");
                                        hashMap.put("ngaysinh", ngaysinhd);
                                        hashMap.put("nhommau", nhommau);
                                        hashMap.put("chieucao", chieucao);
                                        hashMap.put("cannang", canang);
                                        hashMap.put("STD", sdt);
                                        hashMap.put("Type", "user");
                                        hashMap.put("avatar", dowloadUri);
                                        finish();
                                        DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("Patient");
                                        ref4.child(uid).setValue(hashMap);

                                        String ngaysinhff = buttonDate.getText().toString();
                                        HashMap<String, Object> hashMapik = new HashMap<>();
                                        hashMapik.put("TenHoSo", "Hồ sơ của bạn");
                                        hashMapik.put("uid", uid);
                                        hashMapik.put("MaHoSo", mksas);
                                        hashMapik.put("MaBHYT", mabhYT);
                                        hashMapik.put("Name", tv11);
                                        hashMapik.put("diachi", tvdiachii);
                                        hashMapik.put("sex", sex);
                                        hashMapik.put("ngaysinh", ngaysinhff);
                                        hashMapik.put("nhommau", nhommau);
                                        hashMapik.put("chieucao", chieucao);
                                        hashMapik.put("cannang", canang);
                                        hashMapik.put("STD", sdt);
                                        DatabaseReference ref5 = FirebaseDatabase.getInstance().getReference("HoSo_BenhNhan");
                                        ref5.child(mksas).setValue(hashMapik);


                                        tv1.setText("");
                                        mabhyt.setText("");
                                        tvdiachi.setText("");
                                        buttonDate.setText("");
                                        tvchieucao.setText("");
                                        tvcannang.setText("");
                                        img1.setImageURI(null);
                                        image_url = null;

                                        pd.dismiss();
                                        Toast.makeText(Comfirm_In4_Patient_Activity.this, "xác nhận thành công...", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Dashboard_User.class);
                                        intent.putExtra("phar","2" );
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(Comfirm_In4_Patient_Activity.this, "123" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(Comfirm_In4_Patient_Activity.this, "466"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


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

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
    private void datePickerChange(DatePicker datePicker, int year, int month, int dayOfMonth) {
        this.buttonDate.setText(dayOfMonth +"-" + (month + 1) + "-" + year);
    }

    private void showDate()  {
        jjjj.setVisibility(View.VISIBLE);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}