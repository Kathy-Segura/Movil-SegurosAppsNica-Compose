package com.appsandroid.snic.network

import com.appsandroid.snic.api.RetrofitInstance
import com.appsandroid.snic.modelo.AreaChartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientesRepository {

    fun obtenerVentasPorMes(
        onResult: (List<AreaChartData>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        RetrofitInstance.api.getAreaChartData().enqueue(object : Callback<List<AreaChartData>> {
            override fun onResponse(call: Call<List<AreaChartData>>, response: Response<List<AreaChartData>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) }
                } else {
                    onError(Exception("Error en la respuesta de la API"))
                }
            }

            override fun onFailure(call: Call<List<AreaChartData>>, t: Throwable) {
                onError(t)
            }
        })
    }
}