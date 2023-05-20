package com.example.bloodchaiyo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.bloodchaiyo.LearnFragmentFiles.ViewPagerAdapter;
import  com.example.bloodchaiyo.R;
import com.google.android.material.tabs.TabLayout;


/**
 *
 *
 */

public class LearnFragment extends Fragment {

    TabLayout.Tab what,why,types,facts;

    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.learn, container, false);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        what = tabLayout.newTab();
        why = tabLayout.newTab();
        types = tabLayout.newTab();
        facts = tabLayout.newTab();


        tabLayout.addTab(what, 0);
        tabLayout.addTab(why, 1);
        tabLayout.addTab(types, 2);
        tabLayout.addTab(facts , 3);


        what.setText("What is it?");
        why.setText("Why to donate?");
        types.setText("Donation Types");
        facts.setText("Important Facts");


        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getActivity() , R.color.colorAccent));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity() , R.color.colorAccent));
        what.select();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return root;
    }

}