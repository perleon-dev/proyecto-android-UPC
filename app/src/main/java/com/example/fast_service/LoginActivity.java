package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void register_activity(View v) {
        startActivity(new Intent(this, RegisterUserActivity.class));
    }

    public void login_user(View v) {
        final EditText user = findViewById(R.id.editTextTextUser);
        final EditText password = findViewById(R.id.editTextTextPassword);
        String url = "https://serviciomovil.azurewebsites.net/api/FastService/login";

        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("userName", user.getText().toString());
            jsonobject.put("password", password.getText().toString());

        } catch (JSONException e) {
            Log.i("======>", e.getMessage());
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonobject, new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response) {
                if (response != null ){
                    Log.i("======>", "bien");
                    Log.i("======>", response.toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.i("======>", "nulo");
                }

                } }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.i("======>", "mal");
                Log.i("======>", error.toString());
            } } );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }
}