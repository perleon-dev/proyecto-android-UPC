package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class CancelarSolicitud extends AppCompatActivity {
    int id_solicitud = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_solicitud);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_solicitud = extras.getInt("id_solicitud");
            //The key argument here must match that used in the other activity
        }
    }

    public void cancelarSolicitud(View v) {

        final EditText observaciones = findViewById(R.id.editTextRazonCancelar);
        String url = "https://serviciomovil.azurewebsites.net/api/FastService/cancelar-solicitud";

        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("id_solicitud", new Integer(id_solicitud));
            jsonobject.put("observaciones",observaciones.getText().toString());

        } catch (JSONException e) {
            Log.i("Parametros ======>", e.getMessage());
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonobject, new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response) {
                if (response != null ){
                    Log.i("======>", Integer.toString(id_solicitud));
                    Log.i("======>", "bien");
                    Log.i("======>", response.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Se cancelò la solicitud correctamente")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    Intent intent = new Intent(CancelarSolicitud.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else{
                    Log.i("======>", "nulo");

                }

            } }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.i("======>", "mal");
                Log.i("======>", error.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Hubo un error")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } } );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }
}