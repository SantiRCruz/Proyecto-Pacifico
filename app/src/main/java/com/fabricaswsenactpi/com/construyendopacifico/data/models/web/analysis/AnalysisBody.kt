package com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis

data class AnalysisBody(
    val date: String,
    val hour: String,
    val population_id: Int,
    val sample_type: String,
    val sample_type_id: Int,
    val user_id: Int,
    val water_type_id: Int
)