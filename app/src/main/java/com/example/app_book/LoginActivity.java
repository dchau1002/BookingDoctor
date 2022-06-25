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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    ProgressDialog pd;
    String sdt;
    String loainguoidung = null;
    EditText loginphone;
    String Phar;
    private FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.signOut();
        Intent intent = getIntent();
        Phar = ""+intent.getStringExtra("phar");
         if (Phar.equals("3")){
             TextView kk = findViewById(R.id.sigNup);
             kk.setVisibility(View.GONE);
         }
        loginphone = findViewById(R.id.login_email);
        mAuth = FirebaseAuth.getInstance();


        pd = new ProgressDialog(this);
        Button login = findViewById(R.id.btn_login);

        TextView sigNup = findViewById(R.id.sigNup);
        sigNup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Phar.equals("1")){
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    intent.putExtra("phar","1" );
                    startActivity(intent);

                }else if (Phar.equals("2")) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    intent.putExtra("phar","2" );
                    startActivity(intent);

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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Phar.equals("1")){
                    String skal = String.valueOf("+84"+loginphone.getText());
                    pd.show();
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(skal)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(LoginActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                        @Override
                                        public void onVerificationCompleted(PhoneAuthCredential credential) {
                                            signInWithPhoneAuthCredential(credential);

                                        }

                                        @Override
                                        public void onVerificationFailed(FirebaseException e) {
                                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
                                            pd.dismiss();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verificationId,
                                                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

                                            Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                            intent.putExtra("phar","1" );
                                            intent.putExtra("sdt",skal);
                                            intent.putExtra("key","sgin" );
                                            intent.putExtra("verificationId",verificationId);
                                            startActivity(intent);
                                        }
                                    }).build();
                                     PhoneAuthProvider.verifyPhoneNumber(options);

                }
                else if(Phar.equals("2")){
                    pd.show();
                        String skal = String.valueOf("+84" + loginphone.getText());
                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth)
                                        .setPhoneNumber(skal)       // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(LoginActivity.this)                 // Activity (for callback binding)
                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                            @Override
                                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                                signInWithPhoneAuthCredential1(credential);

                                            }

                                            @Override
                                            public void onVerificationFailed(FirebaseException e) {
                                                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String verificationId,
                                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                                                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                                intent.putExtra("phar", "2");
                                                intent.putExtra("sdt", skal);
                                                intent.putExtra("key", "sgin");
                                                intent.putExtra("verificationId", verificationId);
                                                startActivity(intent);
                                                pd.dismiss();
                                            }
                                        }).build();
                        PhoneAuthProvider.verifyPhoneNumber(options);

                }
                else if(Phar.equals("3")){
                    Intent intent = new Intent(getApplicationContext(), Dashboard_User.class);
                    intent.putExtra("phar","3" );
                    startActivity(intent);
                    finish();
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
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(getApplicationContext(), Dashboard_Doctor.class);
                            intent.putExtra("sdt",user.getPhoneNumber());
                            startActivity(intent);
                            finish();
                            pd.dismiss();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginActivity.this, "Xác nhận lại mật khẩu không chính xác.", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(getApplicationContext(), Dashboard_User.class);
                            intent.putExtra("sdt",user.getPhoneNumber() );
                            startActivity(intent);
                            finish();
                            pd.dismiss();

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginActivity.this, "Xác nhận lại mật khẩu không chính xác.", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onRestart() {
        pd.dismiss();
        super.onRestart();
    }

    @Override
    protected void onStart() {
        pd.dismiss();
        super.onStart();
    }

    @Override
    protected void onPause() {
        pd.dismiss();
        super.onPause();
    }
}