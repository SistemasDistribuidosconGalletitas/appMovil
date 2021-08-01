package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.model.Usuario;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.interfaces.RecetaAPI;
import com.example.appssm.interfaces.UsuarioAPI;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        try {
            if (VerificaConectividad())
                Toast.makeText(this, "Conectado a Internet", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Por favor verifica tu conexión a Internet.", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Debe revisar la sesion en esta parte


        login_email = (EditText) findViewById(R.id.login_email);
        login_contrasena = (EditText) findViewById(R.id.login_contrasena);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(login_email.getText().toString().isEmpty() && login_contrasena.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Datos faltantes", Toast.LENGTH_SHORT).show();
                }else if(login_email.getText().toString().isEmpty() || login_contrasena.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Un dato faltante", Toast.LENGTH_SHORT).show();
                }else{
                    findDataBaseWeb(login_email.getText().toString(), login_contrasena.getText().toString());


                    //checkUserAndPass(login_email.getText().toString(), login_contrasena.getText().toString())

                }
            }
        });
                //checkUserAndPass(login_email.getText().toString(), login_contrasena.getText().toString())

    }

    private void iniciarSesion(boolean status, int id) {
        if (status) {
            Intent intent = new Intent(LoginActivity.this, RecetasActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Bienvenido al sistema", Toast.LENGTH_SHORT).show();



            findDataBaseWebRecetas(id);

//            repository.insertRecetaLocalDb(new Receta(1, "Dr. Simi", "2021-07-22", "2021-07-22", "2021-07-30"));
//            repository.insertRecetaLocalDb(new Receta(2, "Dr. h", "2021-07-22", "2021-07-22", "2021-07-30"));
//            repository.insertRecetaLocalDb(new Receta(3, "Dr. g", "2021-07-22", "2021-07-22", "2021-07-30"));
//            repository.insertMedicamentoLocalDb(new Medicamento(1, 1, "Naproxeno",
//                    "2021-07-22", "2021-07-25", 1, "pastilla", "08:00", 8, "10", 1, false, 1));
//            repository.insertMedicamentoLocalDb(new Medicamento(2, 2, "Paracetamol",
//                    "2021-07-22", "2021-07-25", 1, "pastilla", "09:00", 6, "10", 1, false, 1));
//            repository.insertMedicamentoLocalDb(new Medicamento(3, 1, "Gentamicina",
//                    "2021-07-22", "2021-07-25", 1, "inyeccion", "12:00", 24, "10", 1, false, 1));

            finish();
        } else {
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserAndPass(String userName, String pass) {
        //insertUserTest();
        Usuario usuario = repository.checkInformationUser(userName, pass);
        iniciarSesion(usuario != null, usuario.getId());
    }

    private void insertUserTest() {
        //Usuario(int id, String nombrePaciente, String correoElectronico, String contrasenia, String telefono, String nombreUsuario)
        repository.insertUserLocalDb(new Usuario(2, "Paciente", "admin@ssm.com", "admin", "1231231231", "admin"));
    }

    private boolean VerificaConectividad() {
        boolean _resultado = false;

        try {
            NetworkInfo _informacionRed = (((ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();

            boolean _es3g = _informacionRed.getType() == ConnectivityManager.TYPE_MOBILE;
            boolean _esWiFi = _informacionRed.getType() == ConnectivityManager.TYPE_WIFI;
            boolean _conexionEstablecida = _informacionRed.isConnectedOrConnecting();

            _resultado = ((_es3g || _esWiFi) && _conexionEstablecida) ? true : false;
        } catch (Exception ex) {
            throw ex;
        } finally {
            return _resultado;
        }
    }

    private void findDataBaseWeb(String nombreUsuario, String contrasenia) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://sistema-medico-app.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
///Usuario(int id, String nombrePaciente, String correoElectronico, String contrasenia, String telefono, String nombreUsuario)
        UsuarioAPI usuarioAPI = retrofit.create(UsuarioAPI.class);
        Call<Usuario> call = usuarioAPI.find(nombreUsuario, contrasenia);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {
                    if (response.isSuccessful()) {
                        Usuario p = response.body();
                        repository.insertUserLocalDb(new Usuario(p.getId(), p.getNombrePaciente(), p.getCorreoElectronico(), p.getContrasenia(), p.getTelefono(), p.getNombreUsuario()));
                        checkUserAndPass(login_email.getText().toString(), login_contrasena.getText().toString());
                    }
                } catch (Exception exception) {
                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Falló la conexión con servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findDataBaseWebRecetas(int id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://sistema-medico-app.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
//Receta(int idReceta, String fechaConsulta, String recetafechaInicio, String recetafechaFin, String nombreMedico, int paciente, boolean vigencia)
        RecetaAPI recetaAPI = retrofit.create(RecetaAPI.class);
        Call<List<Receta>> call = recetaAPI.find(id);

        call.enqueue(new Callback<List<Receta>> () {
            @Override
            public void onResponse(Call<List<Receta>>  call, Response<List<Receta>>  response) {
                List<Receta> recetaList = response.body();
                try {
                    if (response.isSuccessful()) {
                        for (Receta p: recetaList){
                            repository.insertRecetaLocalDb(new Receta(p.getIdReceta(), p.getFechaConsulta(), p.getRecetafechaInicio(), p.getRecetafechaFin(), p.getPaciente(), p.getNombreMedico(), p.isVigencia()));
                        }
                        //checkUserAndPass(login_email.getText().toString(), login_contrasena.getText().toString());
                    }
                } catch (Exception exception) {
                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Receta>>  call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Falló la conexión con servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}