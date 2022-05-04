package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class VisualizarPedidoActivity extends AppCompatActivity {
    private int id_solicitud = -1;
    private int id_repartidor;
    private int id_cliente;
    private int id_producto;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_pedido);
        nuevoPedido();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_repartidor = extras.getInt("id_usuario");
            Log.i("Se obtuvo usuario ======>", Integer.toString(id_repartidor));
            //The key argument here must match that used in the other activity
        }
    }
    public void recharzarSolicitud(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(VisualizarPedidoActivity.this, CancelarSolicitud.class);
        intent.putExtra("id_solicitud",id_solicitud);
        startActivity(intent);

    }
    public void aceptarPedido(View v) {


        String url = "https://serviciomovil.azurewebsites.net/api/FastService/aceptar-pedido";

        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("id_solicitud", new Integer(id_solicitud));
            jsonobject.put("id_repartidor",new Integer(id_repartidor));
            jsonobject.put("id_cliente",new Integer(id_cliente));
            jsonobject.put("id_producto",new Integer(id_producto));

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
                    builder.setMessage("Se aceptó la solicitud correctamente")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    Intent intent = new Intent(VisualizarPedidoActivity.this, MainActivity.class);
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
    public void nuevoPedido() {

        String url = "https://serviciomovil.azurewebsites.net/api/FastService/nueva-solicitud-pedido";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("======>", response.toString());

                        if (response != null) {

                            try {
                                id_solicitud = response.getInt("id_solicitud");
                                id_cliente = response.getInt("id_usuario");
                                id_producto = response.getInt("id_producto");
                                EditText nombre = findViewById(R.id.editTextNombre);
                                nombre.setText(response.getString("nombre"));
                                EditText celular = findViewById(R.id.editTextCelular);
                                celular.setText(response.getString("telefono"));
                                EditText recojo = findViewById(R.id.editTextPuntoDeRecojo);
                                recojo.setText(response.getString("punto_recojo"));
                                EditText destino = findViewById(R.id.editTextPuntoDeDestino);
                                destino.setText(response.getString("punto_destino"));
                                EditText detalle = findViewById(R.id.editTextDetalle);
                                detalle.setText(response.getString("detalle"));

                            } catch (JSONException e) {
                                Log.i("======>", e.getMessage());
                            }
                        } else {
                            Log.i("======>", "nulo");



                        }
                    }}, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", "mal");
                        Log.i("======>", error.toString());
                        AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarPedidoActivity.this);
                        builder.setMessage("No hay registros")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                        Intent intent = new Intent(VisualizarPedidoActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });
        // Access the RequestQueue through your singleton class.
       // MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}