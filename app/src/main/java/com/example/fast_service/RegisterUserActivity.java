package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
    }

    public void register_user(View v) {
        final EditText nombre = findViewById(R.id.nombre);
        final EditText apellido = findViewById(R.id.apellido);
        final EditText dirección = findViewById(R.id.dirección);
        final EditText email = findViewById(R.id.email);
        final EditText clave = findViewById(R.id.clave);
        final EditText confirma_clave = findViewById(R.id.confirma_clave);

        String url = "https://serviciomovil.azurewebsites.net/api/FastService/register-user";

        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("nombre", nombre.getText().toString());
            jsonobject.put("apellido", apellido.getText().toString());
            jsonobject.put("direccion", dirección.getText().toString());
            jsonobject.put("correo", email.getText().toString());
            jsonobject.put("clave", clave.getText().toString());
            jsonobject.put("tipo_usuario", "1");

        } catch (JSONException e) {
            Log.i("======>", e.getMessage());
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonobject, new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response) {
                if (response != null ){
                    Log.i("======>", "bien");
                    Log.i("======>", response.toString());
                    Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
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