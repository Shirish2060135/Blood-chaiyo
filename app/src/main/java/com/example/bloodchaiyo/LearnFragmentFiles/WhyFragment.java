package com.example.bloodchaiyo.LearnFragmentFiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.bloodchaiyo.R;


/**
 *
 *
 */
public class WhyFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.why_fragment, container, false);


        return root;

    }

}
