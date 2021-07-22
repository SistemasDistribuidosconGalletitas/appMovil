package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Receta;
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
        //insertUserTest();

        login_email = (EditText) findViewById(R.id.login_email);
        login_contrasena = (EditText) findViewById(R.id.login_contrasena);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v ->
                checkUserAndPass(login_email.getText().toString(), login_contrasena.getText().toString())
        );

    }

    private void iniciarSesion(boolean status) {
        if(status){
            Intent intent = new Intent(LoginActivity.this, RecetasActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Bienvenido al sistema", Toast.LENGTH_SHORT).show();

            repository.insertRecetaLocalDb(new Receta(1, "Dr. Simi", "2021-07-22", "2021-07-22", "2021-07-30"));
            repository.insertMedicamentoLocalDb(new Medicamento(1,1,"Naproxeno",
                    "2021-07-22", "2021-07-25", 1, "pastilla", "08:00", 8, "10", 1, false, 1));
            repository.insertMedicamentoLocalDb(new Medicamento(1,1,"Paracetamol",
                    "2021-07-22", "2021-07-25", 1, "pastilla", "09:00", 6, "10", 1, false, 1));
            repository.insertMedicamentoLocalDb(new Medicamento(1,1,"Gentamicina",
                    "2021-07-22", "2021-07-25", 1, "inyeccion", "12:00", 24, "10", 1, false, 1));

            finish();
        }else{
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();

        }
    }

    private void checkUserAndPass(String userName, String pass) {
        insertUserTest();
        Usuario usuario = repository.checkInformationUser(userName, pass);
        iniciarSesion(usuario != null);
    }
    private void insertUserTest() {
        repository.insertUserLocalDb(new Usuario(2, "Usuario autenticado", "admin", "admin", "1231231231"));
    }
}