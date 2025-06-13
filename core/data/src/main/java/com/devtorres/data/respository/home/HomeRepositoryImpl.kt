package com.devtorres.data.respository.home

import android.util.Log
import androidx.annotation.WorkerThread
import com.devtorres.model.Animal
import com.devtorres.network.model.mapper.asDomain
import com.devtorres.network.service.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
) : HomeRepository {

    /**
     * Obtener la lista de animales de la API
     */
    @WorkerThread
    override fun fetchAnimalList(
        page: Int,
        breedIds: String,
        categoryIds: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Animal>> = flow {
        try {
            onStart()
            Log.d("HomeRepositoryImpl", "fetchAnimalList: page: $page")
            // hacer la peticion
            val response = apiClient.fetchAnimalList(
                page = page,
                breedIds = breedIds,
                categoryIds = categoryIds
            )

            // verificar que fue exitosa
            if(response.isSuccessful) {
                //Log.d("HomeRepositoryImpl", "fetchAnimalList: exttosa")
                // obtener el cuerpo
                val bodyResponse = response.body()

                // veriricar con elvis si no es null
                bodyResponse?.let { animals ->
                    animals.forEach { animal ->
                        Log.d("HomeRepositoryImpl", "fetchAnimalList: ${animal}")
                    }
                    emit(animals.asDomain())
                    onComplete()
                }
            } else {
                //Log.d("HomeRepositoryImpl", "fetchAnimalList: ${response.errorBody()}")
                onError(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.d("HomeRepositoryImpl", "fetchAnimalList:  error: ${e.message}")
            onError(e.message)
        }
    }

}