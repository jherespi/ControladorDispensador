package com.example.controladordispensador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Controlador extends AppCompatActivity {

    //Atributos de base de datos
    private TextView tvinformacion;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    Dispensador dispensador;
    Usuario usuario;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlador);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this, "Bienvenido usuario con id: " + getIntent().getExtras().getString("id"),Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Bienvenido: " + currentUser.getDisplayName(),Toast.LENGTH_LONG).show();

        tvinformacion = (TextView)findViewById(R.id.tv_informacion);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        tvinformacion.setText("Usuario: " + currentUser.getDisplayName());
    }

    public void recogerDatos(View view){
        Intent connectToBT = new Intent(this, dispositivos_vinculados.class);
        startActivity(connectToBT);
        // Read from the database
        //readFromDatabase();
    }

    public void mostrarInformacion(Usuario usuario){
        String string = "Usuario: " + usuario.getNombre() +"\n" +
                        "Dispensador: " + usuario.getDispensador().getNombre() + "\n" +
                        "Número de usos disponibles: " + usuario.getDispensador().getNum_usos();
        tvinformacion.setText(string);
    }

    public void readFromDatabase(){
        // Read from the database
        myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nameuser = snapshot.child("Usuarios").child(currentUser.getUid()).child("nombre").getValue().toString();
                    String dispensadorf = snapshot.child("Usuarios").child(currentUser.getUid()).child("Dispensadores").child("nombre").getValue().toString();
                    int usosdisponibles = Integer.parseInt(snapshot.child("Usuarios").child(currentUser.getUid()).child("Dispensadores").child("").child("Usos disponibles").getValue().toString());

                    dispensador = new Dispensador(dispensadorf,usosdisponibles);
                    dispensador.setNum_usos(dispensador.getNum_usos() - 1);
                    usuario = new Usuario(nameuser,  dispensador);
                }
                Toast.makeText(Controlador.this, "No se puede acceder a la información buscada", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Controlador.this, "No se encuentra la información buscada", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void cerrarSesion(View view){
        mAuth.signOut();
        finish();
    }
}