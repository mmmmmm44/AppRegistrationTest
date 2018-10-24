package com.appregistrationtest;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class RegStd4Activity extends AppCompatActivity {

    private boolean mSpinnerInitialized;
    Spinner schoolSpinner, classSpinner;
    EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_std4);

        nameEditText = findViewById(R.id.editTextName);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(s).isEmpty()){
                    schoolSpinner.setEnabled(false);
                    classSpinner.setEnabled(false);
                }
                else{
                    schoolSpinner.setEnabled(true);
                    classSpinner.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        schoolSpinner = findViewById(R.id.spinnerSchool);
        classSpinner = findViewById(R.id.spinnerClass);

        schoolSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, SchoolData.schoolNames));
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Skip the work on installation
                if (!mSpinnerInitialized) {
                    mSpinnerInitialized = true;
                    return;
                }

                classSpinner.setAdapter(new ArrayAdapter<>(RegStd4Activity.this, android.R.layout.simple_spinner_dropdown_item, SchoolData.schoolClasses[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        schoolSpinner.setEnabled(false);
        classSpinner.setEnabled(false);

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String schoolName = schoolSpinner.getSelectedItem().toString();
                String schoolClass = classSpinner.getSelectedItem().toString();

                Log.d("myLog", name + ", " + schoolName + ", " + schoolClass);

                Intent intent = new Intent(RegStd4Activity.this, RegStd5Activity.class);

                User user = (User) getIntent().getSerializableExtra(User.USER);
                user.setPersonalInfo(name, schoolName, schoolClass);
                intent.putExtra(User.USER, user);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Clean other activities when start the following up activity
                startActivity(intent);

            }
        });
    }



}
