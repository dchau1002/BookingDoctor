package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.HashMap;

public class Update_profile_Doctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tv0, tv1, tv2, tv5 ,tv6 ,tv7, tv8, tv9;
    Spinner spinnerEmployee;
    Spinner spinnerEmployee2;
    Spinner Spinnerchuyenkhoa;
    FirebaseAuth firebaseAuth;
    String uid;
    ImageView lkls;
    ProgressDialog pd;
    Uri image_url = null;
    private DatePickerDialog datePickerDialog;
    String[] country = { "Nam", "N???"};
    String[] chuyenkhoa = { "Nam khoa", "N???i ti???t", "Nhi Khoa", "H?? h???p", "Tai ??? m??i ??? h???ng", "R??ng - h??m ??? m???t", "Da li???u", "Tim m???ch", "Nh??n khoa", "Th???n Kinh"};
    String[] country2 = { "B??c s??", "Ph??ng kh??m", "B???nh Vi???n", "Ph??ng X??t nghi???m"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile__doctor);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        pd = new ProgressDialog(this);
        ////////////set gi???i t??nh
        spinnerEmployee = (Spinner) findViewById(R.id.spinner_employee);
        spinnerEmployee.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee.setAdapter(aa);

        ////////////set h??nh th???c
//        spinnerEmployee2 = (Spinner) findViewById(R.id.spinnerdata);
//        spinnerEmployee2.setOnItemSelectedListener(this);
//        //Creating the ArrayAdapter instance having the country list
//        ArrayAdapter aaa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country2 );
//        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerEmployee2.setAdapter(aaa);
        ////////////set h??nh th???c
        Spinnerchuyenkhoa = (Spinner) findViewById(R.id.spinnerdata1);
        Spinnerchuyenkhoa.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bbb = new ArrayAdapter(this, android.R.layout.simple_spinner_item, chuyenkhoa );
        bbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinnerchuyenkhoa.setAdapter(bbb);
        ///////
        ImageView back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lkls = findViewById(R.id.avatar_user);
        tv6= findViewById(R.id.tv6);
        tv1 = findViewById(R.id.tv1);tv7 = findViewById(R.id.tv7);
        tv2 = findViewById(R.id.tv2);tv8 = findViewById(R.id.tv8);

        initDatePicker();
        tv2.setText(getTodaysDate());

        Button tv5 = findViewById(R.id.btn_post);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(TextUtils.isEmpty(tv1.getText().toString())
                            || TextUtils.isEmpty(tv6.getText().toString())
                            || TextUtils.isEmpty(tv7.getText().toString())
                            || TextUtils.isEmpty(tv8.getText().toString())){
                        Toast.makeText(Update_profile_Doctor.this, "Vui l??ng ??i???n ????y ????? th??ng tin theo y??u c???u !", Toast.LENGTH_SHORT).show();
                    }else if (image_url == null){
                        UpdateMy();
                    } else if (image_url != null){
                        UpdateMyImgage();
                    }

            }
        });
        lkls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImagePic();
            }
        });
        showdtaUpdat();
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
    private void UpdateMyImgage() {
        pd.show();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "AvtarDoctor/" + "post_"+timestamp;

        Bitmap bitmap = ((BitmapDrawable)lkls.getDrawable()).getBitmap();
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
                    HashMap<String, Object> hashMapik = new HashMap<>();
                    hashMapik.put("Name", tv1.getText().toString());
                    hashMapik.put("diachi", tv7.getText().toString());
                    hashMapik.put("sex", spinnerEmployee.getSelectedItem().toString().trim());
                    hashMapik.put("STD", tv6.getText().toString());
                    hashMapik.put("ngaysinh", tv2.getText().toString());
                    hashMapik.put("mota", tv8.getText().toString());
                    hashMapik.put("chuyenkhoa", Spinnerchuyenkhoa.getSelectedItem().toString().trim());
                    hashMapik.put("avatar", dowloadUri);

                    DatabaseReference ref6= FirebaseDatabase.getInstance().getReference("Doctor");
                    ref6.child(uid).updateChildren(hashMapik)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    tv1.setText("");
                                    tv2.setText("Ng??y th??ng n??m sinh");
                                    tv5.setText("");
                                    tv6.setText("");
                                    tv7.setText("");
                                    tv8.setText("");
                                    pd.dismiss();
                                    onBackPressed();
                                    image_url = null;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(Update_profile_Doctor.this, "123" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Update_profile_Doctor.this, "466"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateMy() {
        pd.show();
        HashMap<String, Object> hashMapik = new HashMap<>();
        hashMapik.put("Name", tv1.getText().toString());
        hashMapik.put("diachi", tv7.getText().toString());
        hashMapik.put("sex", spinnerEmployee.getSelectedItem().toString().trim());
        hashMapik.put("STD", tv6.getText().toString());
        hashMapik.put("ngaysinh", tv2.getText().toString());
        hashMapik.put("mota", tv8.getText().toString());
        hashMapik.put("chuyenkhoa", Spinnerchuyenkhoa.getSelectedItem().toString().trim());


        DatabaseReference ref5 = FirebaseDatabase.getInstance().getReference("Doctor");
        ref5.child(uid).updateChildren(hashMapik).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                tv1.setText("");
                tv2.setText("Ng??y th??ng n??m sinh");
                tv6.setText("");
                tv7.setText("");
                tv8.setText("");
                Toast.makeText(Update_profile_Doctor.this, "C???p Nh???t Th??nh C??ng !", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Update_profile_Doctor.this, "c???p nh???t th???t b???i !", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
    private void showdtaUpdat() {
        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("Doctor");
        final Query ref5 = databaseReference5.orderByChild("uid").equalTo(uid);
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    tv1.setText(""+ ds.child("Name").getValue().toString());
                    tv2.setText(""+ ds.child("ngaysinh").getValue().toString());
                    tv6.setText(""+ ds.child("STD").getValue().toString());
                    tv7.setText(""+ ds.child("diachi").getValue().toString());
                    tv8.setText(""+ ds.child("mota").getValue().toString());

                    String imagedb = ""+ ds.child("avatar").getValue().toString();
                    try {
                        Picasso.get().load(imagedb).into(lkls);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(lkls);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            lkls.setImageURI(image_url);

        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Intent intent = new Intent(this, Comfirm_In4_Doctor_Activity.class);
            startActivity(intent);
        }        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                tv2.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year)
    {
        return "Ng??y " + day + " - " + getMonthFormat(month) + " - " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Th??ng 1";
        if(month == 2)
            return "Th??ng 2";
        if(month == 3)
            return "Th??ng 3";
        if(month == 4)
            return "Th??ng 4";
        if(month == 5)
            return "Th??ng 5";
        if(month == 6)
            return "Th??ng 6";
        if(month == 7)
            return "Th??ng 7";
        if(month == 8)
            return "Th??ng 8";
        if(month == 9)
            return "Th??ng 9";
        if(month == 10)
            return "Th??ng 10";
        if(month == 11)
            return "Th??ng 11";
        if(month == 12)
            return "Th??ng 12";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}