package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginAndSignup extends AppCompatActivity {

    private Button button1;
    private Button button2;

    private static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_signup);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedpreferences.getString("id", null);


       button1 = findViewById(R.id.login);
       button2 = findViewById(R.id.btnRegister);

       button1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(LoginAndSignup.this, Login.class);
               startActivity(intent);
           }
       });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAndSignup.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (id != null) {
            Intent i = new Intent(LoginAndSignup.this, Suggestions.class);
            i.putExtra("ID",id.toString());
            startActivity(i);
        }
    }
}