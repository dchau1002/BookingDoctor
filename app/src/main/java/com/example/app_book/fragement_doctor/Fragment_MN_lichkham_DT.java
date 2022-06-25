package com.example.app_book.fragement_doctor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_book.Adapter.Adapter_LichkhamOfDT;
import com.example.app_book.Adapter.Adapter_doctor;
import com.example.app_book.Add_LichKham;
import com.example.app_book.Add_Post;
import com.example.app_book.Model.Model_LichKhamDT;
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


public class Fragment_MN_lichkham_DT extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String name, key;
    RecyclerView LichkhamDT;
    Adapter_LichkhamOfDT Adapter_LichkhamOfDT;
    List<Model_LichKhamDT> Model_LichKhamDT;

    public Fragment_MN_lichkham_DT() {
        // Required empty public constructor
    }

    public void onResume() {
        super.onResume();
    }
    public void reloadata(){
        Toast.makeText(getActivity(), "Reload Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lichkham_doctor, container, false);

        TextView kkk = view.findViewById(R.id.tvNmae);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctor");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String mUID = user.getUid();
        Query refrr = databaseReference.orderByChild("uid").equalTo(mUID);
        refrr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    name = ""+ ds.child("Name").getValue().toString();
                    key = ""+ ds.child("kiemduyet").getValue().toString();
                    kkk.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CardView addkkk = view.findViewById(R.id.btanasas);
        addkkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.equals("true")){
                    Intent intent = new Intent(getActivity(), Add_LichKham.class);
                    intent.putExtra("uID",mUID );
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Tài khoản bạn chưa được phê duyệt", Toast.LENGTH_SHORT).show();
                }
            }
        });


        LichkhamDT = view.findViewById(R.id.rcvassas);
        LichkhamDT.setHasFixedSize(true);
        LichkhamDT.setLayoutManager(new LinearLayoutManager(getContext()));


        Model_LichKhamDT = new ArrayList<>();
        Adapter_LichkhamOfDT = new Adapter_LichkhamOfDT(getContext(), Model_LichKhamDT);




        showlichkham();

        return view;
    }
    public void showlichkham() {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lichkham_Doctor");
        Query rs = ref.orderByChild("uid").equalTo(users.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamDT.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_LichKhamDT model_post = ds.getValue(Model_LichKhamDT.class);
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