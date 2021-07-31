package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.adapter.RecetaAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecetasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecetaAdapter adapter;
    Repository repository;
    private List<Receta> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);
        Objects.requireNonNull(getSupportActionBar()).hide();


        recyclerView = findViewById(R.id.rv_receta);
        repository = new Repository(getApplicationContext());
        list = new ArrayList();
        list = repository.getAllRecetas();
        adapter = new RecetaAdapter(list);


        adapter.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Selección receta: "+list.get(recyclerView.getChildAdapterPosition(view)).getId_receta(),
                        Toast.LENGTH_LONG).show();
                int id_receta = list.get(recyclerView.getChildAdapterPosition(view)).getId_receta();
                Bundle parmetros = new Bundle();
                parmetros.putInt("id",id_receta);
                Intent intent = new Intent(view.getContext(), MedicamentosActivity.class);
                intent.putExtras(parmetros);
                view.getContext().startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

}