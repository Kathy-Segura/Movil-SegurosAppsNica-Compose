package com.appsandroid.segurosappsnica.modelo

import com.google.gson.annotations.SerializedName

/*data class AreaChartData(
    @SerializedName("Clientes[Nombre Cliente]") val nombreCliente: String,
    @SerializedName("[Total Ventas]") val totalVentas: Float
)*/

data class AreaChartData(
    @SerializedName("Dim_Date[MonthName]") val monthName: String,  // Nombre del mes
    @SerializedName("[Total Ventas]") val totalVentas: Float      // Total de ventas
)