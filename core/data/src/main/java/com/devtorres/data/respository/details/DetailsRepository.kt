package com.devtorres.data.respository.details

import androidx.annotation.WorkerThread
import com.devtorres.model.AnimalInfo

interface DetailsRepository {

    @WorkerThread
    suspend fun getAnimalInfo(
        animalId: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): AnimalInfo?
}