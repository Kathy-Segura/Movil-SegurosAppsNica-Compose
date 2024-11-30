package com.appsandroid.snic.modelo

import com.google.gson.annotations.SerializedName

data class BartChartData(
    @SerializedName("Agentes[Nombre Agente]") val nombreAgente: String,
    @SerializedName("[Monto Comision Total]") val montoComisionTotal: Float,
    @SerializedName("[Precio Total]") val precioTotal: Float
)