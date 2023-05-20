package  com.example.bloodchaiyo.LearnFragmentFiles;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import  com.example.bloodchaiyo.Fragments.LearnFragment;


/**
 *
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);

    }


    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new WhatFragment();

        } else if (position == 1) {

            return new WhyFragment();


        } else if (position == 2) {

            return new TypesFragment();


        } else if (position == 3) {


            return new FactsFragment();

        } else {

            return new LearnFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

}