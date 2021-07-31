package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appssm.domain.repository.Repository;
import com.example.appssm.adapter.MedicamentoAdapter;

import java.util.Objects;

public class MedicamentosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MedicamentoAdapter adapter;
    Repository repository;

    private int recetaId;

    MedicamentosActivity(int recetaId){
        this.recetaId = recetaId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.rv_medicamento);
        repository = new Repository(getApplicationContext());

//        adapter = new MedicamentoAdapter(repository.getAllMedicamentosByReceta(recetaId));
        adapter = new MedicamentoAdapter(repository.getAllMedicamentos());
        recyclerView.setAdapter(adapter);
    }
}