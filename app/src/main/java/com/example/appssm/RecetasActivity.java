package com.example.appssm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class RecetasActivity extends AppCompatActivity {

//    Button btn_med;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);
//        setContentView(R.layout.fragment_receta);
        Objects.requireNonNull(getSupportActionBar()).hide();

//        btn_med = (Button) findViewById(R.id.btn_med);
//        btn_med.setOnClickListener(v ->
//                ejemlo()
//        );
    }

//    private void ejemlo() {
//
//            Intent intent = new Intent(RecetasActivity.this, MedicamentosActivity.class);
//            startActivity(intent);
//            Toast.makeText(this, "Activity de Medicamentos", Toast.LENGTH_SHORT).show();
//            //finish();
//    }
}