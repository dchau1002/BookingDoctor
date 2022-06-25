package com.example.app_book.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.app_book.fragement_Admin.Fragment_KiemD_Admin;
import com.example.app_book.fragement_Admin.Fragment_MN_DT;
import com.example.app_book.fragement_Admin.Fragment_MN_User;
import com.example.app_book.fragement_Admin.Fragment_home_Admin;
import com.example.app_book.fragement_Admin.Fragment_notification;
import com.example.app_book.fragement_doctor.Fragment_Lichkham_DT;
import com.example.app_book.fragement_doctor.Fragment_MN_lichkham_DT;
import com.example.app_book.fragement_doctor.Fragment_home_Doctor;
import com.example.app_book.fragement_doctor.Fragment_hoso_Doctor;
import com.example.app_book.fragement_doctor.Fragment_notification_Doctor;

public class Adapter_menubottom_Admin extends FragmentStatePagerAdapter {


    public Adapter_menubottom_Admin(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_home_Admin();
            case 1:
                return new Fragment_KiemD_Admin();
            case 2:
                return new Fragment_MN_DT();
            case 3:
                return new Fragment_MN_User();
            case 4:
                return new Fragment_notification();
            default:
                return new Fragment_home_Admin();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
