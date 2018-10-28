package com.appregistrationtest;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RegStd5Activity extends AppCompatActivity {

    int width, height;

    Spinner[] electivesSpinner;
    RadioGroup m12RadioGp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_std5);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //Get screen's width and height
        width = dm.widthPixels;
        height = dm.heightPixels;

        electivesSpinner = new Spinner[3];
        electivesSpinner[0] = findViewById(R.id.spinnerE1);
        electivesSpinner[1] = findViewById(R.id.spinnerE2);
        electivesSpinner[2] = findViewById(R.id.spinnerE3);

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

                //Grab the name of electives (e1 to e3)
                for(Spinner spinner : electivesSpinner){
                    String elective = spinner.getSelectedItem().toString();
                    electives.add(elective);
                }

                //Grab the math elective
                String mathElective;
                if(m12RadioGp.getCheckedRadioButtonId() != -1){
                    int selectedId = m12RadioGp.getCheckedRadioButtonId();
                    RadioButton radioBtnSelected = findViewById(selectedId);
                    mathElective = radioBtnSelected.getText().toString();
                }
                else{
                    mathElective = SchoolData.electives[SchoolData.electives.length-1];
                }
                electives.add(mathElective);

                //Build up a confirmation window

                //Show the confirmation dialog
                showConfirmationDialog(electives);




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

    /**
     *  Show a confirmation dialog to the user
     * @param electives user's selected electives
     */
    private void showConfirmationDialog(final ArrayList<String> electives) {

        Dialog dialogConfirm = new Dialog(RegStd5Activity.this, R.style.AppTheme_ElectiveConfirm);
        dialogConfirm.setContentView(R.layout.activity_reg_std5_confirm);

        //dialogConfirm.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialogConfirm.getWindow().setLayout((int)(width * 0.85), (int)(height * 0.75));

        //Define views
        TextView[] textElectives = new TextView[3];
        textElectives[0] = dialogConfirm.findViewById(R.id.textE1);
        textElectives[1] = dialogConfirm.findViewById(R.id.textE2);
        textElectives[2] = dialogConfirm.findViewById(R.id.textE3);
        TextView textMathExt = dialogConfirm.findViewById(R.id.textMathExt);
        Button btnConfirm = dialogConfirm.findViewById(R.id.btnConfirm);

        //The last string represents the Math extended part
        textMathExt.setText(electives.get(electives.size()-1));

        //Set the text of the electives
        for(int i = 0; i < electives.size()-1; i++){
            textElectives[i].setText(electives.get(i));
        }

        //if next btn is clicked --> finish the confirmation and upload the user's electives to the database
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationCompleted(electives);
            }
        });

        //Show the confirm dialog
        dialogConfirm.show();
    }

    /**
     * Confirmation is completed and upload the electives to the database
     * @param electives the confirmed electives
     */
    private void confirmationCompleted(ArrayList<String> electives) {

        //Grab the arraylist string
        //Take out the Math extended part string
        //**"Not Applicable" strings = null elective
        //Upload them to the database

        //Put the electives arraylist to the User class
        User user = (User) getIntent().getSerializableExtra(User.USER);
        user.setElectives(electives);
        Log.d("MyTag", electives.toString());

        //Upload the array to the user class
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegStd5Activity.this, getString(R.string.after_filling_electives), Toast.LENGTH_LONG).show();

                    //Go to the reg_end Activity
                    Intent intent = new Intent(RegStd5Activity.this, RegEndActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    startActivity(intent);
                }
            }
        });


    }
}
