package com.appregistrationtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Reg3Activity extends AppCompatActivity {

    private String verificationId;
    String phoneNumber;
    private FirebaseAuth mAuth;
    private EditText codeEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg3);

        //Get the user's phone number from the intent
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);
        codeEditText = findViewById(R.id.editTextCode);

        sendVerificationCode(phoneNumber);

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeEditText.getText().toString().trim();

                if(code.isEmpty() || code.length() < 6){
                    codeEditText.setError("Enter code...");
                    codeEditText.requestFocus();
                }

                verifyCode(code);
            }
        });

    }

    /**
     * Send the verification code (the one w/ 6 integers) to the user for phone verification to login
     * @param phoneNumber the phone number of the user
     */
    private void sendVerificationCode(String phoneNumber) {
        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    //A callback object to verification code
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //Store the verification id (not the code!!)
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //When the verification is completed, the system get the sms code (the one w/ 6 integers) by itself
            //Then autofill the code for user and verify the code

            String code = phoneAuthCredential.getSmsCode();

            if(code != null){
                codeEditText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Reg3Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Verify the code, and get a credential for user to login the system
     * @param code the 6 integers sms code
     */
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    /**
     * Sign in the user to the system with the credential
     * @param credential the credential after phone auth
     */
    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //Upload the user class to the dummy user database

                    //Get the user class
                    User user = (User) getIntent().getSerializableExtra(User.USER);
                    user.setPhoneNumber(phoneNumber);

                    //Upload the class to the firebase database
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Reg3Activity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(Reg3Activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                    //Load the next registration process page
                    Intent intent = new Intent(Reg3Activity.this, RegStd4Activity.class);

                    intent.putExtra(User.USER, user);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Clean other activities when start the following up activity
                    startActivity(intent);
                }else{
                    Toast.makeText(Reg3Activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
