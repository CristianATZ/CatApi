package com.devtorres.data.respository.details

import android.util.Log
import androidx.annotation.WorkerThread
import com.devtorres.model.AnimalInfo
import com.devtorres.network.model.mapper.asDomain
import com.devtorres.network.service.ApiClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient

) : DetailsRepository {

    @WorkerThread
    override suspend fun getAnimalInfo(
        animalId: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): AnimalInfo? {
        return try {
            onStart()

            val response = apiClient.fetchAnimalInfo(
                animalId = animalId
            )

            if(response.isSuccessful) {
                val bodyResponse = response.body()

                bodyResponse?.let { animalInfo ->
                    Log.d("DetailsRepositoryImpl", "getAnimalInfo: ${animalInfo}")

                    onComplete()
                    animalInfo.asDomain()
                }
            } else {
                Log.d("DetailsRepositoryImpl", "getAnimalInfo: ${response.errorBody()}")
                onError(response.errorBody().toString())
                null
            }
        } catch (e: Exception) {
            Log.d("DetailsRepositoryImpl", "getAnimalInfo: ${e.message}")
            onError(e.message)
            null
        }
    }
}