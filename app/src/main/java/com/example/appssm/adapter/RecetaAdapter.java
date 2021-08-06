package com.example.appssm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appssm.MedicamentosActivity;
import com.example.appssm.R;
import com.example.appssm.domain.model.Receta;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.ViewHolder> implements View.OnClickListener {

    private List<Receta> recetaList;
    private View.OnClickListener listener;
    ArrayList<Receta> recetaOriginalList;

    public RecetaAdapter(List<Receta> recetaList) {
        this.recetaList = recetaList;
        recetaOriginalList = new ArrayList<>();
        recetaOriginalList.addAll(recetaList);
    }

    public ArrayList<Receta> getRecetaOriginalList() {
        return recetaOriginalList;
    }

    public void setRecetaOriginalList(ArrayList<Receta> recetaOriginalList) {
        this.recetaOriginalList = recetaOriginalList;
    }

    public List<Receta> getRecetaList() {
        return recetaList;
    }

    public void setRecetaList(List<Receta> recetaList) {
        this.recetaList = recetaList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecetaAdapter.ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(recetaList.get(position).getIdReceta()));
        holder.fechaInicio.setText(recetaList.get(position).getRecetafechaFin());
        holder.fechaFin.setText(recetaList.get(position).getRecetafechaInicio());
        holder.nombreMedico.setText(recetaList.get(position).getNombreMedico());
//        holder.cantidadMedicamentos.setText(recetaList.get(position).getCantidad_medicamentos());
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

//    public void filtrado(String txtBusqueda){
//        int longitud = txtBusqueda.length();
//        if (longitud == 0){
//            recetaList.clear();
//            recetaList.addAll(recetaOriginalList);
//        } else {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                recetaList.clear();
//                List<Receta> collection = recetaOriginalList.stream().filter(i -> i.getNombre().toLowerCase().contains(txtBusqueda.toLowerCase())).collect(Collectors.toList());
//                recetaList.addAll(collection);
//            } else {
//                recetaList.clear();
//                for (Receta c: recetaOriginalList) {
//                    if (c.getNombre().toLowerCase().contains(txtBusqueda.toLowerCase())){
//                        recetaList.add(c);
//                    }
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }


    @Override
    public int getItemCount() {
        return recetaList.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView fechaInicio;
        private TextView fechaFin;
        private TextView nombreMedico;
        private final Context context;
//        private TextView cantidadMedicamentos;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();

            // Define click listener for the ViewHolder's View
            id=(TextView) view.findViewById(R.id.receta_id);
            fechaInicio=(TextView) view.findViewById(R.id.receta_fecha_inicio);
            fechaFin=(TextView) view.findViewById(R.id.receta_fecha_fin);
            nombreMedico = (TextView) view.findViewById(R.id.nombre_doctor);
//            cantidadMedicamentos=(TextView) view.findViewById(R.id.receta_medicamentos);
        }

    }

}
