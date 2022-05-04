package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        get_type_user();
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
                    try{
                        int idusuario = response.getInt("id_usuario");
                        intent.putExtra("id_usuario", idusuario);
                    }
                     catch (JSONException e) {
                        Log.i("Parametros ======>", e.getMessage());
                    }
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

    public void get_type_user() {
        String url = "https://serviciomovil.azurewebsites.net/api/FastService/get-tipo-usuario";
        Spinner spinner = findViewById(R.id.spinnerOption);

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());
                    ArrayList<ServiceOptionTypeUser> ServiceOptions = new ArrayList<ServiceOptionTypeUser>();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);

                        ServiceOptions.add(new ServiceOptionTypeUser(Integer.parseInt(object.getString("id_tipo")), object.getString("descripcion")));

                    }

                    ArrayAdapter<ServiceOptionTypeUser> adapter = new ArrayAdapter<>(LoginActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ServiceOptions);

                    spinner.setAdapter(adapter);
                }
                catch (JSONException e) {
                    Log.i("======>", e.getMessage());
                } } }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.i("======>", error.toString()); } } );
        RequestQueue requestQueue= Volley.newRequestQueue(this); requestQueue.add(stringRequest);

    }

    private void LoadOptions(){

        Spinner spinner = findViewById(R.id.spinnerOption);

        ArrayList<ServiceOptionTypeUser> ServiceOptions = new ArrayList<ServiceOptionTypeUser>();
        ServiceOptions.add(new ServiceOptionTypeUser(1, "Consultas"));
        ServiceOptions.add(new ServiceOptionTypeUser(2, "Adquirir producto"));

        ArrayAdapter<ServiceOptionTypeUser> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ServiceOptions);

        spinner.setAdapter(adapter);

    }
}