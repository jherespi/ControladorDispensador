package com.example.controladordispensador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    private EditText etCorreo, etPassword, etPassword2;
    private String correo, password, password2;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private static int id;
    private String nombre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etCorreo = (EditText)findViewById(R.id.txt_correo);
        etPassword = (EditText)findViewById(R.id.txt_password2);
        etPassword2 = (EditText)findViewById(R.id.txt_password3);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    //Recoje los datos para el registro
    public void registro(View view){
        correo = etCorreo.getText().toString();
        password = etPassword.getText().toString();
        password2 = etPassword2.getText().toString();

        for(char letra: correo.toCharArray()){
            if(letra != '@'){
                nombre += letra;
            }else{
                break;
            }
        }

        if(!correo.isEmpty() || !password.isEmpty() || !password2.isEmpty()){
            if(password2.length() >= 6 && password.length() >=6){
                if(password.equals(password2)){
                    registrarUsers();
                }
            }else{
                Toast.makeText(this, "La contraseña debe tener por lo menos 6 caracteres", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
    //Realiza el registro en la base de datos firebase
    private void registrarUsers(){
        mAuth.createUserWithEmailAndPassword(correo, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    enviarCorreoVerificacion();
                    //Agrega a base de datos
                    Map<String, Object> usuario = new HashMap<>();
                    usuario.put("nombre", nombre);
                    myRef.child("Usuarios").child(currentUser.getUid()).setValue(usuario);

                }else{
                    Toast.makeText(getApplicationContext(), "No se pudo registrar este usuario, intentelo nuevamente", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthUserCollisionException){//Excepción para verificar si el correo ya se encuentra en uso
                    etCorreo.setError("El correo ya está registrado");
                    etCorreo.requestFocus();
                }else{
                    Toast.makeText(Registro.this, "Opps, hubo un problema", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Se envía correo de verificación
    private void enviarCorreoVerificacion() {
        if(mAuth.getCurrentUser() != null){
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registro.this, "Un mensaje ha sido enviado a tu dirección de correo", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(Registro.this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void volver(View view){
        finish();
    }
}