package com.example.app_book;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    EditText loginphone;
    EditText loginpass, loginpasscomfir;
    String sphone, sdt;
    ProgressDialog pd;
    String Phar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        Phar= ""+intent.getStringExtra("phar");
        loginphone = findViewById(R.id.login_phone);
        Button login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ktradangky(loginphone.getText().toString().trim());
                sinup();

            }
        });
        ImageButton back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void sinup(){
        pd.show();
        if (Phar.equals("1")){
            String skal = String.valueOf("+84"+loginphone.getText());
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(skal)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(SignUpActivity.this)                 // Activity (for callback binding)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential credential) {
                                    signInWithPhoneAuthCredential(credential);

                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    Toast.makeText(SignUpActivity.this, "Đăng ký thất bại.", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId,
                                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {

                                    Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                    intent.putExtra("phar","1" );
                                    intent.putExtra("sdt",loginphone.getText().toString());
                                    intent.putExtra("key","snup" );
//                                                intent.putExtra("mk",loginpass.getText().toString());
                                    intent.putExtra("verificationId",verificationId);
                                    startActivity(intent);
                                    finish();
                                }
                            }).build();
            PhoneAuthProvider.verifyPhoneNumber(options);

//                            }else if(loginpass.getText() != loginpasscomfir.getText()){
//                                Toast.makeText(SignUpActivity.this, "Xác nhận lại mật khẩu không chính xác.", Toast.LENGTH_SHORT).show();
//
//                            }

        }
        else if(Phar.equals("2")){
//                    if(loginpass.getText().equals(loginpasscomfir.getText())){
            String skal = String.valueOf("+84"+loginphone.getText());
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(skal)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(SignUpActivity.this)                 // Activity (for callback binding)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential credential) {
                                    signInWithPhoneAuthCredential1(credential);

                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    Toast.makeText(SignUpActivity.this, "Đăng ký thất bại.", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId,
                                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {

                                    Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                    intent.putExtra("phar","2" );
                                    intent.putExtra("sdt",loginphone.getText().toString());
                                    intent.putExtra("key","snup" );
//                                                intent.putExtra("mk",loginpass.getText().toString());
                                    intent.putExtra("verificationId",verificationId);
                                    startActivity(intent);
                                    finish();
                                    pd.dismiss();
                                }
                            }).build();
            PhoneAuthProvider.verifyPhoneNumber(options);
//
//                    }else {
//                        Toast.makeText(SignUpActivity.this, "Xác nhận lại mật khẩu không chính xác.", Toast.LENGTH_SHORT).show();
//                        pd.dismiss();
//                    }
        }
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(getApplicationContext(), Comfirm_In4_Doctor_Activity.class);
                            intent.putExtra("sdt",loginphone.getText().toString());
                            startActivity(intent);
                            finish();
                            pd.dismiss();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SignUpActivity.this, "Xác nhận lại mật khẩu không chính xác.", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }
                    }
                });
    }
    private void signInWithPhoneAuthCredential1(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(getApplicationContext(), Comfirm_In4_Patient_Activity.class);
                            intent.putExtra("sdt",loginphone.getText().toString());
                            startActivity(intent);
                            finish();
                            pd.dismiss();

                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SignUpActivity.this, "Xác nhận lại mật khẩu không chính xác.", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }
                    }
                });
    }
    public void onBackPressed() {
        super.onBackPressed();
    }

}
