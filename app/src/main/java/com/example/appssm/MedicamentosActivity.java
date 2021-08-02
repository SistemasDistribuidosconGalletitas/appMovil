package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Usuario;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.adapter.MedicamentoAdapter;
import com.example.appssm.notificacion.AlertReceiver;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MedicamentosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MedicamentoAdapter adapter;
    Repository repository;
    private List<Medicamento> list;
    TextView pacienteNombre;
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

        Usuario usuario = repository.getUsuario();
        pacienteNombre = (TextView) findViewById(R.id.paciente_nombre);
        pacienteNombre.setText(usuario.getNombrePaciente());
        list = new ArrayList();
        list = repository.getAllMedicamentosByReceta(recetaId);
        //list = repository.getAllMedicamentos();
        adapter = new MedicamentoAdapter(list);
        //adapter = new MedicamentoAdapter(repository.getAllMedicamentos());
        setAlarm(list.size());


        adapter.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                String title = list.get(recyclerView.getChildAdapterPosition(view)).getNombre();
                String hora = list.get(recyclerView.getChildAdapterPosition(view)).getHoraAplicacion();
                //onTimeSet(14,39);
                setAlarm(3);
//                sendNotification(title,hora);
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


    public void onTimeSet(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);


        startAlarm(c);
    }

    private void setAlarm(int number){
        Bundle parmetros = new Bundle();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        List<Calendar> calendarList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            c.set(Calendar.HOUR_OF_DAY, 17);
            c.set(Calendar.MINUTE, 07);
            calendarList.add(c);
        }
        int i=0;
        for (Calendar item : calendarList) {
            item.add(Calendar.SECOND,10);
            int requestCode = (int)c.getTimeInMillis()/1000;
            parmetros.putString("nombre",list.get(i).getNombre());
            parmetros.putString("hora",list.get(i).getHoraAplicacion());
            parmetros.putInt("REQUEST",requestCode);
            Intent intent = new Intent(this, AlertReceiver.class);
            intent.putExtras(parmetros);
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            i++;
        }
    }

    private void startAlarm(Calendar c) {

        Bundle parmetros = new Bundle();
        parmetros.putString("nombre","Pacetamol");
        parmetros.putString("hora","14:36");


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtras(parmetros);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
       // mTextView.setText("Alarm canceled");
    }
}