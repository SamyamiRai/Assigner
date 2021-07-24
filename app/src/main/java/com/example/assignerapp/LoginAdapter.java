package com.example.assignerapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class LoginAdapter extends FragmentStatePagerAdapter {
    private Context context;
    int totalTabs;


    public LoginAdapter(@NonNull @NotNull FragmentManager fm, Context context,int totalTabs) {
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }


    @NonNull
    @NotNull
    @Override


    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment=new LoginTabFragment();
                return loginTabFragment;
            case 1:
                SignupTabFragment signupTabFragment=new SignupTabFragment();
                return signupTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
