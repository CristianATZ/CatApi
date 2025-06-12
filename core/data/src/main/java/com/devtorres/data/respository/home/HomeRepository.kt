package com.devtorres.data.respository.home

import androidx.annotation.WorkerThread
import com.devtorres.model.Animal
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio de Home, encargado de:
 *
 * - Obtener la lista de animales de la API
 */
interface HomeRepository {

    @WorkerThread
    fun fetchAnimalList(
        page: Int,
        breedIds: String,
        categoryIds: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) : Flow<List<Animal>>
}