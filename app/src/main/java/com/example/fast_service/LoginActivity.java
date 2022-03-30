package com.example.fast_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void iniciarSesion(View v){
        EditText user = findViewById(R.id.editTextTextUser);
        EditText password = findViewById(R.id.editTextTextPassword);

        String x = user.getText().toString();
        String y = password.getText().toString();

        Log.i("===>", x);

        startActivity(new Intent(this, MainActivity.class));
    }
}