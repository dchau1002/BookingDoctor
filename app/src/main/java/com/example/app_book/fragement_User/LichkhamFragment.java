package com.example.app_book.fragement_User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_book.Adapter.Adapter_LichKhamofUser;
import com.example.app_book.Adapter.Adapter_LichkhamOfDT;
import com.example.app_book.Dashboard_User;
import com.example.app_book.Model.Model_LichKhamDT;
import com.example.app_book.Model.Model_LichKhamUS;
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


public class LichkhamFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String name, sdt;
    RecyclerView LichkhamUS;
    Adapter_LichKhamofUser Adapter_LichkhamOfUS;
    List<Model_LichKhamUS> Model_LichKhamUS;
    TextView kkssk;
    RecyclerView LichkhamUS1;
    Adapter_LichKhamofUser Adapter_LichkhamOfUS1;
    List<Model_LichKhamUS> Model_LichKhamUS1;
    public LichkhamFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lichkham_user, container, false);

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
        kkssk = view.findViewById(R.id.textV1);

        LichkhamUS = view.findViewById(R.id.rcvlhus_1);
        LichkhamUS.setHasFixedSize(true);
        LichkhamUS.setLayoutManager(new LinearLayoutManager(getContext()));


        Model_LichKhamUS = new ArrayList<>();
        Adapter_LichkhamOfUS = new Adapter_LichKhamofUser(getContext(), Model_LichKhamUS);
        showlichkham();
        /////////////////////////////

        LichkhamUS1 = view.findViewById(R.id.rcvlhus_2);
        LichkhamUS1.setHasFixedSize(true);
        LichkhamUS1.setLayoutManager(new LinearLayoutManager(getContext()));


        Model_LichKhamUS1 = new ArrayList<>();
        Adapter_LichkhamOfUS1 = new Adapter_LichKhamofUser(getContext(), Model_LichKhamUS1);
        showlichkham1();


        return view;
    }


    private void showlichkham() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
        Query rs = ref.orderByChild("uid").equalTo(user.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_LichKhamUS model_post = ds.getValue(Model_LichKhamUS.class);
                    if(model_post.getTrangthai().equals("new") && model_post.getNgaykham().equals(Dashboard_User.NgayThangHienTai)){
                        Model_LichKhamUS.add(model_post);

                    }

                    LichkhamUS.setAdapter(Adapter_LichkhamOfUS);
                    Adapter_LichkhamOfUS.notifyDataSetChanged();
                }
                if(Model_LichKhamUS == null){
                    kkssk.setVisibility(View.VISIBLE);
                }else{
                    kkssk.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void showlichkham1() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LichHenchitiet");
        Query rs = ref.orderByChild("uid").equalTo(user.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS1.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_LichKhamUS model_post = ds.getValue(Model_LichKhamUS.class);
                    if(model_post.getTrangthai().equals("new") && !model_post.getNgaykham().equals(Dashboard_User.NgayThangHienTai)){
                        Model_LichKhamUS1.add(model_post);
                    }
                    LichkhamUS1.setAdapter(Adapter_LichkhamOfUS1);
                    Adapter_LichkhamOfUS1.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void reloadata(){
        Toast.makeText(getActivity(), "Reload Data", Toast.LENGTH_SHORT).show();
    }
}