package com.example.appssm.domain.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appssm.domain.model.Receta;

import java.util.List;

@Dao
public interface RecetaDao {

    @Query("SELECt * FROM Receta")
    List<Receta> getAllRecetas();

    @Query("SELECT * FROM Receta WHERE id_receta IN (:idReceta)")
    Receta getReceta(int idReceta);



    //Insert Receta test
    @Insert
    Long insert (Receta receta);

    @Update
    public void updateReceta(Receta receta);

    default void insertOrUpdate(Receta receta){
        List<Receta> itemsReceta = getAllRecetas();
        if(itemsReceta.isEmpty()){
            insert(receta);
        }else{
            updateReceta(receta);
        }
    }
}
