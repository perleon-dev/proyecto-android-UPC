package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VisualizarAtencionesActivity extends AppCompatActivity {
    private int id_repartidor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_atenciones);
        verPedidos();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_repartidor = 2;// extras.getInt("id_usuario");

            //The key argument here must match that used in the other activity
        }
    }

    public void verPedidos() {


        String url = "https://serviciomovil.azurewebsites.net/api/FastService/search-product-idrepartidor";

        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("id_repartidor", new Integer(id_repartidor));


        } catch (JSONException e) {
            Log.i("Parametros ======>", e.getMessage());
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, jsonobject, new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response) {
                if (response != null ){
                    Log.i("======>", Integer.toString(id_repartidor));
                    Log.i("======>", "bien");
                    Log.i("======>", response.toString());

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