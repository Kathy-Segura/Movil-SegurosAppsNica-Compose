package com.appsandroid.segurosappsnica.network

import com.appsandroid.segurosappsnica.api.RetrofitInstance
import com.appsandroid.segurosappsnica.modelo.BartChartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgentesRepository {

    fun obtenerComisionesPorAgente(
        onResult: (List<BartChartData>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        RetrofitInstance.api.getBartChartData().enqueue(object : Callback<List<BartChartData>> {
            override fun onResponse(call: Call<List<BartChartData>>, response: Response<List<BartChartData>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) }
                } else {
                    onError(Exception("Error en la respuesta de la API"))
                }
            }

            override fun onFailure(call: Call<List<BartChartData>>, t: Throwable) {
                onError(t)
            }
        })
    }
}