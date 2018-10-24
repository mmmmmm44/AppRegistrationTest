package com.appregistrationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Spinner countrySpinner;
    EditText phoneNumberEditText;
    TextView signInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countrySpinner = findViewById(R.id.spinnerCountries);
        phoneNumberEditText = findViewById(R.id.editTextPhoneNo);
        signInText = findViewById(R.id.textSignIn);

        countrySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Reg1Activity.class);
                startActivity(intent);
            }
        });
    }
}
