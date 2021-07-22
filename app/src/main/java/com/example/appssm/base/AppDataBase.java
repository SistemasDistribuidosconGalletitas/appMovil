package com.example.appssm.base;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.appssm.domain.dao.MedicamentoDao;
import com.example.appssm.domain.dao.RecetaDao;
import com.example.appssm.domain.dao.UsuarioDao;
import com.example.appssm.domain.model.Medicamento;
import com.example.appssm.domain.model.Receta;
import com.example.appssm.domain.model.Usuario;


@Database(entities = {Usuario.class, Receta.class, Medicamento.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UsuarioDao usuariosDao();
    public abstract RecetaDao recetaDao();
    public abstract MedicamentoDao medicamentoDao();
}
