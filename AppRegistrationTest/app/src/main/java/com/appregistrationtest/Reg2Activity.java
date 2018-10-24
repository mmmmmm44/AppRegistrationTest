package com.appregistrationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Reg2Activity extends AppCompatActivity {

    Spinner countriesSpinner;
    EditText phoneNoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);

        final User user = (User)getIntent().getSerializableExtra(User.USER);

        countriesSpinner = findViewById(R.id.spinnerCountries);
        countriesSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        phoneNoEditText = findViewById(R.id.editTextPhoneNo);

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the inputted phone number
                //Join it with the country code
                //Pass it to the next activity

                String code = CountryData.countryAreaCodes[countriesSpinner.getSelectedItemPosition()];

                String number = phoneNoEditText.getText().toString().trim();

                if(number.isEmpty() || number.length() < 8){
                    phoneNoEditText.setError("Valid number is required");;
                    phoneNoEditText.requestFocus();
                    return;
                }

                String phoneNumber = "+" + code + number;

                Intent intent = new Intent(Reg2Activity.this, Reg3Activity.class);
                intent.putExtra(User.USER, user);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);

            }
        });
    }
}
