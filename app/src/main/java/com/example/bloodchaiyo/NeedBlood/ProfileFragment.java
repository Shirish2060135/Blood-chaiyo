package com.example.bloodchaiyo.NeedBlood;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import com.example.bloodchaiyo.Main.MainActivity;
import com.example.bloodchaiyo.R;
import com.example.bloodchaiyo.activities.PhoneAuthActivity;

/**
 * .
 *
 * .
 * .
 */


public class ProfileFragment extends Fragment {

    FirebaseAuth mFirebaseAuth;
    Button logout;
    TextView name, email, phone, blood, facebook, country;



    String mUserId;
    FirebaseUser mFirebaseUser;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.profile, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Donor Profile");


        name = (TextView) root.findViewById(R.id.name);
        email = (TextView) root.findViewById(R.id.email);
        country = (TextView) root.findViewById(R.id.country);
        phone = (TextView) root.findViewById(R.id.phone);
        facebook = (TextView) root.findViewById(R.id.facebook);
        blood = (TextView) root.findViewById(R.id.blood);

        logout = (Button) root.findViewById(R.id.logout);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        final AlertDialog dialog = new SpotsDialog(getActivity(), "Loading..");

        dialog.show();
        mDatabase.child("users").child(mUserId).child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name.setText("" + dataSnapshot.child("name").getValue());
                email.setText("Email : " + dataSnapshot.child("email").getValue());
                facebook.setText("Facebook : " + dataSnapshot.child("facebook").getValue());
                country.setText("City : " + dataSnapshot.child("city").getValue());
                phone.setText("Phone : " + dataSnapshot.child("phone").getValue());
                blood.setText("Blood Type : " + dataSnapshot.child("blood").getValue());

                save("email" , ""+dataSnapshot.child("email").getValue());
                save("phone" , ""+dataSnapshot.child("phone").getValue());
                save("facebook" , ""+dataSnapshot.child("facebook").getValue());
                save("name" , ""+dataSnapshot.child("name").getValue());

                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout ?")
                        .setCancelText("No")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                mFirebaseAuth.signOut();
                                startActivity(new Intent(getActivity(), PhoneAuthActivity.class));
                                getActivity().finish();
                            }
                        }).show();

            }
        });



        return root;

    }

    public void save(String key, String value) {

        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }

}
