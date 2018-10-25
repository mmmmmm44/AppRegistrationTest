package com.appregistrationtest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegStd5Activity extends AppCompatActivity {

    Spinner[] electivesSpinner;
    RadioGroup m12RadioGp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_std5);

        electivesSpinner = new Spinner[3];
        electivesSpinner[1] = findViewById(R.id.spinnerE1);
        electivesSpinner[2] = findViewById(R.id.spinnerE2);
        electivesSpinner[3] = findViewById(R.id.spinnerE3);

        //Set a array adapter to each of the spinners to show the electives
        for(Spinner spinner : electivesSpinner){
            spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, SchoolData.electives));
        }

        m12RadioGp = findViewById(R.id.radioGpM12);

        findViewById(R.id.btnUncheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m12RadioGp.clearCheck();
            }
        });

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Arraylist storing name of electives
                ArrayList<String> electives = new ArrayList<>();

                //Grab the name of electives

                for(Spinner spinner : electivesSpinner){
                    String elective = spinner.getSelectedItem().toString();
                    if(elective != "Not Applicable")
                        electives.add(elective);

                }

                String mathElective;
                if(m12RadioGp.getCheckedRadioButtonId() != -1){
                    int selectedId = m12RadioGp.getCheckedRadioButtonId();
                    RadioButton radioBtnSelected = findViewById(selectedId);
                    mathElective = radioBtnSelected.getText().toString();
                    electives.add(mathElective);
                }

                //Build up a confirmation window

                /*String[] electivesString = (String[]) electives.toArray();
                User user = (User) getIntent().getSerializableExtra(User.USER);
                user.setElectives(electivesString);

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(RegStd5Activity.this, getString(R.string.after_filling_electives), Toast.LENGTH_LONG).show();
                    }
                });
                */
            }
        });
    }
}
