package com.devtorres.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimalInfoResponse(
    @SerialName(value = "id")       val id:         String,
    @SerialName(value = "url")      val url:        String,
    @SerialName(value = "breeds")   val breeds:     List<BreedInfoResponse>
)

@Serializable
data class BreedInfoResponse(
    @SerialName("name")             val name:               String,
    @SerialName("origin")           val origin:             String,
    @SerialName("description")      val description:        String,
    @SerialName("life_span")        val life_span:          String,
    @SerialName("temperament")      val temperament:        String,
    @SerialName("adaptability")     val adaptability:       Int,
    @SerialName("affection_level")  val affection_level:    Int,
    @SerialName("child_friendly")   val child_friendly:     Int,
    @SerialName("energy_level")     val energy_level:       Int,
    @SerialName("grooming")         val grooming:           Int,
    @SerialName("rare")             val rare:               Int,
    @SerialName("intelligence")     val intelligence:       Int
)
