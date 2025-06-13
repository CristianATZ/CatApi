package com.devtorres.network.model.mapper

import com.devtorres.model.AnimalInfo
import com.devtorres.model.BreedInfo
import com.devtorres.network.model.AnimalInfoResponse

/**
 * Mapper de AnimalInfoResponse a AnimalInfo
 */
object AnimalInfoResponseMapper : ResponseMapper<AnimalInfo, AnimalInfoResponse> {
    override fun asDomain(response: AnimalInfoResponse): AnimalInfo {
        return AnimalInfo(
            id = response.id,
            url = response.url,
            breeds = BreedInfo(
                name = response.breeds.first().name,
                origin = response.breeds.first().origin,
                life_span = response.breeds.first().life_span,
                description = response.breeds.first().description,
                temperament = response.breeds.first().temperament,
                adaptability = response.breeds.first().adaptability,
                affection_level = response.breeds.first().affection_level,
                child_friendly = response.breeds.first().child_friendly,
                intelligence = response.breeds.first().intelligence,
                energy_level = response.breeds.first().energy_level,
                grooming = response.breeds.first().grooming,
                rare = response.breeds.first().rare,
            )
        )
    }
}

/**
 * Funcion de extension para convertir una AnimalInfoResponse a una AnimalInfo
 */
fun AnimalInfoResponse.asDomain() : AnimalInfo {
    return AnimalInfoResponseMapper.asDomain(this)
}