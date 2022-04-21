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

public class VisualizarPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_pedido);
        nuevoPedido();
    }

    public void nuevoPedido() {

        String url = "https://serviciomovil.azurewebsites.net/api/FastService/nueva-solicitud-pedido";

        /*JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response) {
                if (response != null ){
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
        requestQueue.add(jsonObjReq);*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("======>", response.toString());



                        try {
                            EditText nombre = findViewById(R.id.editTextNombre);
                            nombre.setText(response.getJSONObject("nombre").toString());
                            EditText celular = findViewById(R.id.editTextCelular);
                            celular.setText(response.getJSONObject("celular").toString());
                            EditText recojo = findViewById(R.id.editTextPuntoDeRecojo);
                            recojo.setText(response.getJSONObject("puntoRecojo").toString());
                            EditText destino = findViewById(R.id.editTextPuntoDeDestino);
                            destino.setText(response.getJSONObject("puntoDestino").toString());
                            EditText detalle = findViewById(R.id.editTextDetalle);
                            detalle.setText(response.getJSONObject("detalle").toString());

                        } catch (JSONException e) {
                            Log.i("======>", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", "mal");
                        Log.i("======>", error.toString());

                    }
                });
        // Access the RequestQueue through your singleton class.
       // MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}