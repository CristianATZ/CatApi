package com.devtorres.data.respository.home

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
            // hacer la peticion
            val response = apiClient.fetchAnimalList(
                page = page,
                breedIds = breedIds,
                categoryIds = categoryIds
            )

            // verificar que fue exitosa
            if(response.isSuccessful) {

                // obtener el cuerpo
                val bodyResponse = response.body()

                // veriricar con elvis si no es null
                bodyResponse?.let { animals ->
                    // emitir la lista mapeada
                    emit(animals.asDomain())
                }
            } else {
                onError(response.errorBody().toString())
            }
        } catch (e: Exception) {
            onError(e.message)
        }
    }

}