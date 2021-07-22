package com.example.appssm.domain.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appssm.domain.model.Medicamento;

import java.util.List;

@Dao
public interface MedicamentoDao {

    @Query("SELECt * FROM Medicamento WHERE id_receta IN (:idReceta)")
    List<Medicamento> getAllMedicamentos(int idReceta);



    //Insert Medicamentos test
    @Insert
    Long insert (Medicamento medicamento);
}
