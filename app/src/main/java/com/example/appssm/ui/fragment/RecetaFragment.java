package com.example.appssm.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appssm.R;
import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.repository.Repository;
import com.example.appssm.ui.adapter.RecetaAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecetaFragment extends Fragment implements SearchView.OnQueryTextListener{
    private RecetaAdapter adapter;
    private RecyclerView recyclerView;
    private List<Receta> list;
    private Repository repository;

    public RecetaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_receta);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        list = new ArrayList<>();
        adapter = new RecetaAdapter(list);
        repository = new Repository(getContext());

        list = repository.getAllRecetas();
        adapter = new RecetaAdapter(list);

        adapter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Selección: "+list.get(recyclerView.getChildAdapterPosition(view)).getNombre(),
                        Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filtrado(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}