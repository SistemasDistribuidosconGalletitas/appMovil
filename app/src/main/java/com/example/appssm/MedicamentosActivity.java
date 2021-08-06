package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Usuario;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.adapter.MedicamentoAdapter;
import com.example.appssm.notificacion.AlertReceiver;
import com.example.appssm.domain.ServidorContexto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        Toast.makeText(this, "Receta: " + id, Toast.LENGTH_SHORT).show();
        recetaId = id;

        Usuario usuario = repository.getUsuario();
        pacienteNombre = (TextView) findViewById(R.id.paciente_nombre);
        pacienteNombre.setText(usuario.getNombrePaciente());
        list = new ArrayList();
        list = repository.getAllMedicamentosByReceta(recetaId);
        //list = repository.getAllMedicamentos();
        adapter = new MedicamentoAdapter(list);
        //adapter = new MedicamentoAdapter(repository.getAllMedicamentos());
        //setAlarm(list.size());

        for (Medicamento med : list) {
            String horaAplicacion = med.getHoraAplicacion();
            int hora = Integer.parseInt(horaAplicacion.substring(0, 2));
            int min = Integer.parseInt(horaAplicacion.substring(3, 5));
            Log.i("HORA", String.valueOf(hora));
            Log.i("MIN", String.valueOf(min));
            onTimeSet(hora, min, med.getNombre(), med.getHoraAplicacion());
        }

        adapter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                int pos = recyclerView.getChildAdapterPosition(view);
                String horario = list.get(pos).getHoraAplicacion();
                double intervalo = list.get(pos).getIntervalo();
                TextView tv = recyclerView.findViewHolderForAdapterPosition(pos).itemView.findViewById(R.id.medicamento_hora);

                String newHr = ServidorContexto.cambiarHorario(horario, intervalo);

                list.get(recyclerView.getChildAdapterPosition(view)).setHoraAplicacion(newHr);
                tv.setText(newHr);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void sendNotification(String nombre, String hora) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.icono_med1)
                .setContentTitle(nombre)
                .setContentText("Debes de tomar este medicamento a las " + hora);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }


    public void onTimeSet(int hourOfDay, int minute, String nombre, String hora) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);


        Bundle parmetros = new Bundle();
        parmetros.putString("nombre", nombre);
        parmetros.putString("hora", hora);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int requestCode = (int) c.getTimeInMillis() / 1000;
        parmetros.putInt("REQUEST", requestCode);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtras(parmetros);
        Log.i("Request", String.valueOf(requestCode));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);

//        if (c.before(Calendar.getInstance())) {
//            c.add(Calendar.DATE, 1);
//        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void setAlarm(int number) {
        Bundle parmetros = new Bundle();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        List<Calendar> calendarList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            String horaAplicacion = list.get(i).getHoraAplicacion();
            int hora = Integer.parseInt(horaAplicacion.substring(0, 2));
            int min = Integer.parseInt(horaAplicacion.substring(3, 5));

            c.set(Calendar.HOUR_OF_DAY, 22);
            c.set(Calendar.MINUTE, (53 + i));
            c.set(Calendar.SECOND, 0);
            Log.i("List", String.valueOf(c));
            calendarList.add(c);
        }
        int i = 0;
        for (Calendar item : calendarList) {

            //item.add(Calendar.MINUTE,(45 + 1));
            Log.i("Item", String.valueOf(item.getTimeInMillis()));
            int requestCode = (int) c.getTimeInMillis() / 1000;
            parmetros.putString("nombre", list.get(i).getNombre());
            parmetros.putString("hora", list.get(i).getHoraAplicacion());
            parmetros.putInt("REQUEST", requestCode);
            Intent intent = new Intent(this, AlertReceiver.class);
            intent.putExtras(parmetros);
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, item.getTimeInMillis(), pendingIntent);
            i++;
        }
    }


    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        // mTextView.setText("Alarm canceled");
    }
}