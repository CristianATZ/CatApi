package com.devtorres.network.service

import com.devtorres.network.model.AnimalInfoResponse
import com.devtorres.network.model.AnimalResponse
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchAnimalList(
        page: Int,
        breedIds: String,
        categoryIds: String
    ) : Response<List<AnimalResponse>> =
        apiService.fetchAnimalList(
            apiKey = API_KEY,
            page = page,
            limit = PAGING_SIZE,
            hasBreeds = categoryIds.isEmpty(),
            breedIds = breedIds,
            categoryIds = categoryIds
        )

    suspend fun fetchAnimalInfo(
        animalId: String
    ) : Response<AnimalInfoResponse> =
        apiService.fetchAnimalInfo(animalId = animalId)


    companion object {
        const val API_KEY = "live_8NqXIpPNADdRy57Zx8kTKsPIdGbS8TjMtOzDSSEgBcm8nCBlkPj0ibXv23Jd4kOv"

        private const val PAGING_SIZE = 15
    }
}