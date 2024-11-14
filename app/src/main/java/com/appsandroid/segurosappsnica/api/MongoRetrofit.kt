package com.appsandroid.segurosappsnica.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MongoRetrofit {
    private const val BASE_URL = "https://containerseguros.azurewebsites.net/" // Reemplaza con la URL base de tu API

    val api: MongoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MongoApi::class.java)
    }
}