package com.example.appssm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appssm.domain.ServidorContexto;
import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.model.Usuario;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.adapter.RecetaAdapter;
import com.example.appssm.notificacion.AlertReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RecetasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecetaAdapter adapter;
    Repository repository;
    private List<Receta> list;
    TextView pacienteNombre;
    ImageView btn_logout;
    Button actualizarReceta;
    private ProgressDialog progressDialog;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String llave = "llave";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);
        Objects.requireNonNull(getSupportActionBar()).hide();

        preferences = getApplicationContext().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor = preferences.edit();

        recyclerView = findViewById(R.id.rv_receta);
        repository = new Repository(getApplicationContext());
        list = new ArrayList();
        list = repository.getAllRecetas();
        adapter = new RecetaAdapter(list);
        pacienteNombre = (TextView) findViewById(R.id.pacienteNombre);

        Usuario usuario = repository.getUsuario();
        pacienteNombre.setText(usuario.getNombrePaciente());

        btn_logout = (ImageView) findViewById(R.id.logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                mostrarAlerta();
              }
            }
        );

        adapter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Selección receta: "+list.get(recyclerView.getChildAdapterPosition(view)).getIdReceta(),
                       // Toast.LENGTH_LONG).show();
                int id_receta = list.get(recyclerView.getChildAdapterPosition(view)).getIdReceta();
                Bundle parmetros = new Bundle();
                parmetros.putInt("id",id_receta);
                Intent intent = new Intent(view.getContext(), MedicamentosActivity.class);
                intent.putExtras(parmetros);
                view.getContext().startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        actualizarReceta = (Button) findViewById(R.id.btn_actualizar);
        actualizarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServidorContexto.findDataBaseWebRecetas(getApplicationContext(), repository, usuario.getId());
                ServidorContexto.findDataBaseWebMedicamento(getApplicationContext(), repository);

                progressDialog = new ProgressDialog(RecetasActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();

                        list.removeAll(list);
                        recyclerView.removeAllViews();

                        list = repository.getAllRecetas();
                        adapter.setRecetaList(list);

                        recyclerView.setAdapter(adapter);
                    }
                }, 3000); // 3000 milliseconds delay
            }
        });
    }

    public void mostrarAlerta() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿Desea cerrar sesión en ApiMiel?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }

        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }

    public void aceptar() {
        Toast t = Toast.makeText(getApplicationContext(), "Cerrando sesión", Toast.LENGTH_SHORT);
        t.show();
        editor.putBoolean(this.llave, false);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        finish();
        startActivity(intent);
    }

    public void cancelar() {
    }
}
