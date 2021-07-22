package com.example.appssm.domain.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Receta {

    @PrimaryKey
    @ColumnInfo(name = "id_receta")
    private int id_receta;

    @ColumnInfo(name = "nombre_medico")
    private String nombre;

    @ColumnInfo(name = "fechaConsulta")
    private String fecha_consulta;

    @ColumnInfo(name = "fechaInicio")
    private String fecha_inicio;

    @ColumnInfo(name = "fechaFin")
    private String fecha_fin;

    public Receta(int id_receta, String nombre, String fecha_consulta, String fecha_inicio, String fecha_fin) {
        this.id_receta = id_receta;
        this.nombre = nombre;
        this.fecha_consulta = fecha_consulta;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
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

    public String getFecha_consulta() {
        return fecha_consulta;
    }

    public void setFecha_consulta(String fecha_consulta) {
        this.fecha_consulta = fecha_consulta;
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

    @Override
    public String toString() {
        return "Receta{" +
                "id_receta=" + id_receta +
                ", nombre='" + nombre + '\'' +
                ", fecha_consulta='" + fecha_consulta + '\'' +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                '}';
    }
}
