package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import org.jetbrains.annotations.NotNull;

public class IntroductoryActivity extends AppCompatActivity {

    LottieAnimationView loading;
    ImageView background,logo,app_name;
    Animation anim;
    private static final int NUM_PAGES=3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        background=findViewById(R.id.image);
//        loading=findViewById(R.id.loading);
        logo=findViewById(R.id.logo);
//        app_name=findViewById(R.id.app_name);

        viewPager=findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        anim= AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.setAdapter(pagerAdapter);


        background.animate().translationY(-2000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
//        app_name.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
//        loading.animate().translationY(1600).setDuration(1000).setStartDelay(4000);

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePagerAdapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    OnBoardingFragment1 tab1= new OnBoardingFragment1();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2= new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3= new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}