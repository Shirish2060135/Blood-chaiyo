package com.example.bloodchaiyo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.bloodchaiyo.NeedBlood.GetBloodFragment;
import com.example.bloodchaiyo.NeedBlood.ProfileFragment;
import com.example.bloodchaiyo.QuizFragments.TakeQuizFragment;
import com.example.bloodchaiyo.R;


public class HomeFragment extends Fragment {

    View root;
    Toolbar toolbar;
    TextView joinText;
    CardView join, check, get;
    FragmentTransaction fragmentTransaction;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.home, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Blood Chiyoo");

        join = (CardView) root.findViewById(R.id.join);
        check = (CardView) root.findViewById(R.id.check);
        get = (CardView) root.findViewById(R.id.get);

        joinText = (TextView) root.findViewById(R.id.text);

        joinText.setText("Donor Profile");


        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(new GetBloodFragment(), "Search for donors");

            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentFragment(new TakeQuizFragment(), "Check Eligibility");
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(new ProfileFragment(), "Donor Profile");
            }
        });

        return root;
    }


    public void setCurrentFragment(Fragment newFragment, String title) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        toolbar.setTitle(title);

    }


}
