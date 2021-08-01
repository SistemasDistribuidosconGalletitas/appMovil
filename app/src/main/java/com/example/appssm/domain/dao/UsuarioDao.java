package com.example.appssm.domain.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appssm.domain.model.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Query("SELECt * FROM Usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM Usuario")
    Usuario getUsuario();

    //Check Pass and user
    @Query("SELECT * FROM Usuario WHERE nombreUsuario IN (:userName) AND contrasenia IN (:pass)")
    Usuario validateInfo(String userName, String pass);

    //Insert User test
    @Insert
    Long insert (Usuario usuario);

    @Update
    public void updateUsers(Usuario usuario);

    default void insertOrUpdate(Usuario usuario) {
        List<Usuario> itemsFromDB = getAll();
        if (itemsFromDB.isEmpty())
            insert(usuario);
        else
            updateUsers(usuario);
    }
}