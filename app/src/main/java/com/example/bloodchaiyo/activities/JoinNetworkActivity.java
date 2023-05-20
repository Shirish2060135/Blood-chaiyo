package com.example.bloodchaiyo.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Locale;

import com.example.bloodchaiyo.Main.MainActivity;
import com.example.bloodchaiyo.R;

public class JoinNetworkActivity extends AppCompatActivity {
    private static final String TAG = "JoinNetworkActivity";
    private String userId,phoneNumber;
    public Context context;
    SharedPreferences spf;
    SharedPreferences.Editor edit;

    private Button register;
    private EditText name;
    private EditText email;
    private EditText facebook;
    private EditText blood;
    private Spinner spinner;
    private String nameStr,emailStr,facebookStr,bloodStr,country;
    private Locale[] locale;
    private ArrayList<String> countries;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_network);

        if (getIntent()!=null){
            if (getIntent().getStringExtra("user_id")!=null){
                userId = getIntent().getStringExtra("user_id");
                phoneNumber = getIntent().getStringExtra("phone");
                Log.d(TAG,"phoneNumber: "+phoneNumber);
            }
        }

        context = JoinNetworkActivity.this;
        register = (Button) findViewById(R.id.reg);
        name = (EditText) findViewById(R.id.editText);
        email = (EditText) findViewById(R.id.editText2);
        facebook = (EditText) findViewById(R.id.editText5);
        blood = (EditText) findViewById(R.id.editText4);
        spinner = (Spinner) findViewById(R.id.spinner);

        setupSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int position = spinner.getSelectedItemPosition();

                if (!(position == 0) || !(position == 1)) {

                    country = countries.get(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }


        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameStr = name.getText().toString();
                emailStr = email.getText().toString();
                bloodStr = blood.getText().toString();
                facebookStr = facebook.getText().toString();


                if (nameStr.isEmpty() || emailStr.isEmpty() ||
                        bloodStr.isEmpty() || facebookStr.isEmpty()) {

                    Toast.makeText(JoinNetworkActivity.this, "Fill all data please..", Toast.LENGTH_LONG).show();


                } else {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference database = db.getReference();
                    Log.d(TAG, "onClick: ON CLICK>>>>>>");
                    database.child("users").child(userId).child("data").child("email").setValue(emailStr);
                    database.child("users").child(userId).child("data").child("name").setValue(nameStr);
                    database.child("users").child(userId).child("data").child("phone").setValue(phoneNumber);
                    database.child("users").child(userId).child("data").child("facebook").setValue(facebookStr);
                    database.child("users").child(userId).child("data").child("blood").setValue(bloodStr);
                    database.child("users").child(userId).child("data").child("city").setValue(country);

                    save("email" , emailStr);
                    save("phone", phoneNumber);
                    save("facebook", facebookStr);
                    save("name", nameStr);

                    FirebaseMessaging.getInstance().subscribeToTopic("Ap");
                    FirebaseMessaging.getInstance().subscribeToTopic("Am");
                    FirebaseMessaging.getInstance().subscribeToTopic("Bp");
                    FirebaseMessaging.getInstance().subscribeToTopic("Bm");
                    FirebaseMessaging.getInstance().subscribeToTopic("Op");
                    FirebaseMessaging.getInstance().subscribeToTopic("Om");
                    FirebaseMessaging.getInstance().subscribeToTopic("ABp");
                    FirebaseMessaging.getInstance().subscribeToTopic("ABm");


                    startActivity(new Intent(JoinNetworkActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupSpinner() {
        setupCountriesList();

        adapter = new ArrayAdapter<>(JoinNetworkActivity.this, android.R.layout.simple_spinner_item, countries);
        spinner.setAdapter(adapter);

    }

    private void setupCountriesList() {
        locale = Locale.getAvailableLocales();
        countries = new ArrayList<String>();

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

    public void save(String key, String value) {
        spf = PreferenceManager.getDefaultSharedPreferences(context);
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();
    }
}
