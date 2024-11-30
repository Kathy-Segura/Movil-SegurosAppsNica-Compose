package com.appsandroid.snic.api

import com.appsandroid.snic.modelo.AreaChartData
import com.appsandroid.snic.modelo.BartChartData
import com.appsandroid.snic.modelo.LineChartData
import com.appsandroid.snic.modelo.PieChartData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/apiolap/cubeagente")
    fun getPieChartData(): Call<List<PieChartData>>

    @GET("/apiolap/cubeventa")
    fun getLineChartData(): Call<List<LineChartData>>

    @GET("/apiolap/cubemes")
    fun getAreaChartData(): Call<List<AreaChartData>> // si no se puede se deja de columna

    @GET("/apiolap/cubedata")
    fun getBartChartData(): Call<List<BartChartData>>

}

