package com.example.app_book.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.app_book.fragement_User.HosoFragment;
import com.example.app_book.fragement_User.HomeFragment;
import com.example.app_book.fragement_User.NoticationsFragment;
import com.example.app_book.fragement_User.LichkhamFragment;

public class Adapter_menubottom_User extends FragmentStatePagerAdapter {


    public Adapter_menubottom_User(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new LichkhamFragment();
            case 2:
                return new HosoFragment();
            case 3:
                return new NoticationsFragment();
            default:
                return new HomeFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}