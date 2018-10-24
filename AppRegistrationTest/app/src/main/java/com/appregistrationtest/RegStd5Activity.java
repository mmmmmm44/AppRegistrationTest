package com.appregistrationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class RegStd5Activity extends AppCompatActivity {

    Spinner e1Spinner, e2Spinner, e3Spinner;
    RadioGroup m12RadioGp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_std5);

        e1Spinner = findViewById(R.id.spinnerE1);
        e2Spinner = findViewById(R.id.spinnerE2);
        e3Spinner = findViewById(R.id.spinnerE3);



        m12RadioGp = findViewById(R.id.radioGpM12);
    }
}
