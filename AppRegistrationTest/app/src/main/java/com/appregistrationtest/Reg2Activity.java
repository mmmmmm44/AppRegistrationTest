package com.appregistrationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Reg2Activity extends AppCompatActivity {

    Spinner countriesSpinner;
    EditText phoneNoEditText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);

        final User user = (User)getIntent().getSerializableExtra(User.USER);

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

                //Make a check of existing phone number before moving on to the verify code section
                //No duplicate phone number will exist

                String code = CountryData.countryAreaCodes[countriesSpinner.getSelectedItemPosition()];

                String number = phoneNoEditText.getText().toString().trim();

                if(number.isEmpty() || number.length() < 8){
                    phoneNoEditText.setError("Valid number is required");;
                    phoneNoEditText.requestFocus();
                    return;
                }

                final String phoneNumber = "+" + code + number;

                progressBar.setVisibility(View.VISIBLE);

                FirebaseApp.initializeApp(Reg2Activity.this);
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                userRef.orderByChild("phoneNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        if(dataSnapshot.getValue() != null){
                            //it means user already registered
                            Toast.makeText(Reg2Activity.this, "Phone number has already registered...", Toast.LENGTH_SHORT).show();
                            Log.d("MyTag", dataSnapshot.toString());
                            return;
                        }
                        else{
                            //Go to the next registration activity
                            //To fill in the user basic information

                            Intent intent = new Intent(Reg2Activity.this, Reg3Activity.class);
                            intent.putExtra(User.USER, user);
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
