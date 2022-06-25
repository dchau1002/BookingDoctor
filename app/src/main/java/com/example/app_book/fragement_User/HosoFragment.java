package com.example.app_book.fragement_User;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_book.Adapter.Adapter_KQKhambenh;
import com.example.app_book.Adapter.Adapter_KQkham_DT;
import com.example.app_book.Choose_HoSoBenhNhan;
import com.example.app_book.Dashboard_Doctor;
import com.example.app_book.Dat_LichKhamchitiet;
import com.example.app_book.Model.Model_KQKham;
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
import java.util.Calendar;
import java.util.List;


public class HosoFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    FirebaseUser user;
    RecyclerView LichkhamUS;
    Adapter_KQKhambenh Adapter_LichkhamOfUS;
    List<Model_KQKham> Model_LichKhamUS;
    String name, sdt;

    public HosoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoso_user, container, false);
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
                for (DataSnapshot ds : snapshot.getChildren()) {
                    name = "" + ds.child("Name").getValue().toString();
                    kkk.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        TextView tyyrt = view.findViewById(R.id.tvcontent1);
        tyyrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Choose_HoSoBenhNhan.class);
                startActivity(intent);
            }
        });
        ////////////

        LichkhamUS = view.findViewById(R.id.rcvsas);
        LichkhamUS.setHasFixedSize(true);
        LichkhamUS.setLayoutManager(new LinearLayoutManager(getContext()));


        Model_LichKhamUS = new ArrayList<>();
        Adapter_LichkhamOfUS = new Adapter_KQKhambenh(getContext(), Model_LichKhamUS);
        showlichkham();
        /////////////////////////////


        return view;
    }

    private void showlichkham() {
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


    public void reloadata() {
        Toast.makeText(getActivity(), "Reload Data", Toast.LENGTH_SHORT).show();
    }
}