package com.example.appssm.domain.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_usuarioMovil")
    private int id_usuarioMovil;

    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nombrePaciente")
    private String nombrePaciente;

    @ColumnInfo(name = "correoElectronico")
    private String correoElectronico;

    @ColumnInfo(name = "contrasenia")
    private String contrasenia;

    @ColumnInfo(name = "telefono")
    private String telefono;

    @ColumnInfo(name = "nombreUsuario")
    private String nombreUsuario;

    public Usuario(int id, String nombrePaciente, String correoElectronico, String contrasenia, String telefono, String nombreUsuario) {
        this.id = id;
        this.nombrePaciente = nombrePaciente;
        this.correoElectronico = correoElectronico;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
    }

    public int getId_usuarioMovil() {
        return id_usuarioMovil;
    }

    public void setId_usuarioMovil(int id_usuarioMovil) {
        this.id_usuarioMovil = id_usuarioMovil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuarioMovil=" + id_usuarioMovil +
                ", id=" + id +
                ", nombrePaciente='" + nombrePaciente + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                '}';
    }
}
