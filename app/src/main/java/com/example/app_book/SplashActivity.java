package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
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

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ImageView image_top, image, image_beat, image_bottom;
    TextView TV, tvload;
    ProgressBar progressbar;
    Activity activity = SplashActivity.this;
    CharSequence charSequence;
    int index;
    String loainguoidung = null, key, sdt;

    long delay = 20;
    Integer progress = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        sdt = getIntent().getStringExtra("sdt");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loainguoidung = snapshot.child("Type").getValue().toString();
                }else{

                    Intent intent = new Intent(getApplicationContext(), PharmacyActivity.class);
                    startActivity(intent);
                    finish();
                    firebaseAuth.signOut();
                    Toast.makeText(SplashActivity.this, "SĐT chưa được đăng ký.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        key = getIntent().getStringExtra("phar");

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
//                Toast.makeText(SplashActivity.this, "dsff"+user.getUid()+"---"+loainguoidung, Toast.LENGTH_SHORT).show();

                if(key.equals("1") && loainguoidung.equals("user")){
                    Intent intent = new Intent(SplashActivity.this, PharmacyActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phar","1" );
                    startActivity(intent);
                    Toast.makeText(SplashActivity.this, "Đây không phải tài khoản của bác sĩ, vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(key.equals("1") && loainguoidung.equals("doctor")){
                    Intent intent = new Intent(SplashActivity.this, Dashboard_Doctor.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phar","1" );
                    startActivity(intent);
                    finish();
                }else if(key.equals("2") && loainguoidung.equals("user")){
                    Intent intent = new Intent(SplashActivity.this, Dashboard_User.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phar","2" );
                    startActivity(intent);
                    finish();
                }else if(key.equals("2") && loainguoidung.equals("doctor")){
                    Intent intent = new Intent(SplashActivity.this, PharmacyActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phar","2" );
                    startActivity(intent);
                    Toast.makeText(SplashActivity.this, "Đây không phải tài khoản của bệnh nhân, vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
                    finish();
                }else if(key.equals("3") && loainguoidung.equals("admin")){
                    Intent intent = new Intent(SplashActivity.this, Dashboard_Admin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phar","3" );
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this, PharmacyActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phar","3" );
                    startActivity(intent);
                    Toast.makeText(SplashActivity.this, "Đây không phải tài khoản của admin, vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onStart() {
        super.onStart();
    }
}
