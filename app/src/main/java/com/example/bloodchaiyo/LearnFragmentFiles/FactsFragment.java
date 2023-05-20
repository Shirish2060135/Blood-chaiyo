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
public class FactsFragment extends Fragment {

    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.facts, container, false);

        return root;

    }

}
