package com.example.appssm.interfaces;

import com.example.appssm.domain.model.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioAPI {
    @GET("api/ssm/pacientes/{nombreUsuario}/{contrasenia}")
    Call<Usuario> find(@Path("nombreUsuario") String nombreUsuario, @Path("contrasenia") String contrasenia);
}
