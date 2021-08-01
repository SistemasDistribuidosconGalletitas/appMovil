package com.example.appssm.domain.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appssm.domain.model.Medicamento;

import java.util.List;

@Dao
public interface MedicamentoDao {

    @Query("SELECt * FROM Medicamento WHERE receta IN (:idReceta)")
    List<Medicamento> getAllMedicamentosById(int idReceta);

    @Query("SELECt * FROM Medicamento ")
    List<Medicamento> getAllMedicamentos();


    //Insert Medicamentos test
    @Insert
    Long insert (Medicamento medicamento);

    @Update
    public void updateMedicamento(Medicamento medicamento);

    default void insertOrUpdate(Medicamento medicamento){
        List<Medicamento> itemsMedicamento = getAllMedicamentos();
        if(itemsMedicamento.isEmpty()){
            insert(medicamento);
        }else{
            updateMedicamento(medicamento);
        }
    }
}
