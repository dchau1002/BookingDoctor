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

import com.example.app_book.Adapter.Adapter_KQKhambenh;
import com.example.app_book.Adapter.Adapter_Notification;
import com.example.app_book.Model.Model_KQKham;
import com.example.app_book.Model.Model_Notification;
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


public class NoticationsFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String name, sdt;


    RecyclerView notication;
    Adapter_Notification Adapter_Notification;
    List<Model_Notification> modelNotifications;
    public NoticationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notications_user, container, false);

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


        notication = view.findViewById(R.id.rcvthonhbao);
        notication.setHasFixedSize(true);
        notication.setLayoutManager(new LinearLayoutManager(getContext()));


        modelNotifications = new ArrayList<>();
        Adapter_Notification = new Adapter_Notification(getContext(), modelNotifications);
        showwnotification();

        return view;

    }

    private void showwnotification() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Notification");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelNotifications.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_Notification model_post = ds.getValue(Model_Notification.class);
                    if (model_post.getIdTake().equals(user.getUid())){
                        modelNotifications.add(model_post);
                    }
                    notication.setAdapter(Adapter_Notification);
                    Adapter_Notification.notifyDataSetChanged();
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