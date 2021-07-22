package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Objects;

public class MedicamentosActivity extends AppCompatActivity {

    ImageView img_receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);
        Objects.requireNonNull(getSupportActionBar()).hide();

        img_receta = (ImageView) findViewById(R.id.appIconoReceta);
        img_receta.setOnClickListener(v ->
                regresarReceta()
        );
    }
    private void regresarReceta(){
        Intent intent = new Intent(MedicamentosActivity.this, RecetasActivity.class);
        startActivity(intent);
        finish();

    }
}