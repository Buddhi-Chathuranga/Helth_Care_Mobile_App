package com.example.medicalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Suggestions extends AppCompatActivity {
    private ImageView imgBtn , imageViewHeart, imageViewDiabetes, imageViewCommon;
    private TextView txtHeart, txtDiabetes;
    String Test;
    String ID;
    private Button btnLogout, btnStress;

    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;
    String id;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Suggestions.this, Login.class);
        intent.putExtra("ID",ID);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);


        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedpreferences.getString("id", null);

        final LoadingDialog loadingDialog = new LoadingDialog(Suggestions.this);

        Intent myIntent = getIntent();
        ID = myIntent.getStringExtra("ID");
        imgBtn = findViewById(R.id.btnProfile);
        imageViewHeart = findViewById(R.id.imageViewHeart);
        imageViewDiabetes = findViewById(R.id.imageViewDiabetes);
        imageViewCommon = findViewById(R.id.imageViewCommon);
        txtHeart = findViewById(R.id.textHeart);
        txtDiabetes = findViewById(R.id.txtDiabetes);
        btnLogout = findViewById(R.id.btnLogOut);
        txtHeart.setVisibility(View.INVISIBLE);
        txtDiabetes.setVisibility(View.INVISIBLE);





        /////
        loadingDialog.StartLoadingDialog();
        String URL = "https://rpprojectapp.herokuapp.com/predict";
        JSONObject object = new JSONObject();
        try {
            object.put("id", ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, object,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("State", response.toString());
                try {
                    if(response.getString("Diabetes").toString().equals("0.0")) {
                        imageViewHeart.setImageResource(R.drawable.low);
                        txtHeart.setVisibility(View.VISIBLE);
                        txtDiabetes.setVisibility(View.VISIBLE);
                    }
                    if(response.getString("Diabetes").toString().equals("1.0")){
                        imageViewHeart.setImageResource(R.drawable.medium);
                        txtHeart.setVisibility(View.VISIBLE);
                        txtDiabetes.setVisibility(View.VISIBLE);
                    }
                    if(response.getString("Heart").toString().equals("2.0")){
                        imageViewHeart.setImageResource(R.drawable.high);
                        txtHeart.setVisibility(View.VISIBLE);
                        txtDiabetes.setVisibility(View.VISIBLE);
                    }
                    if(response.getString("Diabetes").toString().equals("0.0")){
                        imageViewDiabetes.setImageResource(R.drawable.low);
                        txtHeart.setVisibility(View.VISIBLE);
                        txtDiabetes.setVisibility(View.VISIBLE);
                    }
                    if(response.getString("Heart").toString().equals("1")){
                        imageViewDiabetes.setImageResource(R.drawable.high);
                        txtHeart.setVisibility(View.VISIBLE);
                        txtDiabetes.setVisibility(View.VISIBLE);
                    }
                    if(response.getString("Heart").toString().equals("reqFill")||response.getString("Diabetes").toString().equals("reqFill")){
                        imageViewCommon.setImageResource(R.drawable.please_complete_the_profile);
                    }
                    loadingDialog.dismissDialog();
                } catch (Exception e) {
                    Log.d("JSON", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", error.toString());
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Log.d("JSON", error.toString());
                loadingDialog.dismissDialog();
                Toast.makeText(Suggestions.this, "Error :::: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                ////////////////////////////////////////////////////
                onRestart();
            }
        });
        Log.d("JSON", object.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
        ///




        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Suggestions.this, CommonDetails.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(Suggestions.this, Login.class);
                startActivity(i);
                finish();
            }
        });

    }
}