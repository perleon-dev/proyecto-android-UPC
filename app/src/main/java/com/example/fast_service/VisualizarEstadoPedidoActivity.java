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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VisualizarEstadoPedidoActivity extends AppCompatActivity {
private int id_cliente = -1;
private String estado = "";
private String producto = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_estado_pedido);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_cliente = extras.getInt("id_usuario");
            Log.i("Parametros ======>", Integer.toString(id_cliente));
            //The key argument here must match that used in the other activity
        }
        verPedido2();
    }

    public void retroceder(View v) {

        Intent intent = new Intent(VisualizarEstadoPedidoActivity.this, MainActivity.class);
        intent.putExtra("id_usuario", id_cliente);
        startActivity(intent);

    }

    public void verPedido2(){

        String url = "https://serviciomovil.azurewebsites.net/api/FastService/get-by-id-cliente-pedido?id_persona=" + Integer.toString(id_cliente);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("======>", response.toString());

                if (response != null) {

                    try {

                        estado = response.getString("descripcion");
                        producto = response.getString("nombre");
                        TextView txtProducto = findViewById(R.id.txtEstadoProducto);
                        txtProducto.setText(producto);

                        if (estado.equals("En Almacen")){
                            Log.i("======>","entro al if");
                            EditText enCamino = findViewById(R.id.txtEnCamino);
                            enCamino.setVisibility(View.INVISIBLE);
                            ImageView enCaminoI = findViewById(R.id.imgEnCamino);
                            enCaminoI.setVisibility(View.INVISIBLE);

                            EditText pedidoListo = findViewById(R.id.txtPedidoListo);
                            pedidoListo.setVisibility(View.INVISIBLE);
                            ImageView pedidoListoI = findViewById(R.id.imgPedidoListo);
                            pedidoListoI.setVisibility(View.INVISIBLE);

                            EditText pedidoEntregado = findViewById(R.id.txtPedidoEntregado);
                            pedidoEntregado.setVisibility(View.INVISIBLE);
                            ImageView pedidoEntregadoI = findViewById(R.id.imgPedidoEntregado);
                            pedidoEntregadoI.setVisibility(View.INVISIBLE);


                        }

                        if (estado.equals("En Camino")){
                            Log.i("======>","entro al if");


                            EditText pedidoListo = findViewById(R.id.txtPedidoListo);
                            pedidoListo.setVisibility(View.INVISIBLE);
                            ImageView pedidoListoI = findViewById(R.id.imgPedidoListo);
                            pedidoListoI.setVisibility(View.INVISIBLE);

                            EditText pedidoEntregado = findViewById(R.id.txtPedidoEntregado);
                            pedidoEntregado.setVisibility(View.INVISIBLE);
                            ImageView pedidoEntregadoI = findViewById(R.id.imgPedidoEntregado);
                            pedidoEntregadoI.setVisibility(View.INVISIBLE);


                        }

                        if (estado.equals("Pedido listo para la entrega")){
                            Log.i("======>","entro al if");




                            EditText pedidoEntregado = findViewById(R.id.txtPedidoEntregado);
                            pedidoEntregado.setVisibility(View.INVISIBLE);
                            ImageView pedidoEntregadoI = findViewById(R.id.imgPedidoEntregado);
                            pedidoEntregadoI.setVisibility(View.INVISIBLE);


                        }








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


            }


        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);


    }


}