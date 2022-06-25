package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_book.notification.Token;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.example.app_book.Adapter.Adapter_menubottom_User;

import com.example.app_book.fragement_User.HosoFragment;
import com.example.app_book.fragement_User.HomeFragment;
import com.example.app_book.fragement_User.NoticationsFragment;
import com.example.app_book.fragement_User.LichkhamFragment;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Dashboard_User extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    private NavigationView navigationView2;
    public static String mUID;
    public static String name;
    public static String NgayThangHienTai;
    private TextView nametvdb, emailtvdb;
    private ImageView avatardb, coverimgdb;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private DatabaseReference databaseReference;
    FirebaseUser user;
    String type, pharrr,sdt1;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            finish();
        }else{
            super.onBackPressed();
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        if (Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window  = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        pharrr = getIntent().getStringExtra("phar");
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView2 = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.collapsingtoolbarlayout);
        setSupportActionBar(toolbar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");


        ////////////// set date
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        NgayThangHienTai = dateButton.getText().toString();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Dashboard_User.this
        , mDrawerLayout, toolbar, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ImageButton seach = findViewById(R.id.searchicon);
        seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard_User.this, SearchActivity.class));
            }
        });
        ImageButton mesage = findViewById(R.id.mesicon);
        mesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_User.this, ShowUserChatActivity.class);
                intent.putExtra("uidchat",mUID);
                startActivity(intent);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        navigationView2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.pro1:
                      Intent intent = new Intent(Dashboard_User.this, My_Profile.class);
                       startActivity(intent);
                        mDrawerLayout.close();
                        break;

                    case  R.id.pro2:
                        Intent intent2 = new Intent(Dashboard_User.this, LichSu_User.class);
                        intent2.putExtra("Key","user" );
                        startActivity(intent2);
                        mDrawerLayout.close();

                    break;



                    case R.id.pro5:
                        Intent intent3 = new Intent(Dashboard_User.this, QuyDinh_App.class);
                        startActivity(intent3);
                        break;
                    case R.id.pro6:
                        Intent intent4 = new Intent(Dashboard_User.this, QuyTrinhDatLich.class);
                        startActivity(intent4);
                        break;
                    case R.id.pro8:
                        Intent intent5 = new Intent(Dashboard_User.this, Cau_Hoi_Thuong_Gap.class);
                        startActivity(intent5);
                        break;
                    case R.id.ab1:
//                        intent = new Intent(DashboardActivity.this, Setting_app.class);
//                        startActivity(intent);
                        mDrawerLayout.close();

                        break;
                    case R.id.ab2:

                        break;
                    case R.id.ab3:
                        firebaseAuth.signOut();
                        checkUserStatus();
                        break;

                }
                return false;
            }
        });
        View header= navigationView2.getHeaderView(0);

        /////navigation bottom

        BottomNavigationView navigationView = findViewById(R.id.nav_bottomus);
        ViewPager viewPager = findViewById(R.id.viewpagerbottom);
        Adapter_menubottom_User adapter_ab0 = new Adapter_menubottom_User(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter_ab0);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        HomeFragment homeFragment = (HomeFragment) viewPager.getAdapter().instantiateItem(viewPager,0);
                        homeFragment.reloadata();
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.nav_videos).setChecked(true);
                        LichkhamFragment profileFragment = (LichkhamFragment) viewPager.getAdapter().instantiateItem(viewPager,1);
                        profileFragment.reloadata();
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.nav_discovers).setChecked(true);
                        HosoFragment friendsFragment = (HosoFragment) viewPager.getAdapter().instantiateItem(viewPager,2);
                        friendsFragment.reloadata();
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.nav_notications).setChecked(true);
                        NoticationsFragment NoticationsFragment = (NoticationsFragment) viewPager.getAdapter().instantiateItem(viewPager,3);
                        NoticationsFragment.reloadata();
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        viewPager.setCurrentItem(0);
                        onRestart();
                        break;
                    case R.id.nav_videos:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_discovers:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.nav_notications:
                        viewPager.setCurrentItem(3);
                        break;

                }
                return true;
            }
        });





        coverimgdb = header.findViewById(R.id.cat_img);
        emailtvdb = header.findViewById(R.id.emailtvdb);
        nametvdb = header.findViewById(R.id.nametvdb);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final Query ref = databaseReference.orderByChild("uid").equalTo(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    name = ""+ ds.child("Name").getValue().toString();
                    type = ""+ ds.child("Type").getValue().toString();
                    sdt1 = ""+ ds.child("STD").getValue().toString();
                    String imagedb = ""+ ds.child("avatar").getValue().toString();
                    nametvdb.setText(name);
                    emailtvdb.setText(sdt1);
                    try {
                        Picasso.get().load(imagedb).into(coverimgdb);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(coverimgdb);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        checkUserStatus();

    }

    public void updateToken(String token){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        ref.child(mUID).setValue(mToken);
    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user != null){
            mUID= user.getUid();

            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", mUID);
            editor.putString("Current_Phar", pharrr);
            editor.apply();

            updateToken(FirebaseInstanceId.getInstance().getToken());
        }else{
            startActivity(new Intent(Dashboard_User.this, PharmacyActivity.class));
            finish();
        }

    }
    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();

    }

//    private void checkUser(){
//        final Query ref = databaseReference.orderByChild("uid").equalTo(mUID);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds: snapshot.getChildren()){
//                  String typeee = ""+ ds.child("Type").getValue().toString();
//                    if(typeee == "doctor") {
//                        startActivity(new Intent(Dashboard_User.this, Dashboard_Doctor.class));
//                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
private String getTodaysDate()
{
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    month = month + 1;
    int day = cal.get(Calendar.DAY_OF_MONTH);
    return makeDateString(day, month, year);
}

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year)
    {
        return "Ngày " + day + " - " + getMonthFormat(month) + " - " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Tháng 1";
        if(month == 2)
            return "Tháng 2";
        if(month == 3)
            return "Tháng 3";
        if(month == 4)
            return "Tháng 4";
        if(month == 5)
            return "Tháng 5";
        if(month == 6)
            return "Tháng 6";
        if(month == 7)
            return "Tháng 7";
        if(month == 8)
            return "Tháng 8";
        if(month == 9)
            return "Tháng 9";
        if(month == 10)
            return "Tháng 10";
        if(month == 11)
            return "Tháng 11";
        if(month == 12)
            return "Tháng 12";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    @Override
    protected void onResume() {
        checkUserStatus();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}

