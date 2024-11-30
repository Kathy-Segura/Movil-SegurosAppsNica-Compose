package com.appsandroid.snic


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsandroid.snic.api.MongoRetrofit
import com.appsandroid.snic.modelo.ApiResponse
import com.appsandroid.snic.modelo.Seguro
import kotlinx.coroutines.launch
import retrofit2.Response

/*class SeguroViewModel : ViewModel() {
    var seguro: Seguro? by mutableStateOf(null) // Ahora es un solo objeto seguro
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchSeguro() {
        isLoading = true // Se establece que se está cargando

        // Realiza la llamada a la API dentro de un coroutine
        viewModelScope.launch {
            try {
                val response: Response<List<ApiResponse>> = MongoRetrofit.api.getSeguro() // Llamada a la API

                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    // Ahora la respuesta es una lista de seguros, tomamos el primer objeto
                    val apiResponseList = response.body()
                    seguro = apiResponseList?.firstOrNull()?.data // Obtener el primer seguro de la lista
                } else {
                    // Si la respuesta no fue exitosa, muestra el código y mensaje de error
                    errorMessage = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                // Captura cualquier excepción y asigna el mensaje de error
                errorMessage = "Error al cargar los datos: ${e.message}"
            } finally {
                isLoading = false // Se termina el estado de carga
            }
        }
    }
}*/


class SeguroViewModel : ViewModel() {
    var seguro: Seguro? by mutableStateOf(null) // Mantiene un solo objeto seguro para compatibilidad
    var seguros: List<Seguro> by mutableStateOf(emptyList()) // Lista de seguros para mostrar múltiples registros
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchSeguro() {
        isLoading = true // Inicia el estado de carga

        // Realiza la llamada a la API dentro de un coroutine
        viewModelScope.launch {
            try {
                val response: Response<List<ApiResponse>> = MongoRetrofit.api.getSeguro() // Llamada a la API

                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    val apiResponseList = response.body()
                    seguro = apiResponseList?.firstOrNull()?.data // Mantener el primer seguro para compatibilidad
                    seguros = apiResponseList?.mapNotNull { it.data } ?: emptyList() // Mapea toda la lista de seguros
                } else {
                    // Si la respuesta no fue exitosa, muestra el código y mensaje de error
                    errorMessage = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                // Captura cualquier excepción y asigna el mensaje de error
                errorMessage = "Error al cargar los datos: ${e.message}"
            } finally {
                isLoading = false // Termina el estado de carga
            }
        }
    }
}