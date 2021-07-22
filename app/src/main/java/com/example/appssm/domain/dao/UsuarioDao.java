package com.example.appssm.domain.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appssm.domain.model.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Query("SELECt * FROM Usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM Usuario")
    Usuario getUsuario();

    //Check Pass and user
    @Query("SELECT * FROM Usuario WHERE correo_electronico IN (:userName) AND password IN (:pass)")
    Usuario validateInfo(String userName, String pass);

    //Insert User test
    @Insert
    Long insert (Usuario usuarios);
}