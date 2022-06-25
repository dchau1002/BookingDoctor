package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KhamLamSan_DT extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String idDT, idUS, malichen,malichenDT,ngaykham;
    FirebaseUser user;
    TextView tv0, tv1, tv2, tv5 ,tv6 ,tv7, tv8, tv9;
    Spinner spinnerEmployee2;
    String key;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;

    String[] country2 = { "Tốt", "Cần chú ý", "Không tốt", "Xấu", "Nghiêm trọng"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kham_lam_san__d_t);
         user = FirebaseAuth.getInstance().getCurrentUser();
        idUS = getIntent().getStringExtra("idUS");
        idDT = getIntent().getStringExtra("idDT");
        malichen = getIntent().getStringExtra("MaLichHen");
        malichenDT = getIntent().getStringExtra("malichDT");
        ngaykham = getIntent().getStringExtra("ngaykham");

        pd = new ProgressDialog(this);
        ////////////set hình thức
        spinnerEmployee2 = (Spinner) findViewById(R.id.spinnerdata);
        spinnerEmployee2.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country2);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee2.setAdapter(aaa);
        ImageView back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        checkUserStatus();

        tv0 = findViewById(R.id.tv0);tv6= findViewById(R.id.tv6);
        tv1 = findViewById(R.id.tv1);tv7 = findViewById(R.id.tv7);
        tv5 = findViewById(R.id.tv5);
        Button tv5 = findViewById(R.id.btn_postsdfds);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tv0.getText().toString()) ||TextUtils.isEmpty(tv1.getText().toString())
                        || TextUtils.isEmpty(tv5.getText().toString()) || TextUtils.isEmpty(tv6.getText().toString())
                        || TextUtils.isEmpty(tv7.getText().toString()) ){
                    Toast.makeText(KhamLamSan_DT.this, "Vui lòng điền đày đủ thông tin theo yêu cầu !", Toast.LENGTH_SHORT).show();
                }else{
                    add();
                }
            }
        });
    }

    private void add() {
        pd.show();
        HashMap<String, Object> hashMapik = new HashMap<>();
        String mksas= String.valueOf(System.currentTimeMillis());
        hashMapik.put("uid",  idUS);
        hashMapik.put("CDBanDau", tv0.getText().toString());
        hashMapik.put("maKQ", mksas);
        hashMapik.put("NgNhan", tv5.getText().toString());
        hashMapik.put("NgGoc", tv1.getText().toString());
        hashMapik.put("DonThuoc", tv7.getText().toString());
        hashMapik.put("CDCuoiCung", spinnerEmployee2.getSelectedItem().toString().trim());
        hashMapik.put("idDT", user.getUid());
        hashMapik.put("ngaykham", ngaykham);
        hashMapik.put("MaLichKham",malichen);
        hashMapik.put("LoiKHuyen", tv6.getText().toString());
        DatabaseReference ref5 = FirebaseDatabase.getInstance().getReference("KetQuaKham");
        ref5.child(mksas).setValue(hashMapik).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                tv0.setText("");
                tv1.setText("");
                tv5.setText("");
                tv6.setText("");
                tv7.setText("");
                Toast.makeText(KhamLamSan_DT.this, "Thêm Thành Công !", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                update();
                onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(KhamLamSan_DT.this, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
    private void update() {

       if(malichenDT.equals("null")){
           HashMap<String, Object> hashMapik = new HashMap<>();
           hashMapik.put("Trangthai", "old");
           DatabaseReference ref5 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
           ref5.child(malichen).updateChildren(hashMapik);
       }else {
           HashMap<String, Object> hashMapikdd = new HashMap<>();
           hashMapikdd.put("trangthai", "old");
           DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("SoLuongĐKy");
           ref4.child(malichenDT).child(idUS).updateChildren(hashMapikdd);

           HashMap<String, Object> hashMapik = new HashMap<>();
           hashMapik.put("Trangthai", "old");
           DatabaseReference ref5 = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
           ref5.child(malichen).updateChildren(hashMapik);
       }


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