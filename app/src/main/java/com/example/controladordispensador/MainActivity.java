package com.example.controladordispensador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etnombre, etcontraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etnombre =(EditText)findViewById(R.id.txt_nombre);
        etcontraseña = (EditText)findViewById(R.id.txt_password);
    }

    //Método para el boton de registro: Registra al usuario
    public void Registrar(View view){
        if(Validacion()){

        }
    }
    //Validación de los datos ingresados
    public boolean Validacion(){
        String nombre = etnombre.getText().toString();
        String contraseña = etcontraseña.getText().toString();

        if(nombre.isEmpty() || contraseña.isEmpty()){
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
            return false;
        }else{
            Toast.makeText(this, "Validación completa",Toast.LENGTH_LONG).show();
            return true;
        }
    }

    //Método para el boton de inicio: Lleva al usuario al controlador
    public void InicioSesion(View view){
        if(Validacion()){
            Intent iniciosesion = new Intent(this, Controlador.class);
            startActivity(iniciosesion);
        }
    }
}
