package com.example.appssm.domain;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.interfaces.MedicamentoAPI;
import com.example.appssm.interfaces.RecetaAPI;
import com.example.appssm.notificacion.AlertReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServidorContexto {


    public ServidorContexto() {
    }

    // consultar recetas a web

    public static void findDataBaseWebRecetas(Context context, Repository repository, int id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://sistema-medico-app.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
//Receta(int idReceta, String fechaConsulta, String recetafechaInicio, String recetafechaFin, String nombreMedico, int paciente, boolean vigencia)
        RecetaAPI recetaAPI = retrofit.create(RecetaAPI.class);
        Call<List<Receta>> call = recetaAPI.find(id);

        call.enqueue(new Callback<List<Receta>>() {
            @Override
            public void onResponse(Call<List<Receta>>  call, Response<List<Receta>> response) {
                List<Receta> recetaList = response.body();
                try {
                    if (response.isSuccessful()) {
                        for (Receta p: recetaList){
                            crearReceta(repository,new Receta(p.getIdReceta(), p.getFechaConsulta(), p.getRecetafechaInicio(), p.getRecetafechaFin(), p.getPaciente(), p.getNombreMedico(), p.isVigencia()));
                            //repository.insertRecetaLocalDb(new Receta(p.getIdReceta(), p.getFechaConsulta(), p.getRecetafechaInicio(), p.getRecetafechaFin(), p.getPaciente(), p.getNombreMedico(), p.isVigencia()));
                        }
                        //Toast.makeText(LoginActivity.this, "Datos cargados exitosamente", Toast.LENGTH_SHORT).show();
                        //checkUserAndPass(login_email.getText().toString(), login_contrasena.getText().toString());
                    }
                } catch (Exception exception) {
                    Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Receta>>  call, Throwable t) {
                Toast.makeText(context, "Falló la conexión con servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // consultar medicamentos desde el servidor web

    public static void findDataBaseWebMedicamento(Context context, Repository repository) {
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
                            crearMedicamentos(repository, new Medicamento(p.getId(), p.getNombre(), p.getTipo(),
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
                    Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Medicamento>>  call, Throwable t) {
                Toast.makeText(context, "Falló la conexión con servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //    crear receta - crearReceta()
    public static void crearReceta(Repository repository,Receta receta){
        repository.insertRecetaLocalDb(receta);
    }

    // crear los medicamentos - crearMedicamentos()
    public static void crearMedicamentos(Repository repository, Medicamento medicamento){
        repository.insertMedicamentoLocalDb(medicamento);

    }

    //    checar codigo QR de medicamento - checarQR()
    //    modificar fechas (en caso de cambio por retraso de aplicacion de medicamento) - modificarFechas()

    //    revisar hora de aplicacion (para notificacion) - revisarHrAplicacion()


    public static void revisarHrAplicacion(Context context,int hourOfDay, int minute, String nombre, String hora){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);



        Bundle parmetros = new Bundle();
        parmetros.putString("nombre",nombre);
        parmetros.putString("hora",hora);


        AlarmManager alarmManager = (AlarmManager)  context.getSystemService(Context.ALARM_SERVICE);
        int requestCode = (int)c.getTimeInMillis()/1000;
        parmetros.putInt("REQUEST",requestCode);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtras(parmetros);
        Log.i("Request", String.valueOf(requestCode));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    //    modificar hora de aplicacion (nueva hora de aplicacion del medicamento)
    public static String modificarHrAplicacion(String strHorario, double intervalo) {
        int tmp = (int) (intervalo * 60.0);
        String[] time = new String[3];
        int[] intHorario = new int[2];
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("hh:mm");

        time = strHorario.split(":");

        for (int i = 0; i < 2; i++) {
            intHorario[i] = Integer.valueOf(time[i]);
        }

        cal.set(Calendar.HOUR_OF_DAY, intHorario[0]);
        cal.set(Calendar.MINUTE, intHorario[1]);
        cal.add(Calendar.MINUTE, tmp);

        return df.format(cal.getTime());
    }

    //    revisar dependencia (relacion entre medicamentos) - revisarDependencia()
    //    revisar prioridad (indice de gravedad por no aplicar un medicamento) - revisarPrioridad()
    //    actualizar historial - actualizarHistorial()
}
