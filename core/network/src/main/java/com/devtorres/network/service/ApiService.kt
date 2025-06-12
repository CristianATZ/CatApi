package com.devtorres.network.service

import com.devtorres.network.model.AnimalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * Peticion para obtener una lista de animales
     *
     * @param apiKey Clave de la API de The Cat API
     * @param limit Cantidad de animales a obtener (15 por defecto)
     * @param hasBreeds Si se deben obtener los animales que tengan informacion
     * @param breedIds Ids de las razas a obtener
     * @param categoryIds Ids de las categorias a obtener
     *
     */
    @GET("images/search?")
    suspend fun fetchAnimalList(
        @Query("api_key") apiKey: String = "",
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 10,
        @Query("has_breeds") hasBreeds: Boolean = true,
        @Query("breed_ids") breedIds: String = "",
        @Query("category_ids") categoryIds: String = ""
    ) : Response<List<AnimalResponse>>

}