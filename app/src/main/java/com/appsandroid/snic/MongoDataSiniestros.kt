package com.appsandroid.snic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsandroid.snic.api.MongoRetrofit
import com.appsandroid.snic.modelo.ApiResponse2
import com.appsandroid.snic.modelo.Siniestro
import kotlinx.coroutines.launch
import retrofit2.Response

/*class SiniestroViewModel : ViewModel() {
    var siniestro: Siniestro? by mutableStateOf(null) // Siniestro individual
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchSiniestro() {
        isLoading = true // Se establece que se está cargando

        // Realiza la llamada a la API dentro de un coroutine
        viewModelScope.launch {
            try {
                val response: Response<List<ApiResponse2>> = MongoRetrofit.api.getSiniestro() // Llamada a la API

                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    // Ahora la respuesta es una lista de siniestros, tomamos el primer objeto
                    val apiResponseList = response.body()
                    siniestro = apiResponseList?.firstOrNull()?.data // Obtener el primer siniestro de la lista
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

class SiniestroViewModel : ViewModel() {
    var siniestro: List<Siniestro> by mutableStateOf(emptyList()) // Lista de siniestros
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchSiniestro() {
        isLoading = true // Inicia el estado de carga

        viewModelScope.launch {
            try {
                val response: Response<List<ApiResponse2>> = MongoRetrofit.api.getSiniestro() // Llamada a la API

                if (response.isSuccessful) {
                    val apiResponseList = response.body()
                    siniestro = apiResponseList?.mapNotNull { it.data } ?: emptyList() // Mapea cada `data` no nulo en la lista
                } else {
                    errorMessage = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error al cargar los datos: ${e.message}"
            } finally {
                isLoading = false // Finaliza el estado de carga
            }
        }
    }
}