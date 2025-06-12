package com.devtorres.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimalResponse(
    @SerialName(value = "id")       val id:         String,
    @SerialName(value = "url")      val url:        String,
    @SerialName(value = "breeds")   val breeds:     List<BreedBasicInfo>
)


@Serializable
data class BreedBasicInfo(
    @SerialName("name")     val name:       String,
    @SerialName("origin")   val origin:     String
)