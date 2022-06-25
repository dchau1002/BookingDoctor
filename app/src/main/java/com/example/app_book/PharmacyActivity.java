package com.example.app_book;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PharmacyActivity extends AppCompatActivity {
    Button mLoginbtn, mregisterbtn;
    TextView btn_admin;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    public void onBackPressed() {
            super.onBackPressed();
            finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.signOut();

        mLoginbtn = findViewById(R.id.doctor_btn);
        mregisterbtn = findViewById(R.id.Patient_btn);
        btn_admin = findViewById(R.id.admin_btn);




        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Email.class);
                intent.putExtra("phar","1" );
                startActivity(intent);

            }
        });

        mregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Email.class);
                intent.putExtra("phar","2" );
                startActivity(intent);

            }
        });
        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Email.class);
                intent.putExtra("phar","3" );
                startActivity(intent);

            }
        });
    }

}