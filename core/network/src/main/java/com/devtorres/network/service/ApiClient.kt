package com.devtorres.network.service

import com.devtorres.network.model.AnimalResponse
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchAnimalList(page: Int) : Result<List<AnimalResponse>> =
        apiService.fetchAnimalList(
            apiKey = API_KEY,
            page = page,
            limit = PAGING_SIZE
        )


    companion object {
        const val API_KEY = "live_8NqXIpPNADdRy57Zx8kTKsPIdGbS8TjMtOzDSSEgBcm8nCBlkPj0ibXv23Jd4kOv"

        private const val PAGING_SIZE = 15
    }
}