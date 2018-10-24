package com.appregistrationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Reg1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg1);

        findViewById(R.id.btnStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(User.STUDENT);

                Intent intent = new Intent(Reg1Activity.this, Reg2Activity.class);
                intent.putExtra(User.USER, user);
                finish();
                startActivity(intent);

            }
        });

        findViewById(R.id.btnTeacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
