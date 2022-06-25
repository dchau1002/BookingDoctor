package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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

import java.util.Calendar;
import java.util.HashMap;

public class Add_LichKham extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    private FirebaseUser user;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    ProgressDialog pd;
    String abcd = "123";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lich_kham);
        checkUserStatus();
        ImageButton back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ////////////// set date
        pd = new ProgressDialog(this);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());


       Button xacnhan = findViewById(R.id.btn_post);
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktralichkhamtrunglap();
            }
        });

    }
    private void ktralichkhamtrunglap() {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lichkham_Doctor");
      //  Query rs = ref.orderByChild("Ngaythang").equalTo(dateButton.getText().toString());
        ref.orderByChild("Ngaythang").equalTo(dateButton.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //bus number exists in Database
                    Toast.makeText(Add_LichKham.this, "Ngày tháng năm này đã có lịch, Vui lòng đặt lại !", Toast.LENGTH_SHORT).show();
                } else {
                    //bus number doesn't exists.
                    upload();
                }


             }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void upload() {
       pd.show();
          String timestamp = String.valueOf(System.currentTimeMillis());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        EditText ghichu = findViewById(R.id.tvcontent1);
        HashMap<String, Object> hashMapjj = new HashMap<>();
        hashMapjj.put("uid", user.getUid());
        hashMapjj.put("NameDT", Dashboard_Doctor.Namedt);
        hashMapjj.put("Ngaythang", dateButton.getText().toString());
        hashMapjj.put("Ghichu", ghichu.getText().toString());
        hashMapjj.put("MaLichKham", timestamp);
        hashMapjj.put("Trangthai", "new");
        DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("Lichkham_Doctor");
        ref4.child(timestamp).setValue(hashMapjj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Add_LichKham.this, "Tạo Lịch Thành Công !", Toast.LENGTH_SHORT).show();
                ghichu.setText(" ");
                pd.dismiss();
                onBackPressed();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Add_LichKham.this, "Tạo Lịch Thất bại !", Toast.LENGTH_SHORT).show();
               pd.dismiss();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void checkUserStatus(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){


        }else{
            startActivity(new Intent(this, PharmacyActivity.class));
            finish();
        }
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
                dateButton.setText(date);
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
        return "Ngày " + day + " - " + getMonthFormat(month) + " - " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Tháng 1";
        if(month == 2)
            return "Tháng 2";
        if(month == 3)
            return "Tháng 3";
        if(month == 4)
            return "Tháng 4";
        if(month == 5)
            return "Tháng 5";
        if(month == 6)
            return "Tháng 6";
        if(month == 7)
            return "Tháng 7";
        if(month == 8)
            return "Tháng 8";
        if(month == 9)
            return "Tháng 9";
        if(month == 10)
            return "Tháng 10";
        if(month == 11)
            return "Tháng 11";
        if(month == 12)
            return "Tháng 12";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

}