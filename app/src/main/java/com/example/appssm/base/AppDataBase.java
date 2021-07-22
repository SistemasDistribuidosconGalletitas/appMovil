package com.example.appssm.base;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.appssm.domain.dao.UsuarioDao;
import com.example.appssm.domain.model.Usuario;


@Database(entities = {Usuario.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UsuarioDao usuariosDao();
}
