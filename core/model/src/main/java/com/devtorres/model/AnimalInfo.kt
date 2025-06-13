package com.devtorres.model

data class AnimalInfo(
    val id: String,
    val url: String,
    val breeds: BreedInfo
)

data class BreedInfo(
    val name: String,
    val origin: String,
    val life_span: String,
    val description: String,
    val temperament: String,
    val adaptability: Int,
    val affection_level: Int,
    val child_friendly: Int,
    val energy_level: Int,
    val grooming: Int,
    val rare: Int,
    val intelligence: Int
) {

    fun getOriginString(): String = "Origin: $origin"
    fun getLifeSpanString(): String = "Life span: $life_span"

    fun getAdaptabilityString(): String =" $adaptability/$MAX_VALUE"
    fun getAffectionLevelString(): String =" $affection_level/$MAX_VALUE"
    fun getChildFriendlyString(): String =" $child_friendly/$MAX_VALUE"
    fun getIntelligenceString(): String =" $intelligence/$MAX_VALUE"
    fun getEnergyLevelString(): String =" $energy_level/$MAX_VALUE"
    fun getGroomingString(): String =" $grooming/$MAX_VALUE"
    fun getRareString(): String =" $rare/$MAX_VALUE"
    fun getTemperamentList(): List<String> = temperament.split(", ")

    companion object {
        const val MAX_VALUE = 5
    }
}
