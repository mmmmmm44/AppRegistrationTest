package com.appregistrationtest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    TextView identityText, nameText, schoolText, classText, telText;
    TextView[] electivesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        identityText = findViewById(R.id.textIdentity);
        nameText = findViewById(R.id.textName);
        schoolText = findViewById(R.id.textSchool);
        classText = findViewById(R.id.textClass);
        telText = findViewById(R.id.textTel);

        electivesText = new TextView[4];
        electivesText[0] = findViewById(R.id.textE1);
        electivesText[1] = findViewById(R.id.textE2);
        electivesText[2] = findViewById(R.id.textE3);
        electivesText[3] = findViewById(R.id.textMathExt);

    }

    @Override
    protected void onStart() {
        super.onStart();

        showPersonalInfo();
    }

    private void showPersonalInfo() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d("MyTag", user.name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed : " + databaseError.getMessage());
            }
        });
    }
}
