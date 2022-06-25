package com.example.app_book;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.app_book.Adapter.Adapter_doctor;
import com.example.app_book.Model.Model_doctor;

import java.util.ArrayList;
import java.util.List;
public class Search_Category extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView rcv_user;
    Adapter_doctor adapter_user;
    List<Model_doctor> userList;
    FirebaseAuth firebaseAuth;
    String category;
    Spinner Spinnerchuyenkhoa;
    String[] chuyenkhoa = {"All", "Nam khoa", "Nội tiết", "Nhi Khoa", "Hô hấp", "Tai – mũi – họng", "Răng - hàm – mặt", "Da liễu", "Tim mạch", "Nhãn khoa", "Thần Kinh"};
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageButton backs = findViewById(R.id.backs);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        category = ""+intent.getStringExtra("category");
        String idgr = ""+intent.getStringExtra("idgr");

        ////////////set chuyenkhoa
        Spinnerchuyenkhoa = (Spinner) findViewById(R.id.spinnerchuyenkhoa);
        Spinnerchuyenkhoa.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bbb = new ArrayAdapter(this, android.R.layout.simple_spinner_item, chuyenkhoa);
        bbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinnerchuyenkhoa.setAdapter(bbb);

        SearchView sv = (SearchView) findViewById(R.id.sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query.trim())){
                    searchUsers(query);
                }else{
                    getAlluser();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                if (!TextUtils.isEmpty(query.trim())){
                    searchUsers(query);
                }else{
                    getAlluser();
                }
                return false;
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        rcv_user = findViewById(R.id.rcv_userss);
        rcv_user.setHasFixedSize(true);
        rcv_user.setLayoutManager(new LinearLayoutManager(this));


        userList = new ArrayList<>();
        adapter_user = new Adapter_doctor(this, userList);
        rcv_user.setAdapter(adapter_user);
        getAlluser();


        checkUserStatus();


    }



    private void searchUsers(String query) {

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctor");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Model_doctor model_doctor = ds.getValue(Model_doctor.class);
                    if(!model_doctor.getUid().equals(fUser.getUid())){
                        if (model_doctor.getName().toLowerCase().contains(query.toLowerCase())
                                && model_doctor.getType().equals("doctor")
                                && model_doctor.getTypehoatdong().equals(category)
                                || model_doctor.getChuyenkhoa().toLowerCase().contains(query.toLowerCase())){
                            userList.add(model_doctor);
                        }
                    }

                    adapter_user.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getAlluser() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctor");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Model_doctor model_doctor = ds.getValue(Model_doctor.class);
                    if(!model_doctor.getUid().equals(fUser.getUid()) && model_doctor.getType().equals("doctor")
                            && model_doctor.getTypehoatdong().equals(category)){
                        userList.add(model_doctor);
                    }
                    adapter_user.notifyDataSetChanged();
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
            startActivity(new Intent(Search_Category.this, PharmacyActivity.class));
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String abc = Spinnerchuyenkhoa.getSelectedItem().toString().trim();
        if(abc.equals("All")){
            getAlluser();
        }else{
            searchUsers(abc);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

