package com.example.appssm.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medicamento {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_medicamento")
    private int id_medicamento;

    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "tipo")
    private String tipo;

    @ColumnInfo(name = "dosis")
    private int dosis;

    @ColumnInfo(name = "aplicaciones")
    private int aplicaciones;

    @ColumnInfo(name = "fechaInicio")
    private String fechaInicio;

    @ColumnInfo(name = "fechaFin")
    private String fechaFin;

    @ColumnInfo(name = "horaAplicacion")
    private String horaAplicacion;

    @ColumnInfo(name = "intervalo")
    private double intervalo;

    @ColumnInfo(name = "margenTiempo")
    private String margenTiempo;

    @ColumnInfo(name = "prioridad")
    private int prioridad;


    public Medicamento(int id, String nombre, String tipo, int dosis, int aplicaciones, String fechaInicio, String fechaFin, String horaAplicacion, double intervalo, String margenTiempo, int prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.dosis = dosis;
        this.aplicaciones = aplicaciones;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaAplicacion = horaAplicacion;
        this.intervalo = intervalo;
        this.margenTiempo = margenTiempo;
        this.prioridad = prioridad;
    }

    public int getId_medicamento() {
        return id_medicamento;
    }

    public void setId_medicamento(int id_medicamento) {
        this.id_medicamento = id_medicamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public int getAplicaciones() {
        return aplicaciones;
    }

    public void setAplicaciones(int aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraAplicacion() {
        return horaAplicacion;
    }

    public void setHoraAplicacion(String horaAplicacion) {
        this.horaAplicacion = horaAplicacion;
    }

    public double getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(double intervalo) {
        this.intervalo = intervalo;
    }

    public String getMargenTiempo() {
        return margenTiempo;
    }

    public void setMargenTiempo(String margenTiempo) {
        this.margenTiempo = margenTiempo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id_medicamento=" + id_medicamento +
                ", id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", dosis=" + dosis +
                ", aplicaciones=" + aplicaciones +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", horaAplicacion='" + horaAplicacion + '\'' +
                ", intervalo=" + intervalo +
                ", margenTiempo='" + margenTiempo + '\'' +
                ", prioridad=" + prioridad +
                '}';
    }
}
