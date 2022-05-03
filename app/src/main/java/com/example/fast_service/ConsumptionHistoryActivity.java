package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumptionHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_history);

        cargarProductos();
    }

    public void cargarProductos(){
        String criterio = "1";
        String url = "https://serviciomovil.azurewebsites.net/api/FastService/search-product-idcliente?id_cliente="+criterio;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                try { JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());
                    List<String> items = new ArrayList<>();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        items.add(object.getString("nombre") + " (S/. "+object.getString("precio")+") ");
                    }
                    ListView lstProductos = findViewById(R.id.lista);
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>( ConsumptionHistoryActivity.this, android.R.layout.simple_list_item_1, items);

        lstProductos.setAdapter(adaptador);
                }
                catch (JSONException e) {
                    Log.i("======>", e.getMessage());
                } } }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.i("======>", error.toString()); } } );
        RequestQueue requestQueue= Volley.newRequestQueue(this); requestQueue.add(stringRequest);
    }


    public void retroceder(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

}