package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class CommonDetails extends AppCompatActivity {

    private Button btnSubmit;;
    private EditText txtWeight, txtHeight, txtAge, txtCholLevel, txtHeartRate, txtCigarettePerDay, txtBP;
    private RadioButton radioMale,radioFemale, radioYesStroke, radioNoStroke, radioYesSmoking, radioNoSmoking,
            radioYes100Cigarettes, radioNo100Cigarettes, radioYesBPMeds, radioNoBPMeds, radioYesDifWalk, radioNoDifWalk;
    String Gender = "Male", Stroke = "Yes", CurrentSmoker ="Yes", Cigarette100Life="Yes", BPMeds="yes", DifWalk="Yes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commondetails);

        btnSubmit = findViewById(R.id.btnSSubmit);
        txtWeight = findViewById(R.id.txtWeight);
        txtHeight = findViewById(R.id.txtHeight);
        txtAge = findViewById(R.id.txtAge);
        txtCholLevel = findViewById(R.id.txtCholLevel);
        txtHeartRate = findViewById(R.id.txtHearRate);
        txtCigarettePerDay = findViewById(R.id.txtCigarettePerDay);
        txtBP = findViewById(R.id.txtBloodPressure);

        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioYesStroke = findViewById(R.id.radioYesStroke);
        radioNoStroke = findViewById(R.id.radioNoStroke);
        radioYesSmoking = findViewById(R.id.radioYesSmoking);
        radioNoSmoking = findViewById(R.id.radioNoSmoking);
        radioYes100Cigarettes = findViewById(R.id.radioYes100Cigarettes);
        radioNo100Cigarettes = findViewById(R.id.radioNo100Cigarettes);
        radioYesBPMeds = findViewById(R.id.radioYesBPMeds);
        radioNoBPMeds = findViewById(R.id.radioNoBPMeds);
        radioYesDifWalk = findViewById(R.id.radioYesDifWalk);
        radioNoDifWalk = findViewById(R.id.radioNoDifWalk);


        final LoadingDialog loadingDialog = new LoadingDialog(CommonDetails.this);

        Intent myIntent = getIntent();
        String ID = myIntent.getStringExtra("ID");



        RadioGroup radioGender = (RadioGroup) findViewById(R.id.radioGender);
        RadioGroup radio100Cigarettes = (RadioGroup) findViewById(R.id.radio100Cigarettes);
        RadioGroup radioCurrentSmoker = (RadioGroup) findViewById(R.id.radioCurrentSmorker);
        RadioGroup radioStroke = (RadioGroup) findViewById(R.id.radioStroke);
        RadioGroup radioBPMeds = (RadioGroup) findViewById(R.id.radioBPMeds);
        RadioGroup radioDifWalk = (RadioGroup) findViewById(R.id.radioDifWalk);

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == R.id.radioMale) {
                        btn.setText("Male");
                    } else {
                        btn.setText("Female");
                    }
                    if (btn.getId() == checkedId) {

                        Gender = btn.getText().toString();

                    }

                }

                Log.e("Gender", Gender);
            }
        });

        radioStroke.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                String gender = null;
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == R.id.radioYesStroke) {
                        btn.setText("Yes");
                    } else {
                        btn.setText("No");
                    }
                    if (btn.getId() == checkedId) {

                        Stroke = btn.getText().toString();

                    }

                }

                Log.e("Stroke", Stroke);
            }
        });

        radioCurrentSmoker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                String gender = null;
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == R.id.radioYesSmoking) {
                        btn.setText("Yes");
                    } else {
                        btn.setText("No");
                    }
                    if (btn.getId() == checkedId) {

                        CurrentSmoker = btn.getText().toString();

                    }

                }

                Log.e("CurrentSmoker", CurrentSmoker);
            }
        });

        radio100Cigarettes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                String gender = null;
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == R.id.radioYes100Cigarettes) {
                        btn.setText("Yes");
                    } else {
                        btn.setText("No");
                    }
                    if (btn.getId() == checkedId) {
                        Cigarette100Life = btn.getText().toString();
                    }
                }
                Log.e("100CigaretteLife", Cigarette100Life);
            }
        });

        radioBPMeds.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                String gender = null;
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == R.id.radioYesBPMeds) {
                        btn.setText("Yes");
                    } else {
                        btn.setText("No");
                    }
                    if (btn.getId() == checkedId) {
                        BPMeds = btn.getText().toString();
                    }
                }
                Log.e("BPMeds", BPMeds);
            }
        });

        radioDifWalk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                String gender = null;
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == R.id.radioYesDifWalk) {
                        btn.setText("Yes");
                    } else {
                        btn.setText("No");
                    }
                    if (btn.getId() == checkedId) {
                        DifWalk = btn.getText().toString();
                    }
                }
                Log.e("DifWalk", DifWalk);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////'
                loadingDialog.StartLoadingDialog();
                String REGISTER_URL = "https://rpprojectapp.herokuapp.com/Update";
                JSONObject object = new JSONObject();
                try {
                    object.put("id", ID);
                    object.put("gender", Gender);
                    object.put("weight", txtWeight.getText().toString());
                    object.put("height", txtHeight.getText().toString());
                    object.put("age", txtAge.getText().toString());
                    object.put("colLev", txtCholLevel.getText().toString());
                    object.put("heartRate", txtHeartRate.getText().toString());
                    object.put("stroke", Stroke);
                    object.put("currentSmoker", CurrentSmoker);
                    object.put("entireLife100Cigarettes", Cigarette100Life);
                    object.put("cigarettePerDay", txtCigarettePerDay.getText().toString());
                    object.put("BPMeds", BPMeds);
                    object.put("BP", txtBP.getText().toString());
                    object.put("DifWalk", DifWalk);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, REGISTER_URL, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String res = response.getString("Msg");
                                    if (res.equals("Success")) {
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(CommonDetails.this, "Successfully Updated", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(CommonDetails.this, Suggestions.class);
                                        intent.putExtra("ID",ID);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(CommonDetails.this, "Error !", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(CommonDetails.this, "ER " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d("JSON", object.toString());
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
                /////////////////
            }
        });

        /////////
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
                            String gender = response.getString("gender");
                            String weight = response.getString("weight");
                            String height = response.getString("height");
                            String age = response.getString("age");
                            String colLev = response.getString("colLev");
                            String heartRate = response.getString("heartRate");
                            String BP = response.getString("BP");
                            String stroke = response.getString("stroke");
                            String currentSmoker = response.getString("currentSmoker");
                            String entireLife100Cigarettes = response.getString("entireLife100Cigarettes");
                            String BPMeds = response.getString("BPMeds");
                            String DifWalk = response.getString("DifWalk");
                            String cigarettePerDay = response.getString("cigarettePerDay");


                            if(gender.equals("Male")){
                                radioFemale.setChecked(true);
                            }else if (gender.equals("Female")){
                                radioMale.setChecked(true);
                            }

                            txtWeight.setText(weight.toString());

                            txtHeight.setText(height.toString());

                            txtAge.setText(age.toString());

                            txtCholLevel.setText(colLev.toString());

                            txtHeartRate.setText(heartRate.toString());

                            txtBP.setText(BP.toString());

                            if(stroke.equals("Yes")){
                                radioYesStroke.setChecked(true);
                            }else if (stroke.equals("No")){
                                radioNoStroke.setChecked(true);
                            }

                            if(currentSmoker.equals("Yes")){
                                radioYesSmoking.setChecked(true);
                            }else if (currentSmoker.equals("No")){
                                radioNoSmoking.setChecked(true);
                            }

                            if(entireLife100Cigarettes.equals("Yes")){
                                radioYes100Cigarettes.setChecked(true);
                            }else if (entireLife100Cigarettes.equals("No")){
                                radioNo100Cigarettes.setChecked(true);
                            }

                            if(BPMeds.equals("Yes")){
                                radioYesBPMeds.setChecked(true);
                            }else if (BPMeds.equals("No")){
                                radioNoBPMeds.setChecked(true);
                            }

                            if(DifWalk.equals("Yes")){
                                radioYesDifWalk.setChecked(true);
                            }else if (DifWalk.equals("No")){
                                radioNoDifWalk.setChecked(true);
                            }

                            txtCigarettePerDay.setText(cigarettePerDay.toString());



                        } catch (Exception e) {
                            Log.d("JSON", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(CommonDetails.this, "ER " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("JSON", object.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
        /////////////

    }
}