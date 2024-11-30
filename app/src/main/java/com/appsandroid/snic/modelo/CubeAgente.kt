package com.appsandroid.snic.modelo

import com.google.gson.annotations.SerializedName

data class PieChartData(
    @SerializedName("Agentes[Nombre Agente]") val nombreAgente: String?,
    @SerializedName("[Total Ventas]") val totalVentas: Double?
)



