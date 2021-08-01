package com.example.appssm.interfaces;


import com.example.appssm.domain.model.Receta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecetaAPI {
    @GET("api/ssm/recetas/{id}")
    Call<Receta> find(@Path("id") String id);
}
