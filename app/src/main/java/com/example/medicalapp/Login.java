package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Login extends AppCompatActivity {
    private Button button;
    private  ProgressBar progressBar;
    RequestQueue requestQueue;
    private String Email="", Password="";
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.btnLogin);
        EditText TxtEmail = findViewById(R.id.EmailAddress);
        EditText TxtPassword = findViewById(R.id.Password);
        final LoadingDialog loadingDialog = new LoadingDialog(Login.this);

       button.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               loadingDialog.StartLoadingDialog();
               Log.d("Exception", "AA");


               String REGISTER_URL = "https://rpprojectapp.herokuapp.com/authUser";
               final String email = TxtEmail.getText().toString();
               final String password = TxtPassword.getText().toString();
               Email = email;
               Password = password;

               JSONObject object = new JSONObject();
               try {
                   object.put("email", email);
                   object.put("password", password);
               } catch (JSONException e) {
                   e.printStackTrace();
               }

               JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, object,
                       new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {
//                        Toast.makeText(Login_screen.this,"String Response : "+ response.toString(),Toast.LENGTH_LONG).show();
                               try {
                                   String ID = response.getJSONObject("_id").getString("$oid");
                                   loadingDialog.dismissDialog();
                                   if (ID.equals("Invalid")){
                                       Toast.makeText(Login.this,"Please Check Email and Password",Toast.LENGTH_LONG).show();
                                   }
                                   else{
                                       Log.d("Inside IF", ID.toString());
                                       TxtEmail.setText("");
                                       TxtPassword.setText("");

                                       sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

                                       SharedPreferences.Editor editor = sharedpreferences.edit();
                                       editor.putString("id", ID.toString());
                                       editor.commit();

                                       Intent intent = new Intent(Login.this, Suggestions.class);
                                       intent.putExtra("ID",ID);
                                       startActivity(intent);
                                       finish();
                                   }

                               } catch (Exception e) {
                                   Log.d("Exception", e.toString());
                               }
//                        resultTextView.setText("String Response : "+ response.toString());
                           }
                       }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       loadingDialog.dismissDialog();
                       VolleyLog.d("Error 1", error.toString());
                       Toast.makeText(Login.this, "ER " + error.getMessage(), Toast.LENGTH_LONG).show();
                   }
               });
               RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
               requestQueue.add(jsonObjectRequest);
           }
       });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }
}