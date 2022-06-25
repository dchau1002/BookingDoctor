package com.example.app_book.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.app_book.fragement_doctor.Fragment_Lichkham_DT;
import com.example.app_book.fragement_doctor.Fragment_home_Doctor;
import com.example.app_book.fragement_doctor.Fragment_hoso_Doctor;
import com.example.app_book.fragement_doctor.Fragment_MN_lichkham_DT;
import com.example.app_book.fragement_doctor.Fragment_notification_Doctor;


public class Adapter_menubottom_Doctor extends FragmentStatePagerAdapter {


    public Adapter_menubottom_Doctor(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_home_Doctor();
            case 1:
                return new Fragment_Lichkham_DT();
            case 2:
                return new Fragment_MN_lichkham_DT();
            case 3:
                return new Fragment_hoso_Doctor();
            case 4:
                return new Fragment_notification_Doctor();
            default:
                return new Fragment_home_Doctor();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
