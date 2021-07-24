package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

public class MedicamentosActivity extends AppCompatActivity {

    ImageView img_receta;
    ImageView img_noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);
        Objects.requireNonNull(getSupportActionBar()).hide();

        img_receta = (ImageView) findViewById(R.id.appIconoReceta);
        img_receta.setOnClickListener(v ->
                regresarReceta()
        );

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID","CHANNEL_ID", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }
    private void regresarReceta(){
        Intent intent = new Intent(MedicamentosActivity.this, RecetasActivity.class);
        startActivity(intent);
        //finish();

    }

    public void sendNotification(View view){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.icono_med1)
                .setContentTitle("Naproxeno Sódico")
                .setContentText("Debes de tomar este medicamento a las 12:00 pm");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());
    }
}