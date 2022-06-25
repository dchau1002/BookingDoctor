package com.example.app_book;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
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

public class Comfirm_In4_Doctor_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePicker datePicker;
    TextView buttonDate;
    EditText tv1, dichilamvc, tvdiachi, mota;
    ImageView img1;
    Button submit;
    RelativeLayout jjjj;
    Spinner spinnerEmployee;
    Spinner Spinnerchuyenkhoa;
    Uri image_url = null;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private StorageReference storageReference1;
    String uid, sdt, email;
    ProgressDialog pd;

    Spinner spinnerEmployee2;
    String[] country = { "Nam", "Nữ"};
    String[] chuyenkhoa = { "Nam khoa", "Nội tiết", "Nhi Khoa", "Hô hấp", "Tai – mũi – họng", "Răng - hàm – mặt", "Da liễu", "Tim mạch", "Nhãn khoa", "Thần Kinh"};
    String[] country2 = { "Bác sĩ", "Phòng khám", "Bệnh Viện", "Phòng Xét nghiệm"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_in4_doctor);
        ImageButton back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //get idUSer
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        pd = new ProgressDialog(this);
        jjjj = findViewById(R.id.datechoo);
        datePicker = (DatePicker) this.findViewById(R.id.datePicker);

        buttonDate = findViewById(R.id.tvcontentdate);
        tv1 = findViewById(R.id.tv1);
        dichilamvc = findViewById(R.id.tvcontent1);
        tvdiachi = findViewById(R.id.tvcontent2);
        mota = findViewById(R.id.tvcontent4);
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
        sdt = getIntent().getStringExtra("sdt");
        email = getIntent().getStringExtra("email");
//        mk = getIntent().getStringExtra("mk");
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
//        spinnerEmployee2 = (Spinner) findViewById(R.id.spinnerdata);
//        spinnerEmployee2.setOnItemSelectedListener(this);
//        //Creating the ArrayAdapter instance having the country list
//        ArrayAdapter aaa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country2);
//        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerEmployee2.setAdapter(aaa);

        ////////////set chuyenkhoa
        Spinnerchuyenkhoa = (Spinner) findViewById(R.id.spinnerchuyenkhoa);
        Spinnerchuyenkhoa.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bbb = new ArrayAdapter(this, android.R.layout.simple_spinner_item, chuyenkhoa);
        bbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinnerchuyenkhoa.setAdapter(bbb);



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
            image_url = null;
            startActivity(intent);
        }        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upData() {
        String tv11 = tv1.getText().toString().trim();
        String DCLM = dichilamvc.getText().toString().trim();
        String tvdiachii = tvdiachi.getText().toString().trim();
        String ngaysinh = buttonDate.getText().toString().trim();
        String motaa = mota.getText().toString().trim();

        if(TextUtils.isEmpty(tv11) || TextUtils.isEmpty(DCLM) ||
                TextUtils.isEmpty(tvdiachii) || TextUtils.isEmpty(ngaysinh) ||
                TextUtils.isEmpty(motaa)) {
            Toast.makeText(Comfirm_In4_Doctor_Activity.this, "Vui lòng điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();

        }
       else if(image_url != null) {
            uploaddata();
        } else {
            Toast.makeText(Comfirm_In4_Doctor_Activity.this, "Vui lòng điền thêm các hình ảnh thông tin cần thiết !", Toast.LENGTH_SHORT).show();
        }


    }


     private void uploaddata(){
         pd.show();
         String tv11 = tv1.getText().toString().trim();
         String DCLM = dichilamvc.getText().toString().trim();
         String tvdiachii = tvdiachi.getText().toString().trim();
         String ngaysinh = buttonDate.getText().toString().trim();
         String motaa = mota.getText().toString().trim();
         String sex = spinnerEmployee.getSelectedItem().toString().trim();
         String typehoatdong = "Bác sĩ tư";
         String chuyenkhoa = Spinnerchuyenkhoa.getSelectedItem().toString().trim();
         String timestamp = String.valueOf(System.currentTimeMillis());
         String filePathAndName = "AvtarDoctor/" + "post_"+timestamp;

         if(TextUtils.isEmpty(tv11) || TextUtils.isEmpty(DCLM) ||
                 TextUtils.isEmpty(tvdiachii) || TextUtils.isEmpty(ngaysinh)) {
             Toast.makeText(Comfirm_In4_Doctor_Activity.this, "Vui lòng điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();

         }
         else if(image_url == null) {
             Toast.makeText(Comfirm_In4_Doctor_Activity.this, "Vui lòng điền thêm các hình ảnh thông tin cần thiết !", Toast.LENGTH_SHORT).show();
         }else {

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
                         String ngaysinh = buttonDate.getText().toString();
                         HashMap<String, Object> hashMapss = new HashMap<>();
                         hashMapss.put("uid", uid);
                         hashMapss.put("Name", tv11);
                         hashMapss.put("OnlineStatus", "Online");
                         hashMapss.put("TyingTo", "noOne");
                         hashMapss.put("Type", "doctor");
                         hashMapss.put("STD", sdt);
                         hashMapss.put("avatar", dowloadUri);
                         DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                         ref3.child(uid).setValue(hashMapss)
                                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {


                                         HashMap<String, Object> hashMap = new HashMap<>();
                                         hashMap.put("uid", uid);
                                         hashMap.put("Name", tv11);
                                         hashMap.put("email", email);
                                         hashMap.put("DiaChiLamViec", DCLM);
                                         hashMap.put("OnlineStatus", "Online");
                                         hashMap.put("TyingTo", "noOne");
                                         hashMap.put("diachi", tvdiachii);
                                         hashMap.put("chuyenkhoa", chuyenkhoa);
                                         hashMap.put("sex", sex);
                                         hashMap.put("ngaysinh", ngaysinh);
                                         hashMap.put("typehoatdong", typehoatdong);
                                         hashMap.put("mota", motaa);
                                         hashMap.put("STD", sdt);
                                         hashMap.put("Type", "doctor");
                                         hashMap.put("avatar", dowloadUri);
                                         DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("Doctor");
                                         ref4.child(uid).setValue(hashMap);


                                         pd.dismiss();
                                         Toast.makeText(Comfirm_In4_Doctor_Activity.this, "xác nhận thành công...", Toast.LENGTH_SHORT).show();
                                         Intent intent = new Intent(getApplicationContext(), XacMinhBangCap.class);
                                         intent.putExtra("sdt", sdt);
                                         startActivity(intent);
                                         tv1.setText("");
                                         dichilamvc.setText("");
                                         tvdiachi.setText("");
                                         buttonDate.setText("");
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
                     Toast.makeText(Comfirm_In4_Doctor_Activity.this, "466" + e.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
         }

     }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void datePickerChange(DatePicker datePicker, int year, int month, int dayOfMonth) {
        this.buttonDate.setText(dayOfMonth +"-" + (month + 1) + "-" + year);
    }

    private void showDate()  {
        jjjj.setVisibility(View.VISIBLE);
    }
    public void onBackPressed() {
        super.onBackPressed();
    }

}