package com.example.appssm.domain.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Usuario {
    @PrimaryKey
    @ColumnInfo(name = "id_usuario")
    private int id_usuario;

    @ColumnInfo(name = "nombre_usuario")
    private String nombre;

    @ColumnInfo(name = "correo_electronico")
    private String correo_electronico;

    @ColumnInfo(name = "password")
    private String pass;

    @ColumnInfo(name = "telefono")
    private String telefono;

    public Usuario (int id_usuario, String nombre, String correo_electronico, String pass, String telefono){
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo_electronico = correo_electronico;
        this.pass = pass;
        this.telefono = telefono;

    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", correo_electronico='" + correo_electronico + '\'' +
                ", pass='" + pass + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }



}
