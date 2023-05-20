package com.example.bloodchaiyo.NeedBlood;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.bloodchaiyo.Main.ConnectUsers;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import com.example.bloodchaiyo.R;
import com.example.bloodchaiyo.Services.MessagingService;
import com.example.bloodchaiyo.Util.FCMHelper;

/**
 *
 * .
 */


public class GetBloodFragment extends Fragment {

    View root;
    Toolbar toolbar;
    Locale[] locale;
    Button search;
    String country, blood, bloodT, email, name, facebook, phone;
    SharedPreferences spf;
    ArrayList<String> countries;
    ArrayAdapter<String> adapter;
    SharedPreferences.Editor edit;
    Spinner countrySpinner, bloodSpinner;
    String[] BloodList = {"Select Blood Type", "-------------------", "O-", "O+", "A+", "A-", "B+", "B-", "AB+", "AB-"};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.get, container, false);

        countrySpinner = (Spinner) root.findViewById(R.id.spinner2);
        bloodSpinner = (Spinner) root.findViewById(R.id.spinner3);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        search = (Button) root.findViewById(R.id.search);


        setupCountriesList();
        setupSpinner();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save("searchBlood", blood);
                save("searchCountry", country);
                save("getAll", "false");

//                Log.e("1", blood);
          //      Log.e("1", country);

                load();

                if (bloodSpinner.getSelectedItemPosition() == 1 || bloodSpinner.getSelectedItemPosition() == 0) {


                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Missing Data")
                            .setConfirmText("OK")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();

                                }
                            }).show();


                } else {
                    new AsyncTask<Void, Void, Void>() {
                        JsonObject notificationObject;
                        JsonObject dataObject;

                        AlertDialog dialog = new SpotsDialog(getActivity(), "Sending Request..");

                        Boolean success;


                        protected void onPreExecute() {
                            Log.d("MSG", "GET BLOOD MESSAGING RUNNING");
                            notificationObject = new JsonObject();
                            notificationObject.addProperty("title", "Blood needed");
                            notificationObject.addProperty("body", "Someone is searching for donors in " + country);
                            notificationObject.addProperty("click_action", MessagingService.ACTION_CONNECT_USERS);

                            dataObject = new JsonObject();
                            dataObject.addProperty("blood", bloodT);
                            dataObject.addProperty("country", country);
                            dataObject.addProperty("email", email);
                            dataObject.addProperty("phone", phone);
                            dataObject.addProperty("facebook", facebook);
                            dataObject.addProperty("name", name);

                            dialog.show();
                        }

                        protected Void doInBackground(Void... unused) {

                            FCMHelper hepler = FCMHelper.getInstance();

                            try {


                                hepler.sendTopicNotificationAndData(blood, notificationObject, dataObject);

                                success = true;

                            } catch (IOException e) {
                                e.printStackTrace();

                                success = false;

                            }


                            return null;
                        }

                        protected void onPostExecute(Void unused) {
                            // Post Code
                            dialog.dismiss();

                            if (success) {

                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Request Sent")
                                        .setContentText("Your request was sent to all available donors, relax and wait for a reply")
                                        .setConfirmText("Cool!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {

                                                sDialog.dismissWithAnimation();

                                            }
                                        }).show();

                            } else {
                                dialog.dismiss();
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Request was not Sent")
                                        .setContentText("Your request was not sent, try again later..")
                                        .setConfirmText("Cool!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();

                                            }
                                        }).show();

                            }


                        }
                    }.execute();


                }


            }
        });


        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int position = countrySpinner.getSelectedItemPosition();

                if (!(position == 0) || !(position == 1)) {

                    country = countries.get(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }


        });


        bloodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int position = bloodSpinner.getSelectedItemPosition();

                if (!(position == 0) || !(position == 1)) {

                    bloodT = BloodList[position];

                    switch (BloodList[position]) {

                        case "O-":
                            blood = "Om";
                            break;

                        case "O+":
                            blood = "Op";
                            break;

                        case "A+":
                            blood = "Ap";
                            break;

                        case "A-":
                            blood = "Am";
                            break;

                        case "B+":
                            blood = "Bp";
                            break;

                        case "B-":
                            blood = "Bm";
                            break;

                        case "AB+":
                            blood = "ABp";
                            break;

                        case "AB-":
                            blood = "ABm";
                            break;

                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }


        });


        return root;
    }


    public void setupSpinner() {

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, countries);
        countrySpinner.setAdapter(adapter);


        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, BloodList);
        bloodSpinner.setAdapter(adapter);

    }


    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }


    public void load() {

        spf = PreferenceManager.getDefaultSharedPreferences(getActivity());

        email = spf.getString("email", "");
        phone = spf.getString("phone", "");
        facebook = spf.getString("facebook", "");
        name = spf.getString("name", "");

    }

    public void setupCountriesList() {

        locale = Locale.getAvailableLocales();
        countries = new ArrayList<>();
        countries.add("Select City");
        countries.add("Kathmandu");
        countries.add("Pokhara");
        countries.add("Patan");
        countries.add("Bhakatapur");
        countries.add("Biratnagar");
        countries.add("Dharan");
        countries.add("Birgunj");
        countries.add("Bharatpur");
        countries.add("Janakpur ");
        countries.add("Kritipur");
        countries.add("Tulsipur");
        countries.add("Tikapur");
        countries.add("Banepa");
        countries.add("Dhulikhel");
        countries.add("Nepalganj");
        countries.add("Ithari");

    }
}