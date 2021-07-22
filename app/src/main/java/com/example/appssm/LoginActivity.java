package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appssm.domain.model.Usuario;
import com.example.appssm.domain.repository.Repository;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText login_email, login_contrasena;

    Button btn_login;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        repository = new Repository(getApplicationContext());
        insertUserTest();

        login_email = (EditText) findViewById(R.id.login_email);
        login_contrasena = (EditText) findViewById(R.id.login_contrasena);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v ->
                checkUserAndPass(login_email.getText().toString(), login_contrasena.getText().toString())
        );

    }

    private void iniciarSesion(boolean status) {
        if(status){
            //Intent intent = new Intent(LoginActivity.this, RecetaActivity.class);
            //startActivity(intent);
            Toast.makeText(this, "Bienvenido al sistema", Toast.LENGTH_SHORT).show();
            //finish();
        }else{
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();

        }
    }

    private void checkUserAndPass(String userName, String pass) {
        Usuario usuario = repository.checkInformationUser(userName, pass);
        iniciarSesion(usuario != null);
    }
    private void insertUserTest() {
        repository.insertUserLocalDb(new Usuario(2, "Usuario autenticado", "admin", "admin", "1231231231"));
    }
}