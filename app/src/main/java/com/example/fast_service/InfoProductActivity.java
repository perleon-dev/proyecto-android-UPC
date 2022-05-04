package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_product);

        infoUsuario();
        infoPedido();
    }

    public void infoUsuario() {

        String id_cliente = "1";
        String url = "https://serviciomovil.azurewebsites.net/api/FastService/get_by_id_usuario?id_persona="+id_cliente;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("======>", response.toString());

                        try {
                            EditText nombre = findViewById(R.id.nombre_tb);
                            nombre.setText(response.getString("nombre"));
                            EditText apellido = findViewById(R.id.apellido_tb);
                            apellido.setText(response.getString("apellido"));
                            EditText direccion = findViewById(R.id.direccion_tb);
                            direccion.setText(response.getString("direccion"));

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

    public void infoPedido() {

        String id_cliente = "1";
        String url = "https://serviciomovil.azurewebsites.net/api/FastService/get-by-id-cliente-pedido?id_persona="+id_cliente;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("======>", response.toString());

                        try {
                            EditText pedido = findViewById(R.id.pedido_id_tb);
                            pedido.setText(response.getString("id_pedido"));

                            EditText pedidoDesc = findViewById(R.id.envio_descrip);
                            pedidoDesc.setText(response.getString("nombre"));

                            RadioGroup contenedor = (RadioGroup) findViewById(R.id.group_radio);
                            Integer numId = Integer.parseInt(response.getString("id_estado"));


                            RadioButton opcionI2 = (RadioButton) contenedor.getChildAt(numId-1);

                           contenedor.check(opcionI2.getId());

                            Log.i("======>", numId.toString());

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

    public void retroceder(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}