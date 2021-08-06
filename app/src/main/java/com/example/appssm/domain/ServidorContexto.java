package com.example.appssm.domain;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.notificacion.AlertReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServidorContexto {


    public ServidorContexto() {
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
