package com.example.controladordispensador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Controlador extends AppCompatActivity {

    private TextView tvinformacion;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlador);
        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "Bienvenido " + getIntent().getExtras().getString("nombre"),Toast.LENGTH_LONG).show();
        tvinformacion = (TextView)findViewById(R.id.tv_informacion);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void mostrarInformacion(View view){
        tvinformacion.setText("Aquí se muestra la info");
    }

    public void cerrarSesion(View view){
        mAuth.signOut();
        finish();
    }
}
