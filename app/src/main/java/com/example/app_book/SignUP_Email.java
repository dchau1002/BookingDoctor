package com.example.app_book;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUP_Email extends AppCompatActivity {

      Button mregisterbtn;
      EditText tvemail, tvpass, tvname;
      String Phar;
      ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mregisterbtn = findViewById(R.id.btn_register);
        tvname = findViewById(R.id.register_name);
        tvemail = findViewById(R.id.register_email);
        tvpass = findViewById(R.id.register_password);

        mAuth = FirebaseAuth.getInstance();
        Phar = getIntent().getStringExtra("phar");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        mregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = tvname.getText().toString().trim();
                String email = tvemail.getText().toString().trim();
                String pass = tvpass.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    tvemail.setError("Invalid Email");
                    tvemail.setFocusable(true);
                }
                else if(pass.length()<6){
                    tvpass.setError("Password lenght at least 6 chara");
                    tvpass.setFocusable(true);

                }else{
                    registerUser(name1, email, pass);
                }
            }


        });
    }
    private void registerUser(String name1, String email, String pass ) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(Phar.equals("1")){
                                Intent intent = new Intent(getApplicationContext(), Comfirm_In4_Doctor_Activity.class);
                                intent.putExtra("sdt",tvname.getText().toString().trim());
                                intent.putExtra("email",tvemail.getText().toString().trim());
                                startActivity(intent);
                            }else if(Phar.equals("2")){
                                Intent intent = new Intent(getApplicationContext(), Comfirm_In4_Patient_Activity.class);
                                intent.putExtra("sdt",tvname.getText().toString().trim());
                                intent.putExtra("email",tvemail.getText().toString().trim());
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUP_Email.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignUP_Email.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}