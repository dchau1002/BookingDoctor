package com.example.app_book.fragement_Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Adapter.Adapter_LichkhamOfDT;
import com.example.app_book.Adapter.Adapter_User_AD;
import com.example.app_book.Add_LichKham;
import com.example.app_book.Model.Model_LichKhamDT;
import com.example.app_book.Model.Model_User;
import com.example.app_book.Model.Model_doctor;
import com.example.app_book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Fragment_MN_User extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String name, sdt;
    RecyclerView LichkhamDT;
    Adapter_User_AD Adapter_LichkhamOfDT;
    List<Model_User> Model_LichKhamDT;

    public Fragment_MN_User() {
        // Required empty public constructor
    }


    public void onResume() {
        super.onResume();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_user, container, false);

        TextView kkk = view.findViewById(R.id.tvNmae);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String mUID = user.getUid();
        Query refrr = databaseReference.orderByChild("uid").equalTo(mUID);
        refrr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    name = ""+ ds.child("Name").getValue().toString();
                    kkk.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        LichkhamDT = view.findViewById(R.id.rcvassas);
        LichkhamDT.setHasFixedSize(true);
        LichkhamDT.setLayoutManager(new LinearLayoutManager(getContext()));


        Model_LichKhamDT = new ArrayList<>();
        Adapter_LichkhamOfDT = new Adapter_User_AD(getContext(), Model_LichKhamDT);




        showlichkham();

        return view;
    }
    private void showlichkham() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Patient");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamDT.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_User model_post = ds.getValue(Model_User.class);

                        Model_LichKhamDT.add(model_post);


                    LichkhamDT.setAdapter(Adapter_LichkhamOfDT);
                    Adapter_LichkhamOfDT.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}