package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.app_book.Adapter.Adapter_KQKhambenh;
import com.example.app_book.Model.Model_KQKham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LichSu_User extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    FirebaseUser user;
    RecyclerView LichkhamUS;
    String key;
    Adapter_KQKhambenh Adapter_LichkhamOfUS;
    List<Model_KQKham> Model_LichKhamUS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su__user);

        initDatePicker();
        key = getIntent().getStringExtra("Key");
        ImageButton back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        LichkhamUS = findViewById(R.id.rcvsas);
        LichkhamUS.setHasFixedSize(true);
        LichkhamUS.setLayoutManager(new LinearLayoutManager(this));
        user = FirebaseAuth.getInstance().getCurrentUser();

        Model_LichKhamUS = new ArrayList<>();
        Adapter_LichkhamOfUS = new Adapter_KQKhambenh(this, Model_LichKhamUS);
        if(key.equals("user")){
            showlichkhamus();
        }else if(key.equals("doctor")){
            showlichkham();
        }

        /////////////////////////////
        checkUserStatus();

    }

    private void showlichkham() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("KetQuaKham");
        Query rs = ref.orderByChild("idDT").equalTo(user.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_KQKham model_post = ds.getValue(Model_KQKham.class);
                    Model_LichKhamUS.add(model_post);

                    LichkhamUS.setAdapter(Adapter_LichkhamOfUS);
                    Adapter_LichkhamOfUS.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void showlichkhamLOC(String fsf) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("KetQuaKham");
        Query rs = ref.orderByChild("idDT").equalTo(user.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_KQKham model_post = ds.getValue(Model_KQKham.class);
                    if(model_post.getNgaykham().equals(fsf)){
                        Model_LichKhamUS.add(model_post);
                    }

                    LichkhamUS.setAdapter(Adapter_LichkhamOfUS);
                    Adapter_LichkhamOfUS.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void showlichkhamus() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("KetQuaKham");
        Query rs = ref.orderByChild("uid").equalTo(user.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_KQKham model_post = ds.getValue(Model_KQKham.class);
                    Model_LichKhamUS.add(model_post);

                    LichkhamUS.setAdapter(Adapter_LichkhamOfUS);
                    Adapter_LichkhamOfUS.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void showlichkhamLOCus(String fsf) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("KetQuaKham");
        Query rs = ref.orderByChild("uid").equalTo(user.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_KQKham model_post = ds.getValue(Model_KQKham.class);
                    if(model_post.getNgaykham().equals(fsf)){
                        Model_LichKhamUS.add(model_post);
                    }

                    LichkhamUS.setAdapter(Adapter_LichkhamOfUS);
                    Adapter_LichkhamOfUS.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                if(key.equals("user")){
                    showlichkhamLOCus(date);
                }else if(key.equals("doctor")){
                    showlichkhamLOC(date);
                }

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