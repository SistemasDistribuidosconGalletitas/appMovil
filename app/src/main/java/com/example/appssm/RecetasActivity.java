package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.appssm.domain.repository.Repository;
import com.example.appssm.adapter.RecetaAdapter;

import java.util.Objects;

public class RecetasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecetaAdapter adapter;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.rv_receta);
        repository = new Repository(getApplicationContext());
        adapter = new RecetaAdapter(repository.getAllRecetas());
        recyclerView.setAdapter(adapter);
    }

}