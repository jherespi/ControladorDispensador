package com.example.controladordispensador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Controlador extends AppCompatActivity {

    private TextView tvinformacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlador);

        tvinformacion = (TextView)findViewById(R.id.tv_informacion);
    }

    public void MostrarInformacion(View view){
        tvinformacion.setText("Aquí se muestra la info");
    }

    public void CerrarSesion(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
