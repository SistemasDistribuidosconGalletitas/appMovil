package com.example.appssm.domain.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.appssm.base.AppDataBase;
import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.model.Usuario;

import java.util.List;

public class Repository {

    private final AppDataBase db;

    public Repository(Context context) {
        db = Room.databaseBuilder(context, AppDataBase.class, "appssmdb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public Usuario checkInformationUser(String userName, String pass){
        return db.usuariosDao().validateInfo(userName, pass);
    }

    public void insertUserLocalDb(Usuario usuario){
       // db.usuariosDao().insert(usuario);
        db.usuariosDao().insertOrUpdate(usuario);
    }

    public List<Usuario> getAllUsers(){
        return db.usuariosDao().getAll();
    }

    public Usuario getUsuario() {
        return db.usuariosDao().getUsuario();
    }

    public void insertRecetaLocalDb(Receta receta){
        db.recetaDao().insert(receta);
//        db.recetaDao().updateReceta(receta);
    }

    public List<Receta> getAllRecetas(){
        return db.recetaDao().getAllRecetas();
    }
    public Receta getReceta(int id_receta){
        return db.recetaDao().getReceta(id_receta);
    }

    public void insertMedicamentoLocalDb(Medicamento medicamento){
        db.medicamentoDao().insert(medicamento);
//        db.medicamentoDao().insertOrUpdate(medicamento);
    }

    public List<Medicamento> getAllMedicamentosByReceta(int id_receta){
        return db.medicamentoDao().getAllMedicamentosById(id_receta);
    }

    public List<Medicamento> getAllMedicamentos(){
        return db.medicamentoDao().getAllMedicamentos();
    }
}
