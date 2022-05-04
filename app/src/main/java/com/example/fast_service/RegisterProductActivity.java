package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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
import java.util.List;

public class RegisterProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);

        get_productos();
    }

    public void get_productos() {

        String estado = "1";
        String url = "https://serviciomovil.azurewebsites.net/api/FastService/find-all-productos?estado="+estado;
        Spinner spinner = findViewById(R.id.spinnerOptionProductos);

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());
                    ArrayList<ServiceOptionTypeUser> ServiceOptions = new ArrayList<ServiceOptionTypeUser>();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);

                        ServiceOptions.add(new ServiceOptionTypeUser(Integer.parseInt(object.getString("id_producto")), object.getString("nombre")));

                    }

                    ArrayAdapter<ServiceOptionTypeUser> adapter = new ArrayAdapter<>(RegisterProductActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ServiceOptions);

                    spinner.setAdapter(adapter);
                }
                catch (JSONException e) {
                    Log.i("======>", e.getMessage());
                } } }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.i("======>", error.toString()); } } );
        RequestQueue requestQueue= Volley.newRequestQueue(this); requestQueue.add(stringRequest);

    }


    public void separa_producto(View v) {
        Spinner spinner = findViewById(R.id.spinnerOptionProductos);
        String Textspinner = spinner.getSelectedItem().toString();
        String url = "https://serviciomovil.azurewebsites.net/api/FastService/escoger-producto";

        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("producto", Textspinner);
            jsonobject.put("id_cliente", "1");
            jsonobject.put("comentario", "");
        } catch (JSONException e) {
            Log.i("======>", e.getMessage());
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonobject, new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response) {
                if (response != null ){
                    Log.i("======>", "bien");
                    Log.i("======>", response.toString());
                    Intent intent = new Intent(RegisterProductActivity.this, MainActivity.class);
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

    public void retroceder(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}