package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Usuario;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.adapter.MedicamentoAdapter;
import com.example.appssm.notificacion.AlertReceiver;
import com.example.appssm.domain.ServidorContexto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class MedicamentosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MedicamentoAdapter adapter;
    Repository repository;
    private List<Medicamento> list;
    TextView pacienteNombre;
    private int recetaId;
    Button medicamentoPrioridad;
    String nombrePa, nombreMe, horarioPa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.rv_medicamento);
        repository = new Repository(getApplicationContext());

        Bundle parametros = this.getIntent().getExtras();
        int id = parametros.getInt("id");
        //Toast.makeText(this, "Receta: " + id, Toast.LENGTH_SHORT).show();
        recetaId = id;

        Usuario usuario = repository.getUsuario();
        pacienteNombre = (TextView) findViewById(R.id.paciente_nombre);
        pacienteNombre.setText(usuario.getNombrePaciente());
        list = new ArrayList();
        list = repository.getAllMedicamentosByReceta(recetaId);
        //list = repository.getAllMedicamentos();
        adapter = new MedicamentoAdapter(list);


        adapter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                int pos = recyclerView.getChildAdapterPosition(view);
                String horario = list.get(pos).getHoraAplicacion();
                double intervalo = list.get(pos).getIntervalo();
                TextView tv = recyclerView.findViewHolderForAdapterPosition(pos).itemView.findViewById(R.id.medicamento_hora);

                String newHr = ServidorContexto.modificarHrAplicacion(horario, intervalo);

                list.get(recyclerView.getChildAdapterPosition(view)).setHoraAplicacion(newHr);
                tv.setText(newHr);

                Toast.makeText(getApplicationContext(), "Notificación de medicamento activada: " + newHr, Toast.LENGTH_SHORT).show();
                ServidorContexto.modificarHrAplicacionBD(repository,list.get(pos));


                int hora = Integer.parseInt(newHr.substring(0,2));
                int min = Integer.parseInt(newHr.substring(3,5));
                Log.i("HORA", String.valueOf(hora));
                Log.i("MIN", String.valueOf(min));
                //onTimeSet(hora,min,med.getNombre(),med.getHoraAplicacion());
                ServidorContexto.revisarHrAplicacion(getApplicationContext(),hora,min,list.get(pos).getNombre(),list.get(pos).getHoraAplicacion());


                // Mandar el SMS cuando la prioridad del medicamento es 1
                if (ContextCompat.checkSelfPermission(MedicamentosActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){

                    int prioridad = list.get(pos).getPrioridad();

                    nombrePa = usuario.getNombrePaciente();
                    nombreMe = list.get(pos).getNombre();
                    horarioPa = list.get(pos).getHoraAplicacion();
                    if (prioridad < 2) {
                        ServidorContexto.alertaPrioridad(getApplicationContext(),nombrePa, nombreMe, horarioPa);
                    }
                }else {
                    ActivityCompat.requestPermissions(MedicamentosActivity.this, new String[]{Manifest.permission.SEND_SMS},100);
                }


            }
        });
        recyclerView.setAdapter(adapter);



    }


    @Override
    public boolean onPictureInPictureRequested() {
        return super.onPictureInPictureRequested();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //sendMessage(nombrePa, nombreMe, horarioPa);
            ServidorContexto.alertaPrioridad(getApplicationContext(),nombrePa, nombreMe, horarioPa);
        }else{
            Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();
        }
    }

    public void sendMessage(String nombrePaciente, String nombreMedicamento, String horario){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("2881226389", null, nombrePaciente + " " + nombreMedicamento + " " + horario, null, null);
        Toast.makeText(getApplicationContext(), "Mensaje enviado", Toast.LENGTH_LONG).show();
    }
}