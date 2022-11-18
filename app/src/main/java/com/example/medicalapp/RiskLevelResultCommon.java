package com.example.medicalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RiskLevelResultCommon extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_level_result_common);

        button =(Button) findViewById(R.id.suggestions);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencommondetails();
            }
        });
    }
    public void opencommondetails(){
        Intent intent = new Intent(this, Suggestions.class);
        startActivity(intent);
    }
}