package com.appregistrationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Spinner countriesSpinner;
    EditText phoneNoEditText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);

        countriesSpinner = findViewById(R.id.spinnerCountries);
        countriesSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        phoneNoEditText = findViewById(R.id.editTextPhoneNo);

        progressBar = findViewById(R.id.progressBar2);

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the inputted phone number
                //Join it with the country code
                //Pass it to the next activity


                String code = CountryData.countryAreaCodes[countriesSpinner.getSelectedItemPosition()];

                String number = phoneNoEditText.getText().toString().trim();

                if(number.isEmpty() || number.length() < 8){
                    phoneNoEditText.setError("Valid number is required");
                    phoneNoEditText.requestFocus();
                    return;
                }

                final String phoneNumber = "+" + code + number;

                //Make a check of the phone number exists in the auth or not
                //If not, don't let the user login and send a toast to him/her
                //Else, send him to the verify phone page

                FirebaseApp.initializeApp(LoginActivity.this);
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                userRef.orderByChild("phoneNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressBar.setVisibility(View.VISIBLE);
                        if(dataSnapshot.getValue() == null){
                            //it means user has not been registered
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(LoginActivity.this, "Phone number has not been registered...", Toast.LENGTH_SHORT).show();
                            Log.d("MyTag", dataSnapshot.toString());
                        }
                        else{
                            //User has registered --> Send him to the verify phone page
                            Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                            intent.putExtra("phoneNumber", phoneNumber);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

}
