package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appssm.domain.ServidorContexto;
import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.model.Usuario;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.interfaces.MedicamentoAPI;
import com.example.appssm.interfaces.RecetaAPI;
import com.example.appssm.interfaces.UsuarioAPI;
import com.example.appssm.notificacion.AlertReceiver;

import java.util.ArrayList;
import java.util.Calendar;
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
    private ProgressDialog progressDialog;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String llave = "llave";

    List<Receta> listReceta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        repository = new Repository(getApplicationContext());


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID","CHANNEL_ID", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
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
        preferences = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor = preferences.edit();

        if (revisarSesion()) {
            Intent intent = new Intent(LoginActivity.this, RecetasActivity.class);
            startActivity(intent);
            finish();
        }




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


            editor.putBoolean(this.llave, status);
            editor.apply();

            findDataBaseWebRecetas(id);
            findDataBaseWebMedicamento();


            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );


            Runnable r = new Runnable() {

                @Override
                public void run() {
                    List<Medicamento> listMedicamento;
                    // Se activan las notificaciones
                    listReceta = repository.getAllRecetas();
                    for (Receta rec: listReceta) {
                        listMedicamento = repository.getAllMedicamentosByReceta(rec.getIdReceta());
                        for (Medicamento med: listMedicamento) {
                            String horaAplicacion =  med.getHoraAplicacion();
                            int hora = Integer.parseInt(horaAplicacion.substring(0,2));
                            int min = Integer.parseInt(horaAplicacion.substring(3,5));
                            Log.i("HORA", String.valueOf(hora));
                            Log.i("MIN", String.valueOf(min));
                            //onTimeSet(hora,min,med.getNombre(),med.getHoraAplicacion());
                            ServidorContexto.revisarHrAplicacion(getApplicationContext(),hora,min,med.getNombre(),med.getHoraAplicacion());
                        }
                    }
                    // if you are redirecting from a fragment then use getActivity() as the context.
                    Intent intent = new Intent(LoginActivity.this, RecetasActivity.class);
                    startActivity(intent);
                    finish();

                }
            };


            Handler h = new Handler();
            // The Runnable will be executed after the given delay time
            h.postDelayed(r, 1500); // will be delayed for 1.5 seconds



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
                            ServidorContexto.crearReceta(repository,new Receta(p.getIdReceta(), p.getFechaConsulta(), p.getRecetafechaInicio(), p.getRecetafechaFin(), p.getPaciente(), p.getNombreMedico(), p.isVigencia()));
                            //repository.insertRecetaLocalDb(new Receta(p.getIdReceta(), p.getFechaConsulta(), p.getRecetafechaInicio(), p.getRecetafechaFin(), p.getPaciente(), p.getNombreMedico(), p.isVigencia()));
                        }
                        //Toast.makeText(LoginActivity.this, "Datos cargados exitosamente", Toast.LENGTH_SHORT).show();
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

    private void findDataBaseWebMedicamento() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://sistema-medico-app.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
//Medicamento(int id, String nombre, String tipo, int dosis, int aplicaciones, String fechaInicio, String fechaFin, String horaAplicacion, double intervalo, String margenTiempo, int prioridad
        MedicamentoAPI medicamentoAPI = retrofit.create(MedicamentoAPI.class);
        Call<List<Medicamento>> call = medicamentoAPI.find();

        call.enqueue(new Callback<List<Medicamento>> () {
            @Override
            public void onResponse(Call<List<Medicamento>>  call, Response<List<Medicamento>>  response) {
                List<Medicamento> medicamentoList = response.body();
                try {
                    if (response.isSuccessful()) {
                        for (Medicamento p: medicamentoList){
                            ServidorContexto.crearMedicamentos(repository, new Medicamento(p.getId(), p.getNombre(), p.getTipo(),
                                    p.getDosis(), p.getAplicaciones(), p.getFechaInicio(), p.getFechaFin(), p.getHoraAplicacion(), p.getIntervalo(),
                                    p.getMargenTiempo(), p.getPrioridad(), p.getReceta()));
//                            repository.insertMedicamentoLocalDb(new Medicamento(p.getId(), p.getNombre(), p.getTipo(),
//                                    p.getDosis(), p.getAplicaciones(), p.getFechaInicio(), p.getFechaFin(), p.getHoraAplicacion(), p.getIntervalo(),
//                                    p.getMargenTiempo(), p.getPrioridad(), p.getReceta()));
                        }
                        //Toast.makeText(LoginActivity.this, "Datos cargados exitosamente medicamento", Toast.LENGTH_SHORT).show();
                        //checkUserAndPass(login_email.getText().toString(), login_contrasena.getText().toString());
                    }
                } catch (Exception exception) {
                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Medicamento>>  call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Falló la conexión con servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean revisarSesion() {
        return this.preferences.getBoolean(llave, false);
    }

    public void onTimeSet(int hourOfDay, int minute, String nombre, String hora) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);



        Bundle parmetros = new Bundle();
        parmetros.putString("nombre",nombre);
        parmetros.putString("hora",hora);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int requestCode = (int)c.getTimeInMillis()/1000;
        parmetros.putInt("REQUEST",requestCode);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtras(parmetros);
        Log.i("Request", String.valueOf(requestCode));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

}