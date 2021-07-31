package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.adapter.MedicamentoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MedicamentosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MedicamentoAdapter adapter;
    Repository repository;
    private List<Medicamento> list;

    private int recetaId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.rv_medicamento);
        repository = new Repository(getApplicationContext());

        Bundle parametros = this.getIntent().getExtras();
        int id = parametros.getInt("id");
        Toast.makeText(this, "Receta: "+id, Toast.LENGTH_SHORT).show();
        recetaId = id;


        list = new ArrayList();
        list = repository.getAllMedicamentosByReceta(recetaId);
        adapter = new MedicamentoAdapter(list);
        //adapter = new MedicamentoAdapter(repository.getAllMedicamentos());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID","CHANNEL_ID", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        adapter.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                String title = list.get(recyclerView.getChildAdapterPosition(view)).getNombre();
                String hora = list.get(recyclerView.getChildAdapterPosition(view)).getHora_aplicacion();
                sendNotification(title,hora);
            }
        });
        recyclerView.setAdapter(adapter);
    }
    public void sendNotification(String nombre, String hora){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.icono_med1)
                .setContentTitle(nombre)
                .setContentText("Debes de tomar este medicamento a las "+ hora);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());
    }
}