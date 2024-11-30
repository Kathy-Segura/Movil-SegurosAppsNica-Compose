package com.appsandroid.snic.network

import com.appsandroid.snic.api.RetrofitInstance
import com.appsandroid.snic.modelo.LineChartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PolizasRepository {

    fun obtenerVentasPorPoliza(
        onResult: (List<LineChartData>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        RetrofitInstance.api.getLineChartData().enqueue(object : Callback<List<LineChartData>> {
            override fun onResponse(call: Call<List<LineChartData>>, response: Response<List<LineChartData>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) }
                } else {
                    onError(Exception("Error en la respuesta de la API"))
                }
            }

            override fun onFailure(call: Call<List<LineChartData>>, t: Throwable) {
                onError(t)
            }
        })
    }
}