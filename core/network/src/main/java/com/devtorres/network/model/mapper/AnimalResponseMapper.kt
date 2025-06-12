package com.devtorres.network.model.mapper

import com.devtorres.model.Animal
import com.devtorres.network.model.AnimalResponse

/**
 * Mapper de List<AnimalResponse> a List<Animal>
 */
object AnimalResponseMapper : ResponseMapper<List<Animal>, List<AnimalResponse>> {
    override fun asDomain(response: List<AnimalResponse>): List<Animal> {
        return response.map { animal ->
            Animal(
                id = animal.id,
                imageUri = "animal.uri",
                breedName = animal.breeds[0].name,
                breedOrigin = animal.breeds[0].origin
            )
        }
    }
}

/**
 * Funcion de extension para convertir una lista de AnimalResponse a una lista de Animal
 */
fun List<AnimalResponse>.asDomain() : List<Animal> {
    return AnimalResponseMapper.asDomain(this)
}