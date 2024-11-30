package com.appsandroid.snic.modelo

// Modelo que representa los datos del seguro
data class Seguro(
    val _id: String,
    val tipo: String,
    val coberturas: Coberturas,
    val prima_mensual: Double,
    val fecha_inicio: String,
    val fecha_fin: String,
    val modificaciones: List<Modificacion>
)

// Modelo para las coberturas
data class Coberturas(
    val incendio: Int,
    val robo: Int,
    val terremoto: Int
)

// Modelo para las modificaciones
data class Modificacion(
    val fecha: String,
    val detalles: String
)

// Modelo para la respuesta de la API (contiene un arreglo de objetos)
data class ApiResponse(
    val data: Seguro
)