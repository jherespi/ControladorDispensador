package com.example.controladordispensador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText etCorreo, etcontrasena;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etCorreo =(EditText)findViewById(R.id.txt_correoLog);
        etcontrasena = (EditText)findViewById(R.id.txt_password);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    //Método para el boton de registro: Abre el registro del usuario
    public void registrar(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
    //Validación de los datos ingresados
    public boolean validacion(String correo, String contrasena){
        if(correo.isEmpty() || contrasena.isEmpty()){
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    //Método para el boton de inicio: Lleva al usuario al controlador
    public void inicioSesion(View view){
        String correo = etCorreo.getText().toString();
        String contrasena = etcontrasena.getText().toString();
        if(validacion(correo, contrasena)){
            mAuth.signInWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent iniciosesion = new Intent(MainActivity.this, Controlador.class);
                                iniciosesion.putExtra("id",user.getUid());
                                startActivity(iniciosesion);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "No se pudo autenticar al usuario, intentelo nuevamente",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }
}
