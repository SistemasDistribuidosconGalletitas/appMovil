package com.example.appssm.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medicamento {

    @PrimaryKey
    @ColumnInfo(name = "id_medicamento")
    private int id_medicamento;

    @ColumnInfo(name = "id_receta")
    private int id_receta;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "fechaInicio")
    private String fecha_inicio;

    @ColumnInfo(name = "fechaFin")
    private String fecha_fin;

    @ColumnInfo(name = "dosis")
    private int dosis;

    @ColumnInfo(name = "tipo")
    private String tipo;

    @ColumnInfo(name = "horaAplicacion")
    private String hora_aplicacion;

    @ColumnInfo(name = "intervalo")
    private double intervalo;

    @ColumnInfo(name = "margenTiempo")
    private String margen_tiempo;

    @ColumnInfo(name = "aplicaciones")
    private int aplicaciones;

    @ColumnInfo(name = "reagendar")
    private boolean reagendar;

    @ColumnInfo(name = "prioridad")
    private int prioridad;

    public Medicamento(int id_medicamento, int id_receta, String nombre, String fecha_inicio,
                       String fecha_fin, int dosis, String tipo, String hora_aplicacion,
                       double intervalo, String margen_tiempo, int aplicaciones, boolean reagendar, int prioridad) {
        this.id_medicamento = id_medicamento;
        this.id_receta = id_receta;
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.dosis = dosis;
        this.tipo = tipo;
        this.hora_aplicacion = hora_aplicacion;
        this.intervalo = intervalo;
        this.margen_tiempo = margen_tiempo;
        this.aplicaciones = aplicaciones;
        this.reagendar = reagendar;
        this.prioridad = prioridad;
    }

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHora_aplicacion() {
        return hora_aplicacion;
    }

    public void setHora_aplicacion(String hora_aplicacion) {
        this.hora_aplicacion = hora_aplicacion;
    }

    public double getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(double intervalo) {
        this.intervalo = intervalo;
    }

    public String getMargen_tiempo() {
        return margen_tiempo;
    }

    public void setMargen_tiempo(String margen_tiempo) {
        this.margen_tiempo = margen_tiempo;
    }

    public int getAplicaciones() {
        return aplicaciones;
    }

    public void setAplicaciones(int aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public boolean isReagendar() {
        return reagendar;
    }

    public void setReagendar(boolean reagendar) {
        this.reagendar = reagendar;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getId_medicamento() {
        return id_medicamento;
    }

    public void setId_medicamento(int id_medicamento) {
        this.id_medicamento = id_medicamento;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id_medicamento=" + id_medicamento +
                ", id_receta=" + id_receta +
                ", nombre='" + nombre + '\'' +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                ", dosis=" + dosis +
                ", tipo='" + tipo + '\'' +
                ", hora_aplicacion='" + hora_aplicacion + '\'' +
                ", intervalo=" + intervalo +
                ", margen_tiempo='" + margen_tiempo + '\'' +
                ", aplicaciones=" + aplicaciones +
                ", reagendar=" + reagendar +
                ", prioridad=" + prioridad +
                '}';
    }
}
