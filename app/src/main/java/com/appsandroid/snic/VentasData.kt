package com.appsandroid.snic.network

import com.appsandroid.snic.api.RetrofitInstance
import com.appsandroid.snic.modelo.PieChartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VentasRepository {

    fun obtenerVentasPorAgente(
        onResult: (List<PieChartData>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        RetrofitInstance.api.getPieChartData().enqueue(object : Callback<List<PieChartData>> {
            override fun onResponse(call: Call<List<PieChartData>>, response: Response<List<PieChartData>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) }
                } else {
                    onError(Exception("Error en la respuesta de la API"))
                }
            }

            override fun onFailure(call: Call<List<PieChartData>>, t: Throwable) {
                onError(t)
            }
        })
    }
}
