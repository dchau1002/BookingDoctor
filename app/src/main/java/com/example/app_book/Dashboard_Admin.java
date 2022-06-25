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

import com.example.app_book.Adapter.Adapter_menubottom_Admin;
import com.example.app_book.Adapter.Adapter_menubottom_Doctor;
import com.example.app_book.fragement_Admin.Fragment_KiemD_Admin;
import com.example.app_book.fragement_Admin.Fragment_MN_DT;
import com.example.app_book.fragement_Admin.Fragment_MN_User;
import com.example.app_book.fragement_Admin.Fragment_home_Admin;
import com.example.app_book.fragement_Admin.Fragment_notification;
import com.example.app_book.fragement_doctor.Fragment_Lichkham_DT;
import com.example.app_book.fragement_doctor.Fragment_home_Doctor;
import com.example.app_book.fragement_doctor.Fragment_hoso_Doctor;
import com.example.app_book.fragement_doctor.Fragment_MN_lichkham_DT;
import com.example.app_book.fragement_doctor.Fragment_notification_Doctor;
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

import com.squareup.picasso.Picasso;
import com.example.app_book.notification.Token;

import java.util.Calendar;

public class Dashboard_Admin extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    private NavigationView navigationView2;
    public static String mUID;
    public static String Namedt;
    public static String NgayThangHienTai;
    private TextView nametvdb, emailtvdb;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private ImageView avatardb, coverimgdb;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String name, type ,pharrr;

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
    protected void onResume() {
        checkUserStatus();
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard__admin);

        if (Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window  = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        pharrr = getIntent().getStringExtra("phar");
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView2 = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.collapsingtoolbarlayout1);
        setSupportActionBar(toolbar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctor");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Dashboard_Admin.this
                , mDrawerLayout, toolbar, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        ImageButton mesage = findViewById(R.id.mesicon);
        mesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_Admin.this, ShowUserChatActivity.class);
                intent.putExtra("uidchat",mUID );
                startActivity(intent);
            }
        });



        ////////////// set date
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        NgayThangHienTai = dateButton.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        navigationView2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.pro1:
//                        Intent intent1 = new Intent(Dashboard_Admin.this, Profile_Doctor.class);
//                        intent1.putExtra("Key","doctor" );
//                        startActivity(intent1);
                        mDrawerLayout.close();
                        break;

                    case  R.id.pro2:
//                        Intent intent = new Intent(Dashboard_Admin.this, LichSu_User.class);
//                        intent.putExtra("Key","doctor" );
//                        startActivity(intent);
                        mDrawerLayout.close();

                        break;



                    case R.id.pro5:

                        break;
                    case R.id.pro6:

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

        BottomNavigationView navigationView = findViewById(R.id.nav_bottom_ad);
        ViewPager viewPager = findViewById(R.id.viewpagerbottomad);
        Adapter_menubottom_Admin adapter_ab0 = new Adapter_menubottom_Admin(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter_ab0);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        Fragment_home_Admin homeFragment = (Fragment_home_Admin) viewPager.getAdapter().instantiateItem(viewPager,0);

                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.nav_lichkham).setChecked(true);
                        Fragment_KiemD_Admin profileFragment = (Fragment_KiemD_Admin) viewPager.getAdapter().instantiateItem(viewPager,1);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.nav_videos).setChecked(true);
                        Fragment_MN_DT fragment_mn_lichkham_dt = (Fragment_MN_DT) viewPager.getAdapter().instantiateItem(viewPager,2);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.nav_discovers).setChecked(true);
                        Fragment_MN_User friendsFragment = (Fragment_MN_User) viewPager.getAdapter().instantiateItem(viewPager,3);
                        break;
                    case 4:
                        navigationView.getMenu().findItem(R.id.nav_notications).setChecked(true);
                        Fragment_notification NoticationsFragment = (Fragment_notification) viewPager.getAdapter().instantiateItem(viewPager,4);
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
                    case R.id.nav_lichkham:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_videos:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.nav_discovers:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.nav_notications:
                        viewPager.setCurrentItem(4);
                        break;


                }
                return true;
            }
        });
        coverimgdb = header.findViewById(R.id.cat_img);
        emailtvdb = header.findViewById(R.id.emailtvdb);
        nametvdb = header.findViewById(R.id.nametvdb);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String mUIDDD = user.getUid();
        final Query ref = databaseReference.orderByChild("uid").equalTo(mUIDDD);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Namedt = ""+ ds.child("Name").getValue().toString();
                    type = ""+ ds.child("Type").getValue().toString();
                    String sdt1 = ""+ ds.child("STD").getValue().toString();
                    String imagedb = ""+ ds.child("avatar").getValue().toString();
                    nametvdb.setText(Namedt);
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
            startActivity(new Intent(Dashboard_Admin.this, PharmacyActivity.class));
            finish();
        }

    }
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
    protected void onStart() {
        checkUserStatus();
        super.onStart();

    }
    @Override
    protected void onStop() {
        super.onStop();

    }

}
