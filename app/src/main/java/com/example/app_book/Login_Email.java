package com.example.app_book;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login_Email extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mgooglesigninClient;
    TextView login_email, login_pass, forgot;
    Button login_btn;
    String Phar;
    SignInButton msiginGG;
    private FirebaseAuth mAuth;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mgooglesigninClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        Phar = getIntent().getStringExtra("phar");

        ImageButton back = findViewById(R.id.backc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView sn2 = findViewById(R.id.gfhh);
        sn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("phar",Phar);
                startActivity(intent);
            }
        });
        TextView sn3 = findViewById(R.id.sigNup);
        sn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUP_Email.class);
                intent.putExtra("phar",Phar);
                startActivity(intent);
            }
        });
                //Init
        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.btn_login);
        forgot = findViewById(R.id.forgot);
        if (Phar.equals("3")){
            sn3.setVisibility(View.GONE);
            sn2.setVisibility(View.GONE);
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                String pass = login_pass.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    login_email.setError("Invaild Email");
                    login_email.setFocusable(true);
                }else{
                    loginUser(email, pass);
            }
          }
        });
       forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCoverPasswordDialog();
            }

       });

       pd = new ProgressDialog(this);
    }

    private void showCoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);
        final EditText email = new EditText(this);
        email.setHint("Email");
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(email);
        email.setMinEms(16);
        linearLayout.setPadding(10, 10, 10, 10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailET = email.getText().toString().trim();
                beginrecovery(emailET);
            }


        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
     builder.create().show();
    }
    private void beginrecovery(String emailET) {

        pd.setMessage("Sending Email...");
        pd.show();
        mAuth.sendPasswordResetEmail(emailET).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(Login_Email.this, "Email Sent", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login_Email.this, "Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
              Toast.makeText(Login_Email.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loginUser(String email, String pass) {
        pd.setMessage("Loging In...");
        pd.show();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(Phar.equals("1")){
                                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                                intent.putExtra("phar","1" );
                                startActivity(intent);
                                finish();
                            }else if(Phar.equals("2")){
                                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                                intent.putExtra("phar","2" );
                                startActivity(intent);
                                finish();
                            } else if(Phar.equals("3")){
                                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                                    intent.putExtra("phar","3" );
                                    startActivity(intent);
                                    finish();
                                }

                        } else {
                            Toast.makeText(Login_Email.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            // ...
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             pd.dismiss();
             Toast.makeText(Login_Email.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    ///SingIn Google
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void firebaseAuthWithGoogle(String idToken) {
//        pd.dismiss();
//        pd.setMessage("Loging with Gogle...");
//        pd.show();
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's informatio
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//
//                            if (task.getResult().getAdditionalUserInfo().isNewUser()){
//
//                                String email = user.getEmail();
//                                String uid = user.getUid();
//
//                                HashMap<Object, String> hashMap = new HashMap<>();
//                                hashMap.put("email", email);
//                                hashMap.put("uid", uid);
//                                hashMap.put("name", "Chưa cập nhật");
//                                hashMap.put("OnlineStatus", "Online");
//                                hashMap.put("TyingTo", "noOne");
//                                hashMap.put("phone", "");
//                                hashMap.put("image", "");
//                                hashMap.put("cover", "");
//                                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                DatabaseReference reference = database.getReference("Users");
//                                reference.child(uid).setValue(hashMap);
//
//                            }
//
//                                Toast.makeText(Login_Email.this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(Login_Email.this, .class));
//                                finish();
//
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(Login_Email.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Login_Email.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}
