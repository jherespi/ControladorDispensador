package com.example.controladordispensador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {
    private EditText etCorreo, etPassword, etPassword2;
    private String correo, password, password2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etCorreo = (EditText)findViewById(R.id.txt_correo);
        etPassword = (EditText)findViewById(R.id.txt_password2);
        etPassword2 = (EditText)findViewById(R.id.txt_password3);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    //Recoje los datos para el registro
    public void registro(View view){
        correo = etCorreo.getText().toString();
        password = etPassword.getText().toString();
        password2 = etPassword2.getText().toString();

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
        mAuth.createUserWithEmailAndPassword(password, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    finish();
                    //updateUI(user);
                }else{
                    Toast.makeText(getApplicationContext(), "No se pudo registrar este usuario, intentelo nuevamente", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}