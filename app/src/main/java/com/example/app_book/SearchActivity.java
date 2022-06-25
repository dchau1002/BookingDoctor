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

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView rcv_user;
    Adapter_doctor adapter_user;
    List<Model_doctor> userList;
    FirebaseAuth firebaseAuth;
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
        String key = ""+intent.getStringExtra("key");
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
                               if(query.toLowerCase().contains("viêm tuyến tiền liệt".toLowerCase())
                               || query.toLowerCase().contains("xuất tinh sớm".toLowerCase())
                               || query.toLowerCase().contains("viêm đường tiết niệu".toLowerCase()) ){
                              String query1 = "Nam khoa";
                              searchUsers(query1);
                        }else if(query.toLowerCase().contains("đái tháo đường".toLowerCase())
                               || query.toLowerCase().contains("tiểu đường".toLowerCase())
                               || query.toLowerCase().contains("cường giáp".toLowerCase())
                               || query.toLowerCase().contains("suy giáp".toLowerCase())){
                                String query2 = "Nội tiết";
                                searchUsers(query2);
                       }else if(query.toLowerCase().contains("cảm cúng".toLowerCase())
                               || query.toLowerCase().contains("viêm thanh quản".toLowerCase())
                               || query.toLowerCase().contains("viêm thế quản".toLowerCase())
                               || query.toLowerCase().contains("viêm tiểu phế".toLowerCase())
                               || query.toLowerCase().contains("viêm phổi".toLowerCase())){
                               String query3 = "Hô hấp";
                               searchUsers(query3);
                       }else if(query.toLowerCase().contains("viêm tai giữa".toLowerCase())
                               || query.toLowerCase().contains("viêm họng".toLowerCase())
                               || query.toLowerCase().contains("viêm mũi xoang".toLowerCase())
                               || query.toLowerCase().contains("viêm amidan".toLowerCase())
                               || query.toLowerCase().contains("viêm xoang mũi dị ứng".toLowerCase())){
                               String query4 = "Tai – mũi – họng";
                               searchUsers(query4);
                       }else if(query.toLowerCase().contains("sâu răng".toLowerCase())
                               || query.toLowerCase().contains("viêm tủy răng".toLowerCase())
                               || query.toLowerCase().contains("viêm nướu".toLowerCase())
                               || query.toLowerCase().contains("viêm nha chu".toLowerCase())
                               || query.toLowerCase().contains("hôi miệng".toLowerCase()) ){
                           String query5 = "Răng – hàm – mặt";
                           searchUsers(query5);
                       }else if(query.toLowerCase().contains("mụn".toLowerCase())
                               || query.toLowerCase().contains("viêm da tiết bã".toLowerCase())
                               || query.toLowerCase().contains("da nổi mề đay".toLowerCase())
                               || query.toLowerCase().contains("lở miệng".toLowerCase())){
                           String query6 = "Da liễu";
                           searchUsers(query6);
                       }else if(query.toLowerCase().contains("thiếu máu cơ tim".toLowerCase())
                               || query.toLowerCase().contains("viêm cơ tim".toLowerCase())
                               || query.toLowerCase().contains("suy tim".toLowerCase())
                               || query.toLowerCase().contains("rối loạn nhịp tim".toLowerCase())
                               || query.toLowerCase().contains("cao huyết áp".toLowerCase())
                               || query.toLowerCase().contains("nhồi máu cơ tim".toLowerCase())){
                           String query7 = "Tim mạch";
                           searchUsers(query7);
                       }else if(query.toLowerCase().contains("Đục thủy tinh thể".toLowerCase())
                               || query.toLowerCase().contains("khô mắt".toLowerCase())
                               || query.toLowerCase().contains("khối u mắt".toLowerCase())
                               || query.toLowerCase().contains("viêm màng bồ đào".toLowerCase())){
                           String query8 = "Nhãn khoa";
                           searchUsers(query8);
                       }else if(query.toLowerCase().contains("parkinson".toLowerCase())
                               || query.toLowerCase().contains("động kinh".toLowerCase())
                               || query.toLowerCase().contains("sa sút trí tuệ".toLowerCase())
                               || query.toLowerCase().contains("đau nữa đầu".toLowerCase())
                               || query.toLowerCase().contains("u não".toLowerCase())
                               || query.toLowerCase().contains("trầm cảm".toLowerCase())
                               || query.toLowerCase().contains("tâm thần phân liệt".toLowerCase())
                               || query.toLowerCase().contains("thiểu năng trí tuệ".toLowerCase())
                               || query.toLowerCase().contains("tự kỷ".toLowerCase())){
                           String query9 = "Thần Kinh";
                           searchUsers(query9);
                       }


                    }else{
                        getAlluser();
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String query) {
                    if (!TextUtils.isEmpty(query.trim())){
                        if(query.toLowerCase().contains("viêm tuyến tiền liệt".toLowerCase())
                                || query.toLowerCase().contains("xuất tinh sớm".toLowerCase())
                                || query.toLowerCase().contains("viêm đường tiết niệu".toLowerCase()) ){
                            String query1 = "Nam khoa";
                            searchUsers(query1);
                        }else if(query.toLowerCase().contains("đái tháo đường".toLowerCase())
                                || query.toLowerCase().contains("tiểu đường".toLowerCase())
                                || query.toLowerCase().contains("cường giáp".toLowerCase())
                                || query.toLowerCase().contains("suy giáp".toLowerCase())){
                            String query2 = "Nội tiết";
                            searchUsers(query2);
                        }else if(query.toLowerCase().contains("cảm cúng".toLowerCase())
                                || query.toLowerCase().contains("viêm thanh quản".toLowerCase())
                                || query.toLowerCase().contains("viêm thế quản".toLowerCase())
                                || query.toLowerCase().contains("viêm tiểu phế".toLowerCase())
                                || query.toLowerCase().contains("viêm phổi".toLowerCase())){
                            String query3 = "Hô hấp";
                            searchUsers(query3);
                        }else if(query.toLowerCase().contains("viêm tai giữa".toLowerCase())
                                || query.toLowerCase().contains("viêm họng".toLowerCase())
                                || query.toLowerCase().contains("viêm mũi xoang".toLowerCase())
                                || query.toLowerCase().contains("viêm amidan".toLowerCase())
                                || query.toLowerCase().contains("viêm xoang mũi dị ứng".toLowerCase())){
                            String query4 = "Tai – mũi – họng";
                            searchUsers(query4);
                        }else if(query.toLowerCase().contains("sâu răng".toLowerCase())
                                || query.toLowerCase().contains("viêm tủy răng".toLowerCase())
                                || query.toLowerCase().contains("viêm nướu".toLowerCase())
                                || query.toLowerCase().contains("viêm nha chu".toLowerCase())
                                || query.toLowerCase().contains("hôi miệng".toLowerCase()) ){
                            String query5 = "Răng – hàm – mặt";
                            searchUsers(query5);
                        }else if(query.toLowerCase().contains("mụn".toLowerCase())
                                || query.toLowerCase().contains("viêm da tiết bã".toLowerCase())
                                || query.toLowerCase().contains("da nổi mề đay".toLowerCase())
                                || query.toLowerCase().contains("lở miệng".toLowerCase())){
                            String query6 = "Da liễu";
                            searchUsers(query6);
                        }else if(query.toLowerCase().contains("thiếu máu cơ tim".toLowerCase())
                                || query.toLowerCase().contains("viêm cơ tim".toLowerCase())
                                || query.toLowerCase().contains("suy tim".toLowerCase())
                                || query.toLowerCase().contains("rối loạn nhịp tim".toLowerCase())
                                || query.toLowerCase().contains("cao huyết áp".toLowerCase())
                                || query.toLowerCase().contains("nhồi máu cơ tim".toLowerCase())){
                            String query7 = "Tim mạch";
                            searchUsers(query7);
                        }else if(query.toLowerCase().contains("Đục thủy tinh thể".toLowerCase())
                                || query.toLowerCase().contains("khô mắt".toLowerCase())
                                || query.toLowerCase().contains("khối u mắt".toLowerCase())
                                || query.toLowerCase().contains("viêm màng bồ đào".toLowerCase())){
                            String query8 = "Nhãn khoa";
                            searchUsers(query8);
                        }else if(query.toLowerCase().contains("parkinson".toLowerCase())
                                || query.toLowerCase().contains("động kinh".toLowerCase())
                                || query.toLowerCase().contains("sa sút trí tuệ".toLowerCase())
                                || query.toLowerCase().contains("đau nữa đầu".toLowerCase())
                                || query.toLowerCase().contains("u não".toLowerCase())
                                || query.toLowerCase().contains("trầm cảm".toLowerCase())
                                || query.toLowerCase().contains("tâm thần phân liệt".toLowerCase())
                                || query.toLowerCase().contains("thiểu năng trí tuệ".toLowerCase())
                                || query.toLowerCase().contains("tự kỷ".toLowerCase())){
                            String query9 = "Thần Kinh";
                            searchUsers(query9);
                        }
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
                        if (model_doctor.getName().toLowerCase().contains(query.toLowerCase())
                                || model_doctor.getChuyenkhoa().toLowerCase().contains(query.toLowerCase())){
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

    private void getAlluser() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctor");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Model_doctor model_doctor = ds.getValue(Model_doctor.class);
                    if(!model_doctor.getUid().equals(fUser.getUid()) && model_doctor.getType().equals("doctor")){
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
            startActivity(new Intent(SearchActivity.this, PharmacyActivity.class));
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


