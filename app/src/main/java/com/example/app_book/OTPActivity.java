package com.example.app_book;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    String sdt,mk,key, verificationId;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        setContentView(R.layout.activity_otpactivity);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String Phar = ""+intent.getStringExtra("phar");
        key = getIntent().getStringExtra("key");
        sdt = getIntent().getStringExtra("sdt");
        verificationId = getIntent().getStringExtra("verificationId");
        Button login = findViewById(R.id.btn_login);
        EditText otpcode = findViewById(R.id.edt_OTP);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                if (Phar.equals("1")){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpcode.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);
                }
                else if(Phar.equals("2")){
                    PhoneAuthCredential credential2 = PhoneAuthProvider.getCredential(verificationId, otpcode.getText().toString());
                    signInWithPhoneAuthCredential1(credential2);
                }
            }
        });
        ImageButton back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView recore = findViewById(R.id.forgotss);
        recore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                if (Phar.equals("1")){
                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth)
                                        .setPhoneNumber("+84"+sdt)       // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(OTPActivity.this)
                                        .setForceResendingToken(forceResendingToken)// Activity (for callback binding)
                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                            @Override
                                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                                signInWithPhoneAuthCredential(credential);

                                            }

                                            @Override
                                            public void onVerificationFailed(FirebaseException e) {
                                                Toast.makeText(OTPActivity.this, "Thất bại.", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String verificationIdi,
                                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                                                verificationId = verificationIdi;
                                                forceResendingToken = token;
                                                pd.dismiss();
                                            }
                                        }).build();
                        PhoneAuthProvider.verifyPhoneNumber(options);


                }
                else if(Phar.equals("2")){
                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth)
                                        .setPhoneNumber("+84"+sdt)       // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(OTPActivity.this)
                                        .setForceResendingToken(forceResendingToken)
                                        // Activity (for callback binding)
                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                            @Override
                                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                                signInWithPhoneAuthCredential1(credential);

                                            }

                                            @Override
                                            public void onVerificationFailed(FirebaseException e) {
                                                Toast.makeText(OTPActivity.this, "Thất bại.", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String verificationIdi,
                                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                                verificationId = verificationIdi;
                                                forceResendingToken = token;
                                                pd.dismiss();
                                            }
                                        }).build();
                        PhoneAuthProvider.verifyPhoneNumber(options);

                }
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(key.equals("snup")){
                                Intent intent = new Intent(getApplicationContext(), Comfirm_In4_Doctor_Activity.class);
                                intent.putExtra("phar","1" );
                                intent.putExtra("sdt",sdt);
                                startActivity(intent);
                                finish();
                                pd.dismiss();
                            }else if(key.equals("sgin")){
                                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                                intent.putExtra("phar","1" );
                                intent.putExtra("sdt",sdt);
                                startActivity(intent);
                                finish();
                                pd.dismiss();
                            }

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPActivity.this, "Mã OTP không chính xác.", Toast.LENGTH_SHORT).show();
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
                            if(key.equals("snup")){
                                Intent intent = new Intent(getApplicationContext(), Comfirm_In4_Patient_Activity.class);
                                intent.putExtra("phar","2" );
                                intent.putExtra("sdt",sdt);
                                startActivity(intent);
                                finish();
                                pd.dismiss();
                            }else if(key.equals("sgin")){
                                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                                intent.putExtra("phar","2" );
                                intent.putExtra("sdt",sdt);
                                startActivity(intent);
                                finish();
                                pd.dismiss();
                            }


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPActivity.this, "Mã OTP không chính xác.", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }
                    }
                });
    }


    public void onBackPressed()
    {
        mAuth.signOut();
        super.onBackPressed();
    }
}