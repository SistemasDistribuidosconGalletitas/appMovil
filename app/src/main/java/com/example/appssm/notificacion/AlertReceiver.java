package com.example.appssm.notificacion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.appssm.R;
import com.example.appssm.domain.model.Medicamento;

public class AlertReceiver extends BroadcastReceiver {
    String nombre,hora;
    int requestCode;


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle parametros = intent.getExtras();
        nombre = parametros.getString("nombre");
        hora = parametros.getString("hora");
       requestCode = parametros.getInt("REQUEST");


        Intent notifyIntent = new Intent(context, Medicamento.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.icono_med1)
                .setContentTitle("Medicamento "+nombre)
                .setContentText("Debe tomar su medicamento a esta hora "+hora)
                .setAutoCancel(true)
                .setContentIntent(notifyPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
        mNotificationManager.notify(requestCode,mBuilder.build());
    }
}
