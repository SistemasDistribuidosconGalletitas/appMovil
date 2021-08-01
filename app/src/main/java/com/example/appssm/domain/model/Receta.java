package com.example.appssm.domain.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Receta {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_receta")
    private int id_receta;

    @ColumnInfo(name = "idReceta")
    private int idReceta;

    @ColumnInfo(name = "fechaConsulta")
    private String fechaConsulta;

    @ColumnInfo(name = "recetafechaInicio")
    private String recetafechaInicio;

    @ColumnInfo(name = "recetafechaFin")
    private String recetafechaFin;

    @ColumnInfo(name = "nombreMedico")
    private String nombreMedico;

    @ColumnInfo(name = "paciente")
    private int paciente;

    @ColumnInfo(name = "vigencia")
    private boolean vigencia;

    public Receta(int idReceta, String fechaConsulta, String recetafechaInicio, String recetafechaFin, String nombreMedico, int paciente, boolean vigencia) {
        this.idReceta = idReceta;
        this.fechaConsulta = fechaConsulta;
        this.recetafechaInicio = recetafechaInicio;
        this.recetafechaFin = recetafechaFin;
        this.nombreMedico = nombreMedico;
        this.paciente = paciente;
        this.vigencia = vigencia;
    }

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getRecetafechaInicio() {
        return recetafechaInicio;
    }

    public void setRecetafechaInicio(String recetafechaInicio) {
        this.recetafechaInicio = recetafechaInicio;
    }

    public String getRecetafechaFin() {
        return recetafechaFin;
    }

    public void setRecetafechaFin(String recetafechaFin) {
        this.recetafechaFin = recetafechaFin;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public int getPaciente() {
        return paciente;
    }

    public void setPaciente(int paciente) {
        this.paciente = paciente;
    }

    public boolean isVigencia() {
        return vigencia;
    }

    public void setVigencia(boolean vigencia) {
        this.vigencia = vigencia;
    }
}
