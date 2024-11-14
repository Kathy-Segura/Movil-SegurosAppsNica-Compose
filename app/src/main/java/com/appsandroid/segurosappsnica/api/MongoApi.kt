package com.appsandroid.segurosappsnica.api

import com.appsandroid.segurosappsnica.modelo.ApiResponse
import com.appsandroid.segurosappsnica.modelo.ApiResponse2
import retrofit2.Response
import retrofit2.http.GET

interface MongoApi {
    @GET("/polizas/") // Asegúrate de poner la ruta correcta
    suspend fun getSeguro(): Response<List<ApiResponse>> // Ahora devuelve un listado de ApiResponse

    @GET("/siniestros/") // Asegúrate de poner la ruta correcta para siniestros
    suspend fun getSiniestro(): Response<List<ApiResponse2>> // Devuelve un listado de ApiResponseSiniestro

}