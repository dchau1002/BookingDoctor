package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Timer;
import java.util.TimerTask;

public class Splash0Activity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ImageView image_top, image, image_beat, image_bottom;
    TextView TV, tvload;
    ProgressBar progressbar;
    Activity activity = Splash0Activity.this;
    CharSequence charSequence;
    int index;
    String loainguoidung = null, key, sdt, type;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    long delay = 20;
    Integer progress = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash0);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");



        image_top = findViewById(R.id.image_top);
        image = findViewById(R.id.image);
        image_beat = findViewById(R.id.image_beat);
        image_bottom = findViewById(R.id.image_bottom);
        TV = findViewById(R.id.TV);

        progressbar = findViewById(R.id.progressbar);
        tvload = findViewById(R.id.tvload);


        progressbar.setProgress(progress);
        progressbar.setMax(100);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                progress = progress + 2;
                progressbar.setProgress(progress);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvload.setText(String.valueOf(progress) + "%");
                    }
                });
                if (progressbar.getProgress() >= 100){
                    timer.cancel();
                }

            }
        }, 2500, 50);


        // set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // tôp animation
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.top_wave);
        //start top animation
        image_top.setAnimation(animation1);

        //object animtor
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                image,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        );

        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

        animatText("Loading...");

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/demoapp-ae96a.appspot.com/o/heart_beat.gif?alt=media&token=b21dddd8-782c-457c-babd-f2e922ba172b")
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image_beat);

        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.bottom_wave);
        image_bottom.setAnimation(animation2);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                firebaseAuth = FirebaseAuth.getInstance();
               FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    String user2 = firebaseAuth.getUid();
                    final Query ref = databaseReference.orderByChild("uid").equalTo(user2);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                               String typeoo = ""+ ds.child("Type").getValue().toString();
                                if(typeoo.equals("user")){

                                    Intent intent = new Intent(Splash0Activity.this, Dashboard_User.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else if(typeoo.equals("doctor")){
                                    Intent intent = new Intent(Splash0Activity.this, Dashboard_Doctor.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else if(typeoo.equals("admin")){
                                    Intent intent = new Intent(Splash0Activity.this, Dashboard_Admin.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else{
                    Intent intent = new Intent(getApplicationContext(), PharmacyActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        }, 5000);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            TV.setText(charSequence.subSequence( 0, index++));

            if (index <= charSequence.length()){

                handler.postDelayed(runnable, delay);

            }
        }
    };

    public void animatText(CharSequence cs) {

        charSequence = cs;
        index = 0;
        TV.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delay);
    }



}
