package com.example.appssm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appssm.R;
import com.example.appssm.domain.model.Medicamento;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Medicamento> medicamentoList;
    private View.OnClickListener listener;
    ArrayList<Medicamento> medicamentoOriginalList;

    public MedicamentoAdapter(List<Medicamento> medicamentoList) {
        this.medicamentoList = medicamentoList;
        medicamentoOriginalList = new ArrayList<>();
        medicamentoOriginalList.addAll(medicamentoList);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MedicamentoAdapter.ViewHolder holder, int position) {
        holder.nombre.setText(String.valueOf(medicamentoList.get(position).getNombre()));
        holder.hora.setText(String.valueOf(medicamentoList.get(position).getHoraAplicacion()));
        holder.intervalo.setText(String.valueOf(medicamentoList.get(position).getIntervalo()));
        holder.dosis.setText(String.valueOf(medicamentoList.get(position).getDosis()));
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public void filtrado(String txtBusqueda){
        int longitud = txtBusqueda.length();
        if (longitud == 0){
            medicamentoList.clear();
            medicamentoList.addAll(medicamentoOriginalList);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                medicamentoList.clear();
                List<Medicamento> collection = medicamentoOriginalList.stream().filter(i -> i.getNombre().toLowerCase().contains(txtBusqueda.toLowerCase())).collect(Collectors.toList());
                medicamentoList.addAll(collection);
            } else {
                medicamentoList.clear();
                for (Medicamento c: medicamentoOriginalList) {
                    if (c.getNombre().toLowerCase().contains(txtBusqueda.toLowerCase())){
                        medicamentoList.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return medicamentoList.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView hora;
        private TextView intervalo;
        private TextView dosis;

        public ViewHolder(View view) {
            super(view);

            // Define click listener for the ViewHolder's View
            nombre =(TextView) view.findViewById(R.id.medicamento_nombre);
            hora=(TextView) view.findViewById(R.id.medicamento_hora);
            intervalo=(TextView) view.findViewById(R.id.medicamento_intervalo_hrs);
            dosis=(TextView) view.findViewById(R.id.medicamento_dosis);
        }

    }

}

