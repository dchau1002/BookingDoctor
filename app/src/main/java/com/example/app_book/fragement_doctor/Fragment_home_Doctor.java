package com.example.app_book.fragement_doctor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_book.Adapter.Adapter_lichkhamDT;
import com.example.app_book.Adapter.Adpter_post;
import com.example.app_book.Adapter.SliderAdapter;
import com.example.app_book.Add_LichKham;
import com.example.app_book.Add_Post;
import com.example.app_book.Dashboard_Doctor;
import com.example.app_book.Model.Mode_Post;
import com.example.app_book.Model.Model_LichKhamUS;
import com.example.app_book.PharmacyActivity;
import com.example.app_book.R;
import com.example.app_book.SplashActivity;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Fragment_home_Doctor extends Fragment {
    RecyclerView LichkhamUS;
    Adpter_post Adapter_LichkhamOfUS;
    List<Mode_Post> Model_LichKhamUS;

    public Fragment_home_Doctor() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    SliderView sliderView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    LinearLayoutManager layoutManager;
        FirebaseAuth firebaseAuth;
        FirebaseUser user;
           String name, key;
    int[] images = {R.drawable.bn1,
            R.drawable.bn2,
            R.drawable.bn3,
            R.drawable.bn4};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_doctor, container, false);

        sliderView = view.findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        TextView kkk = view.findViewById(R.id.tvNmae);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference( "Doctor");
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



        CardView addkkk = view.findViewById(R.id.btnassss);
        addkkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.equals("true")){
                    Intent intent = new Intent(getContext(), Add_Post.class);
                    intent.putExtra("key","add");
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Tài khoản bạn chưa được phê duyệt", Toast.LENGTH_SHORT).show();
                }

            }
        });

        LichkhamUS = view.findViewById(R.id.rcvposst);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);



        Model_LichKhamUS = new ArrayList<>();
        Adapter_LichkhamOfUS = new Adpter_post(getContext(), Model_LichKhamUS);
        showposst();
        return view;
      }

    private void showposst() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post");
        Query rs = ref.orderByChild("iddoctor").equalTo(user.getUid());
        rs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model_LichKhamUS.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Mode_Post model_post = ds.getValue(Mode_Post.class);
                        Model_LichKhamUS.add(model_post);

                    LichkhamUS.setLayoutManager(layoutManager);
                    LichkhamUS.setHasFixedSize(true);

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