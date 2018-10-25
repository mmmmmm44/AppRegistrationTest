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


    TextView signInText, loginInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginInText = findViewById(R.id.textLoginIn);
        signInText = findViewById(R.id.textSignIn);

        loginInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to the next activity to login

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to the next activity to register

                Intent intent = new Intent(MainActivity.this, Reg1Activity.class);
                startActivity(intent);
            }
        });
    }
}
