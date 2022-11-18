package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.entity.StringEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private Button button;
    private EditText Name, Phone, Email, Password, ConPassword;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Name = findViewById(R.id.txtName);
        Phone = findViewById(R.id.txtPhone);
        Email = findViewById(R.id.txtMail);
        Password = findViewById(R.id.txtPassword);
        ConPassword = findViewById(R.id.txtConPassword);

        final LoadingDialog loadingDialog = new LoadingDialog(Signup.this);


        button = (Button) findViewById(R.id.btnRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CheckEmpty();
                loadingDialog.StartLoadingDialog();
                String REGISTER_URL = "https://rpprojectapp.herokuapp.com/addNewUser";
                final String name = Name.getText().toString();
                final String password = Password.getText().toString();
                final String phone = Phone.getText().toString();
                final String email = Email.getText().toString();

                JSONObject object = new JSONObject();
                try {
                    //input your API parameters
                    object.put("name", name);
                    object.put("phone", phone);
                    object.put("email", email);
                    object.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
 //                               Toast.makeText(Signup.this,"String Response : "+ response.toString(),Toast.LENGTH_LONG).show();
                                try {
                                    String res = response.getString("Message");
                                    if (res.equals("UsedEmail")){
                                        Toast.makeText(Signup.this,"Email is Already Used",Toast.LENGTH_LONG).show();
                                        loadingDialog.dismissDialog();
                                    }
                                    else if (res.equals("Success")){
                                        Toast.makeText(Signup.this,"Success Registration",Toast.LENGTH_LONG).show();
                                        loadingDialog.dismissDialog();
                                        Name.setText("");
                                        Password.setText("");
                                        Phone.setText("");
                                        Email.setText("");
                                        ConPassword.setText("");

                                        Intent intent = new Intent(Signup.this, Login.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Signup.this,"Error !",Toast.LENGTH_LONG).show();
                                        loadingDialog.dismissDialog();
                                    }
                                } catch (Exception e) {
                                    Log.d("JSON", e.toString());
                                }
//                        resultTextView.setText("String Response : "+ response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog();
                        VolleyLog.d("Error", "Error: " + error.getMessage());
                        Toast.makeText(Signup.this, "ER " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    public void CheckEmpty() {
        if (Name.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Fill Name", Toast.LENGTH_SHORT).show();
        }
        if (Phone.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Fill Phone Number", Toast.LENGTH_SHORT).show();
        }
        if (Email.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Fill Email Address", Toast.LENGTH_SHORT).show();
        }
        if (Password.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Fill Password", Toast.LENGTH_SHORT).show();
        }
        if (ConPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Fill Confirm Password", Toast.LENGTH_SHORT).show();
        }

    }
}