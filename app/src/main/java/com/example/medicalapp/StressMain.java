package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class StressMain extends AppCompatActivity {
    private View btnContinue;
    private EditText txtHumidity, txtTemp, txtStep;
    private ImageView imageViewStress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_main);

        final LoadingDialog loadingDialog = new LoadingDialog(StressMain.this);
        Intent myIntent = getIntent();
        String ID = myIntent.getStringExtra("ID");

        btnContinue = findViewById(R.id.btnContinue);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtTemp = findViewById(R.id.txtTemp);
        txtStep = findViewById(R.id.txtStep);
        imageViewStress = findViewById(R.id.imageViewStress);


        /////////
        loadingDialog.StartLoadingDialog();
        String REGISTER_URL = "https://rpprojectapp.herokuapp.com/getUser";
        JSONObject object = new JSONObject();
        try {
            object.put("id", ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String humidity = response.getString("humidity");
                            String temp = response.getString("temp");
                            String stepCount = response.getString("stepCount");

                            if(!humidity.equals("")){
                                txtHumidity.setText(humidity.toString());
                            }
                            if(!temp.equals("")){
                                txtTemp.setText(temp.toString());
                            }
                            if(!stepCount.equals("")){
                                txtStep.setText(stepCount.toString());
                            }

                        } catch (Exception e) {
                            Log.d("JSON", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(StressMain.this, "ER " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("JSON", object.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
        loadingDialog.dismissDialog();
        /////////////


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                try {
                    loadingDialog.StartLoadingDialog();
                    String REGISTER_URL = "https://rpprojectapp.herokuapp.com/stressAddData";
                    JSONObject object = new JSONObject();
                    try {
                        object.put("id", ID);
                        object.put("humidity", txtHumidity.getText().toString());
                        object.put("temp", txtTemp.getText().toString());
                        object.put("stepCount", txtStep.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, object,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String res = response.getString("Msg");
                                        if (res.equals("Success")) {
                                            loadingDialog.dismissDialog();
                                            Toast.makeText(StressMain.this, "Successfully Updated", Toast.LENGTH_LONG).show();

                                        } else {
                                            Toast.makeText(StressMain.this, "Error !", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        Log.d("JSON", e.toString());
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loadingDialog.dismissDialog();
                            VolleyLog.d("Error", "Error: " + error.getMessage());
                            Toast.makeText(StressMain.this, "ER " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.d("JSON", object.toString());
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                }
                catch (Exception e){
                    loadingDialog.dismissDialog();
                    Toast.makeText(StressMain.this, "ER " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("JSON", e.toString());
                }

                loadingDialog.dismissDialog();

                loadingDialog.StartLoadingDialog();
                String URL = "https://rpprojectapp.herokuapp.com/predictStress";
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
                            if(response.getString("Stress").toString().equals("0")) {
                                imageViewStress.setImageResource(R.drawable.low);
                                loadingDialog.dismissDialog();
                            }
                            if(response.getString("Stress").toString().equals("1")){
                                imageViewStress.setImageResource(R.drawable.medium);
                                loadingDialog.dismissDialog();
                            }
                            if(response.getString("Stress").toString().equals("2")){
                                imageViewStress.setImageResource(R.drawable.high);
                                loadingDialog.dismissDialog();
                            }

                        } catch (Exception e) {
                            Log.d("JSON", e.toString());
                            loadingDialog.dismissDialog();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON", error.toString());
                        VolleyLog.d("Error", "Error: " + error.getMessage());
                        Log.d("JSON", error.toString());
                        loadingDialog.dismissDialog();
                        Toast.makeText(StressMain.this, "Error :::: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        ////////////////////////////////////////////////////
                    }
                });
                Log.d("JSON", object.toString());
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
                ///
                ///
                loadingDialog.dismissDialog();

            }
        });
    }
}