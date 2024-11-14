package com.appsandroid.segurosappsnica.modelo

import com.google.gson.annotations.SerializedName

data class LineChartData(
    @SerializedName("Polizas[Nombre Polizas]") val nombrePoliza: String,
    @SerializedName("[Total Ventas]") val totalVentas: Float
)