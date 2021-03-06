package com.example.fast_service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fast_service.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private int id_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_usuario = extras.getInt("id_usuario");
            //The key argument here must match that used in the other activity
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_estado_pedido:
                Intent myintent = new Intent(MainActivity.this, VisualizarEstadoPedidoActivity.class);
                myintent.putExtra("id_usuario",id_usuario);
                startActivity(myintent);
                return false;
            case R.id.action_registro_usuario:
                Intent myintent1 = new Intent(MainActivity.this, RegistroUsuario.class);
                startActivity(myintent1);
                return false;
            case R.id.action_visualizar_atenciones:
                Intent myintent2 = new Intent(MainActivity.this, VisualizarAtencionesActivity.class);
                myintent2.putExtra("id_usuario",id_usuario);

                startActivity(myintent2);

                return false;
            case R.id.action_visualizar_pedido:
                Intent myintent3 = new Intent(MainActivity.this, VisualizarPedidoActivity.class);
                myintent3.putExtra("id_usuario",id_usuario);
                startActivity(myintent3);
                return false;
            case R.id.nav_home:
                Intent myintent4 = new Intent(MainActivity.this, ConsumptionHistoryActivity.class);
                startActivity(myintent4);
                return false;
            case R.id.info_productos:
                Intent myintent5 = new Intent(MainActivity.this, InfoProductActivity.class);
                startActivity(myintent5);
                return false;
            case R.id.escoger_producto:
                Intent myintent6 = new Intent(MainActivity.this, RegisterProductActivity.class);
                startActivity(myintent6);
                return false;
            case R.id.action_ver_sucursales:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new SucursalesFragment()).addToBackStack(null).commit();
                return false;
            case R.id.action_ver_camara:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new CamaraFragment()).addToBackStack(null).commit();
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}