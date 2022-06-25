package com.example.app_book.fragement_User;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_book.Adapter.Adpter_post;
import com.example.app_book.Adapter.SliderAdapter;

import com.example.app_book.Cau_Hoi_Thuong_Gap;
import com.example.app_book.Dashboard_Doctor;
import com.example.app_book.Dashboard_User;
import com.example.app_book.Kham_TrucTiep;
import com.example.app_book.LichSu_User;
import com.example.app_book.Model.Mode_Post;
import com.example.app_book.QuyDinh_App;
import com.example.app_book.QuyTrinhDatLich;
import com.example.app_book.R;
import com.example.app_book.Search_Category;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeFragment extends Fragment {
    ActionBarDrawerToggle mToggle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    RecyclerView LichkhamUS;
    Adpter_post Adapter_LichkhamOfUS;
    List<Mode_Post> Model_LichKhamUS;
    String name, sdt;

    public HomeFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    SliderView sliderView;
    int[] images = {R.drawable.bn1,
            R.drawable.bn2,
            R.drawable.bn3,
            R.drawable.bn4};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_user, container, false);

        sliderView = view.findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

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




        CardView ca1 = view.findViewById(R.id.ca1);
        CardView ca2 = view.findViewById(R.id.ca2);
        CardView ca3 = view.findViewById(R.id.ca3);
        CardView ca4 = view.findViewById(R.id.ca4);
        CardView ca5 = view.findViewById(R.id.ca5);
        CardView ca6 = view.findViewById(R.id.ca6);
        String[] country2 = { "Bác sĩ", "Phòng khám", "Bệnh Viện", "Phòng Xét nghiệm"};
        ca1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search_Category.class);
                intent.putExtra("category","Bác sĩ tư" );
                startActivity(intent);
            }
        });
        ca2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuyTrinhDatLich.class);
                startActivity(intent);
            }
        });
        ca3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuyDinh_App.class);
                startActivity(intent);
            }
        });
        ca4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Cau_Hoi_Thuong_Gap.class);
                startActivity(intent);
            }
        });
        ca5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), Kham_TrucTiep.class);
                startActivity(intent2);
            }
        });
        ca6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getContext(), LichSu_User.class);
                intent2.putExtra("Key","user" );
                startActivity(intent2);

            }
        });


        LichkhamUS = view.findViewById(R.id.rcvposst);
        LichkhamUS.setHasFixedSize(true);
        LichkhamUS.setLayoutManager(new LinearLayoutManager(getContext()));


        Model_LichKhamUS = new ArrayList<>();
        Adapter_LichkhamOfUS = new Adpter_post(getContext(), Model_LichKhamUS);
        showposst();
        return view;
    }
    private void showposst() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post");
//        Query rs = ref.orderByChild("iddoctor").equalTo(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Mode_Post model_post = ds.getValue(Mode_Post.class);
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


    public void reloadata(){
        Toast.makeText(getActivity(), "Reload Data", Toast.LENGTH_SHORT).show();
    }




}

