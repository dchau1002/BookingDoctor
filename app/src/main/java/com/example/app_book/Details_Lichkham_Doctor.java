package com.example.app_book;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Adapter.Adapter_LichkhamOfDT;
import com.example.app_book.Adapter.Apdapter_LichKham_DTUS;
import com.example.app_book.Model.Model_HosoBN;
import com.example.app_book.Model.Model_LichKhamDT;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Details_Lichkham_Doctor extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    TextView buttonDate;
    TextView name, sdt, chuyenkhoa, hinhthuc, diachi, seenCmt, loadall, tvno;
    ImageView avatar;
    FirebaseAuth firebaseAuth;
    private ActionBar actionBar;
    String uida;
    FirebaseUser user;
    RelativeLayout jjjj;
    DatabaseReference users;
    RecyclerView LichkhamDT;
    Apdapter_LichKham_DTUS Adapter_LichkhamOfDT;
    List<Model_LichKhamDT> Model_LichKhamDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_lichkham_doctor);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        jjjj = findViewById(R.id.datechoo);
        name = findViewById(R.id.nametvdb);
        sdt = findViewById(R.id.emailtvdb);
        avatar = findViewById(R.id.avatar_db);
        loadall = findViewById(R.id.loadall);

        tvno = findViewById(R.id.tvno);
        hinhthuc = findViewById(R.id.ooop);
        chuyenkhoa = findViewById(R.id.xemcmt);
        diachi = findViewById(R.id.Desc);
        ImageView back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ////////////// set date
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());



        Intent intent = getIntent();
        users = FirebaseDatabase.getInstance().getReference("Doctor");
        uida = ""+intent.getStringExtra("uid");
         TextView jjkk = findViewById(R.id.btnhantin);
        jjkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details_Lichkham_Doctor.this, ChatActivity.class);
                intent.putExtra("hisUid",uida );
                intent.putExtra("uidchat",user.getUid() );
                startActivity(intent);
            }
        });
        Query query = FirebaseDatabase.getInstance().getReference("Doctor").orderByChild("uid").equalTo(uida);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){
                    String nameaa = ""+ ds.child("Name").getValue();
                    String sdtt = ""+ ds.child("STD").getValue();
                    String hinhthaaa = ""+ ds.child("typehoatdong").getValue();
                    String chuyenda = ""+ ds.child("chuyenkhoa").getValue();
                    String diachiaii = ""+ ds.child("diachi").getValue();
                    String uDp = ""+ ds.child("avatar").getValue();

                    name.setText(nameaa);
                    sdt.setText(sdtt);
                    hinhthuc.setText("Hình thức: "+hinhthaaa);
                    chuyenkhoa.setText("Chuyên Khoa: "+chuyenda);
                    diachi.setText("Địa chỉ: "+diachiaii);
                    try {
                        Picasso.get().load(uDp).placeholder(R.drawable.ic_face).into(avatar);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.hi).into(avatar);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //// rcv lichkham
        LichkhamDT = findViewById(R.id.rcvlichkham);
        LichkhamDT.setHasFixedSize(true);
        LichkhamDT.setLayoutManager(new LinearLayoutManager(this));


        Model_LichKhamDT = new ArrayList<>();
        Adapter_LichkhamOfDT = new Apdapter_LichKham_DTUS(this, Model_LichKhamDT);
        loadall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showlichkham(uida);
            }
        });
        showlichkham(uida);
    }

    private void showlichkham(String uida) {
        tvno.setVisibility(View.GONE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lichkham_Doctor");
        Query rs = ref.orderByChild("uid").equalTo(uida);
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamDT.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_LichKhamDT model_post = ds.getValue(Model_LichKhamDT.class);
                    if(model_post.getTrangthai().equals("new")){
                        Model_LichKhamDT.add(model_post);
                    }
                    LichkhamDT.setAdapter(Adapter_LichkhamOfDT);
                    Adapter_LichkhamOfDT.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
        }else{
            startActivity(new Intent(Details_Lichkham_Doctor.this, PharmacyActivity.class));
            finish();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    private void showlichkhamLOC(String fsf) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lichkham_Doctor");
        Query rs = ref.orderByChild("uid").equalTo(uida);
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamDT.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_LichKhamDT model_post = ds.getValue(Model_LichKhamDT.class);
                    if(model_post.getTrangthai().equals("new") && model_post.getNgaythang().equals(fsf)){
                        Model_LichKhamDT.add(model_post);
                    }
                    LichkhamDT.setAdapter(Adapter_LichkhamOfDT);
                    Adapter_LichkhamOfDT.notifyDataSetChanged();
                }

                if(Model_LichKhamDT == null){
                    tvno.setVisibility(View.VISIBLE);
                }else {
                    tvno.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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
                showlichkhamLOC(date);
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