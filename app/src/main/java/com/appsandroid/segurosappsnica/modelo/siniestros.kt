package com.appsandroid.segurosappsnica.modelo

// Modelo que representa un siniestro
data class Siniestro(
    val _id: String,
    val tipo_seguro: String,
    val fecha_incidente: String,
    val ubicacion: String,
    val descripcion: String,
    val documentos: List<Documento>,
    val estado: String
)

// Modelo para los documentos asociados al siniestro
data class Documento(
    val tipo: String,
    val url: String
)

// Modelo para la respuesta de la API (contiene un arreglo de siniestros)
data class ApiResponse2(
    val data: Siniestro
)