package com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population

data class PopulationBody(
    val populated_center_id: Int,
    val ethnic_groups: List<Int>,
    val length: String,
    val latitude: String,
    val altitude: String,
    val photography: String,
    val inhabitants_number: String,
)