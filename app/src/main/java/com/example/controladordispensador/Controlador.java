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
    Dispensador dispensador = new Dispensador(1, "HC-06",100);
    Usuario usuario = new Usuario(1, "jherestu",  dispensador);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlador);
        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "Bienvenido usuario con id: " + getIntent().getExtras().getString("id"),Toast.LENGTH_LONG).show();
        tvinformacion = (TextView)findViewById(R.id.tv_informacion);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mostrarInformacion(usuario);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void recogerDatos(View view){
        Intent connectToBT = new Intent(this, dispositivos_vinculados.class);
        startActivity(connectToBT);
        dispensador.setNum_usos(dispensador.getNum_usos() - 1);
        mostrarInformacion(usuario);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void mostrarInformacion(Usuario usuario){
        String string = "Usuario: " + usuario.getNombre() +"\n" +
                        "Dispensador: " + usuario.getDispensador().getNombre() + "\n" +
                        "Número de usos disponobles: " + usuario.getDispensador().getNum_usos();
        tvinformacion.setText(string);
    }

    //Envía los registros a la base de datos en firebase
    public void sendToDataBase(Usuario usuario){
        myRef.child(String.valueOf(usuario.getId())).child("Dispensadores").child(String.valueOf(usuario.getDispensador().getId())).setValue(usuario.getDispensador());
    }

    public void readFromDatabase(){
        // Read from the database
        myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nameuser = snapshot.child("nombre").getValue().toString();
                    String dispensador = snapshot.child("dispensador").child("id").getValue().toString();
                    int usosdisponibles = Integer.parseInt(snapshot.child("dispensador").child("usosDisponibles").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void cerrarSesion(View view){
        mAuth.signOut();
        finish();
    }
}